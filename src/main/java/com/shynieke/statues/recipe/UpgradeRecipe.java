package com.shynieke.statues.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.datacomponent.StatueUpgrades;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.registry.StatueDataComponents;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UpgradeRecipe implements Recipe<RecipeInput> {
	protected final String group;
	protected final Ingredient center;
	protected final List<Ingredient> catalysts;
	protected final ItemStack result;
	protected final boolean requireCore;
	private final UpgradeType upgradeType;
	private final int tier;
	protected final boolean showNotification;

	public UpgradeRecipe(String group, Ingredient center, List<Ingredient> catalysts,
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
			nonnulllist.add(Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(StatueTags.STATUE_CORE)));
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

	public List<Ingredient> getCatalysts() {
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

	public MutableComponent getUpgradeName() {
		String descriptionID = "statues.upgrade." + getUpgradeType().getSerializedName() + ".name";
		MutableComponent component = Component.translatable("statues.upgrade.upgrade_type").withStyle(ChatFormatting.YELLOW);
		component.append(" : ");
		component = component.append(Component.translatable(descriptionID).withStyle(ChatFormatting.GRAY));

		if (getTier() > 0) {
			component.append(" ").append(Component.translatable("enchantment.level." + getTier()));
		}
		return component;
	}

	@Override
	public boolean matches(RecipeInput recipeInput, Level level) {
		ItemStack statueStack = recipeInput.getItem(0);
		if (!center.test(statueStack)) {
			return false;
		}
		if (statueStack.getItem() instanceof StatueBlockItem) {
			boolean upgraded = statueStack.getOrDefault(StatueDataComponents.UPGRADED, false);
			StatueStats stats = statueStack.getOrDefault(StatueDataComponents.STATS, StatueStats.empty());
			if (upgradeType.requiresUpgrade()) {
				//Check if it hasn't been upgraded
				if (!upgraded || stats.upgradeSlots() < 1)
					return false;
			} else {
				//Check if it has been upgraded
				if (upgradeType == UpgradeType.UPGRADE && upgraded)
					return false;
			}

			StatueUpgrades statueUpgrades = statueStack.getOrDefault(StatueDataComponents.UPGRADES, StatueUpgrades.empty());
			if (tier != -1 && tier != statueUpgrades.getUpgradeLevel(upgradeType.name().toLowerCase(Locale.ROOT))) {
				return false;
			}
			if (statueUpgrades.getUpgradeLevel(upgradeType.name().toLowerCase(Locale.ROOT)) >= upgradeType.getCap()) {
				return false;
			}
		}
		if (requireCore) {
			ItemStack coreStack = recipeInput.getItem(1);
			if (!coreStack.is(StatueTags.STATUE_CORE)) {
				return false;
			}
		}

		if (this.catalysts.isEmpty()) {
			for (int j = 2; j < 6; ++j) {
				ItemStack itemstack = recipeInput.getItem(j);
				if (!itemstack.isEmpty()) {
					return false;
				}
			}

			return true;
		}

		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int itemCount = 0;

		for (int j = 2; j < 6; ++j) {
			ItemStack itemstack = recipeInput.getItem(j);
			if (!itemstack.isEmpty()) {
				++itemCount;
				inputs.add(itemstack);
			}
		}

		return itemCount == this.catalysts.size() && RecipeMatcher.findMatches(inputs, this.catalysts) != null;
	}

	@Override
	public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider lookupProvider) {
		return this.getResultItem(lookupProvider).copy();
	}

//	@Override
//	public boolean canCraftInDimensions(int x, int y) {
//		return false;
//	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	/**
	 * @return the first result item
	 */
	public ItemStack getResultItem(HolderLookup.Provider lookupProvider) {
		return this.result;
	}

	@Override
	public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
		return StatuesRecipes.UPGRADE_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends Recipe<RecipeInput>> getType() {
		return StatuesRecipes.UPGRADE_RECIPE.get();
	}

	@Override
	public PlacementInfo placementInfo() {
		return null;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return null;
	}

	public static class Serializer implements RecipeSerializer<UpgradeRecipe> {
		private static final MapCodec<UpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC.fieldOf("center").forGetter(recipe -> recipe.center),
								Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(0, 4)).fieldOf("catalysts").forGetter(p_360071_ -> p_360071_.catalysts),
								ItemStack.SINGLE_ITEM_CODEC.optionalFieldOf("result", ItemStack.EMPTY).forGetter(recipe -> recipe.result),
								Codec.BOOL.optionalFieldOf("requireCore", false).forGetter(recipe -> recipe.requireCore),
								UpgradeType.CODEC.optionalFieldOf("upgradeType", UpgradeType.CRAFTING).forGetter(recipe -> recipe.upgradeType),
								Codec.INT.optionalFieldOf("tier", -1).forGetter(recipe -> recipe.tier),
								Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(recipe -> recipe.showNotification)
						)
						.apply(instance, UpgradeRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, UpgradeRecipe> STREAM_CODEC = StreamCodec.of(
				UpgradeRecipe.Serializer::toNetwork, UpgradeRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<UpgradeRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, UpgradeRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		public static UpgradeRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf(32767);
			Ingredient center = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);

			int i = byteBuf.readVarInt();
			List<Ingredient> catalist = new ArrayList<>();

			for (int j = 0; j < i; ++j) {
				catalist.set(j, Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf));
			}

			ItemStack result = ItemStack.OPTIONAL_STREAM_CODEC.decode(byteBuf);
			boolean requireCore = byteBuf.readBoolean();
			int type = byteBuf.readVarInt();
			UpgradeType upgradeType = UpgradeType.values()[type];
			int tier = byteBuf.readVarInt();
			boolean showNotification = byteBuf.readBoolean();

			return new UpgradeRecipe(s, center, catalist, result, requireCore, upgradeType, tier, showNotification);
		}

		public static void toNetwork(RegistryFriendlyByteBuf byteBuf, UpgradeRecipe recipe) {
			byteBuf.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, recipe.center);
			byteBuf.writeVarInt(recipe.catalysts.size());

			for (Ingredient ingredient : recipe.catalysts) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, ingredient);
			}

			ItemStack.OPTIONAL_STREAM_CODEC.encode(byteBuf, recipe.result);

			byteBuf.writeBoolean(recipe.requireCore);
			byteBuf.writeVarInt(recipe.upgradeType.ordinal());
			byteBuf.writeVarInt(recipe.tier);
			byteBuf.writeBoolean(recipe.showNotification);
		}
	}
}

