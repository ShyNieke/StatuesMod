package com.shynieke.statues.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.registry.StatueTags;
import com.shynieke.statues.util.UpgradeHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class UpgradeRecipe implements Recipe<Container> {
	protected final String group;
	protected final Ingredient center;
	protected final NonNullList<Ingredient> catalysts;
	protected final ItemStack result;
	protected final boolean requireCore;
	private final UpgradeType upgradeType;
	private final int tier;
	protected final boolean showNotification;

	public UpgradeRecipe(String group, Ingredient center, NonNullList<Ingredient> catalysts,
						 ItemStack stack, boolean requireCore, UpgradeType upgradeType, int tier, boolean showNotification) {
		this.group = group;
		this.center = center;
		this.catalysts = catalysts;
		this.result = stack;
		this.requireCore = requireCore;
		this.upgradeType = upgradeType;
		this.tier = tier;
		this.showNotification = showNotification;
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(center);
		if (requireCore)
			nonnulllist.add(Ingredient.of(StatueTags.STATUE_CORE));
		nonnulllist.addAll(catalysts);
		return nonnulllist;
	}

	@Override
	public boolean showNotification() {
		return this.showNotification;
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
		if (statueStack.getItem() instanceof StatueBlockItem) {
			CompoundTag compoundtag = statueStack.getTagElement("BlockEntityTag");
			if (upgradeType.requiresUpgrade()) {
				//Check if it hasn't been upgraded
				if (compoundtag == null || !compoundtag.contains(Reference.UPGRADED) || compoundtag.getInt(Reference.UPGRADE_SLOTS) < 1)
					return false;
			} else {
				//Check if it has been upgraded
				if (upgradeType == UpgradeType.UPGRADE && compoundtag != null && compoundtag.contains(Reference.UPGRADED))
					return false;
			}

			if (tier != -1 && tier != UpgradeHelper.getUpgradeLevel(statueStack, upgradeType.name().toLowerCase(Locale.ROOT))) {
				return false;
			}
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

		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int itemCount = 0;

		for (int j = 2; j < 6; ++j) {
			ItemStack itemstack = container.getItem(j);
			if (!itemstack.isEmpty()) {
				++itemCount;
				inputs.add(itemstack);
			}
		}

		return itemCount == this.catalysts.size() && RecipeMatcher.findMatches(inputs, this.catalysts) != null;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess access) {
		return this.getResultItem(access).copy();
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
	public ItemStack getResultItem(RegistryAccess access) {
		return this.result;
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
		private static final Codec<UpgradeRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC_NONEMPTY.fieldOf("center").forGetter(recipe -> recipe.center),
								Ingredient.CODEC_NONEMPTY
										.listOf()
										.fieldOf("catalysts")
										.flatXmap(
												array -> {
													Ingredient[] aingredient = array
															.toArray(Ingredient[]::new); //Forge skip the empty check and immediately create the array.
													return aingredient.length > 4
															? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(4))
															: DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
												},
												DataResult::success
										)
										.forGetter(recipe -> recipe.catalysts),
								ExtraCodecs.strictOptionalField(ItemStack.SINGLE_ITEM_CODEC, "result", ItemStack.EMPTY).forGetter(recipe -> recipe.result),
								ExtraCodecs.strictOptionalField(Codec.BOOL, "requireCore", false).forGetter(recipe -> recipe.requireCore),
								UpgradeType.CODEC.optionalFieldOf("upgradeType", UpgradeType.CRAFTING).forGetter(recipe -> recipe.upgradeType),
								Codec.INT.optionalFieldOf("tier", -1).forGetter(recipe -> recipe.tier),
								ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(recipe -> recipe.showNotification)
						)
						.apply(instance, UpgradeRecipe::new)
		);

		@Override
		public Codec<UpgradeRecipe> codec() {
			return CODEC;
		}

		@Override
		public @Nullable UpgradeRecipe fromNetwork(FriendlyByteBuf byteBuf) {
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
			boolean showNotification = byteBuf.readBoolean();

			return new UpgradeRecipe(s, center, catalist, result, requireCore, upgradeType, tier, showNotification);
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
			byteBuf.writeBoolean(recipe.showNotification);
		}
	}
}

