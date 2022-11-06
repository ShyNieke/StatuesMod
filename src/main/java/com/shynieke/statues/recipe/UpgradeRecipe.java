package com.shynieke.statues.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.registry.StatueTags;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class UpgradeRecipe implements Recipe<Container> {
	protected final ResourceLocation id;
	protected final String group;
	protected final Ingredient center;
	protected final NonNullList<Ingredient> catalysts;
	protected final ItemStack result;
	protected final boolean requireCore;
	private final boolean isSimple;
	private final UpgradeType upgradeType;
	private final int tier;

	public UpgradeRecipe(ResourceLocation id, String group, Ingredient center, NonNullList<Ingredient> catalysts,
						 ItemStack stack, boolean requireCore, UpgradeType upgradeType, int tier) {
		this.id = id;
		this.group = group;
		this.center = center;
		this.catalysts = catalysts;
		this.result = stack;
		this.requireCore = requireCore;
		this.upgradeType = upgradeType;
		this.tier = tier;
		this.isSimple = catalysts.stream().allMatch(Ingredient::isSimple);
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(center);
		if (requireCore)
			nonnulllist.add(Ingredient.of(StatueTags.STATUE_CORE));
		nonnulllist.addAll(catalysts);
		return nonnulllist;
	}

	public Ingredient getCenter() {
		return center;
	}

	public NonNullList<Ingredient> getCatalysts() {
		return catalysts;
	}

	public boolean requiresCore() {
		return requireCore;
	}

	public int getTier() {
		return tier;
	}

	public UpgradeType getUpgradeType() {
		return upgradeType;
	}

	@Override
	public boolean matches(Container container, Level level) {
		ItemStack statueStack = container.getItem(0);
		if (!center.test(statueStack)) {
			return false;
		}
		if (upgradeType.requiresUpgrade() && statueStack.getItem() instanceof StatueBlockItem) {
			CompoundTag compoundtag = statueStack.getTagElement("BlockEntityTag");
			if (compoundtag == null || !compoundtag.contains(Reference.UPGRADED) || compoundtag.getInt(Reference.UPGRADE_SLOTS) < 1)
				return false;
		}
		if (requireCore) {
			ItemStack coreStack = container.getItem(1);
			if (!coreStack.is(StatueTags.STATUE_CORE)) {
				return false;
			}
		}

		if (this.catalysts.isEmpty()) {
			for (int j = 2; j < 6; ++j) {
				ItemStack itemstack = container.getItem(j);
				if (!itemstack.isEmpty()) {
					return false;
				}
			}

			return true;
		}

		StackedContents stackedcontents = new StackedContents();
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int i = 0;

		for (int j = 2; j < 6; ++j) {
			ItemStack itemstack = container.getItem(j);
			if (!itemstack.isEmpty()) {
				++i;
				if (isSimple)
					stackedcontents.accountStack(itemstack, 1);
				else inputs.add(itemstack);
			}
		}

		return i == this.catalysts.size() && (isSimple ? stackedcontents.canCraft(this, (IntList) null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.catalysts) != null);
	}

	@Override
	public ItemStack assemble(Container container) {
		return this.getResultItem().copy();
	}

	@Override
	public boolean canCraftInDimensions(int x, int y) {
		return false;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	/**
	 * @return the first result item
	 */
	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return StatuesRecipes.UPGRADE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return StatuesRecipes.UPGRADE_RECIPE.get();
	}

	public static class Serializer implements RecipeSerializer<UpgradeRecipe> {

		@Override
		public UpgradeRecipe fromJson(ResourceLocation recipeID, JsonObject jsonObject) {
			String s = GsonHelper.getAsString(jsonObject, "group", "");
			JsonElement jsonelement = (JsonElement) (GsonHelper.isArrayNode(jsonObject, "center") ?
					GsonHelper.getAsJsonArray(jsonObject, "center") :
					GsonHelper.getAsJsonObject(jsonObject, "center"));

			Ingredient center = Ingredient.fromJson(jsonelement);
			NonNullList<Ingredient> catalystList = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "catalysts"));
			String type = GsonHelper.getAsString(jsonObject, "upgradeType", "crafting");
			UpgradeType upgradeType = UpgradeType.valueOf(type.toUpperCase());
			if (catalystList.isEmpty() && upgradeType != UpgradeType.UPGRADE) {
				throw new JsonParseException("No catalysts for upgrade recipe");
			} else if (catalystList.size() > 4) {
				throw new JsonParseException("Too many catalysts for upgrade recipe. The maximum is " + 4);
			} else {
				ItemStack resultStack;
				if (jsonObject.has("result")) {
					if (jsonObject.get("result").isJsonObject())
						resultStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
					else {
						String s1 = GsonHelper.getAsString(jsonObject, "result");
						ResourceLocation resourcelocation = new ResourceLocation(s1);
						resultStack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() ->
								new IllegalStateException("Item: " + s1 + " does not exist")));
					}
				} else {
					resultStack = ItemStack.EMPTY;
				}
				boolean requireCore = GsonHelper.getAsBoolean(jsonObject, "requireCore", false);
				int tier = GsonHelper.getAsInt(jsonObject, "tier", -1);

				return new UpgradeRecipe(recipeID, s, center, catalystList, resultStack, requireCore, upgradeType, tier);
			}
		}

		private static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for (int i = 0; i < jsonArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i));
				nonnulllist.add(ingredient);
			}

			return nonnulllist;
		}

		@Override
		public @Nullable UpgradeRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf(32767);
			Ingredient center = Ingredient.fromNetwork(byteBuf);

			int i = byteBuf.readVarInt();
			NonNullList<Ingredient> catalist = NonNullList.withSize(i, Ingredient.EMPTY); //You get it? As it's a list of catalysts

			for (int j = 0; j < catalist.size(); ++j) {
				catalist.set(j, Ingredient.fromNetwork(byteBuf));
			}

			ItemStack result = byteBuf.readItem();
			boolean requireCore = byteBuf.readBoolean();
			int type = byteBuf.readVarInt();
			UpgradeType upgradeType = UpgradeType.values()[type];
			int tier = byteBuf.readVarInt();

			return new UpgradeRecipe(recipeID, s, center, catalist, result, requireCore, upgradeType, tier);
		}

		@Override
		public void toNetwork(FriendlyByteBuf byteBuf, UpgradeRecipe recipe) {
			byteBuf.writeUtf(recipe.group);
			recipe.center.toNetwork(byteBuf);
			byteBuf.writeVarInt(recipe.catalysts.size());

			for (Ingredient ingredient : recipe.catalysts) {
				ingredient.toNetwork(byteBuf);
			}

			byteBuf.writeItem(recipe.result);

			byteBuf.writeBoolean(recipe.requireCore);
			byteBuf.writeVarInt(recipe.upgradeType.ordinal());
			byteBuf.writeVarInt(recipe.tier);
		}
	}
}
