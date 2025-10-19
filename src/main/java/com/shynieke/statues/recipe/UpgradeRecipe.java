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
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
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

import java.util.List;
import java.util.Locale;

public class UpgradeRecipe implements Recipe<RecipeInput> {
	protected final String group;
	protected final Ingredient center;
	protected final List<Ingredient> catalysts;
	protected final Ingredient coreIngredient;
	protected final ItemStack result;
	protected final boolean requireCore;
	private final UpgradeType upgradeType;
	private final int tier;
	protected final boolean showNotification;

	public UpgradeRecipe(String group, Ingredient center, List<Ingredient> catalysts,
	                     Ingredient core, ItemStack stack, boolean requireCore, UpgradeType upgradeType,
	                     int tier, boolean showNotification) {
		this.group = group;
		this.center = center;
		this.catalysts = catalysts;
		this.coreIngredient = core;
		this.result = stack;
		this.requireCore = requireCore;
		this.upgradeType = upgradeType;
		this.tier = tier;
		this.showNotification = showNotification;
	}

	public Ingredient getCoreIngredient() {
		return coreIngredient;
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(center);
		if (requireCore)
			nonnulllist.add(coreIngredient);
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
		return this.getResultItem();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	/**
	 * @return the first result item
	 */
	public ItemStack getResultItem() {
		return this.result.copy();
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
								Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(0, 4)).fieldOf("catalysts").forGetter(recipe -> recipe.catalysts),
								Ingredient.CODEC.fieldOf("core").forGetter(recipe -> recipe.coreIngredient),
								ItemStack.SINGLE_ITEM_CODEC.optionalFieldOf("result", ItemStack.EMPTY).forGetter(recipe -> recipe.result),
								Codec.BOOL.optionalFieldOf("requireCore", false).forGetter(recipe -> recipe.requireCore),
								UpgradeType.CODEC.optionalFieldOf("upgradeType", UpgradeType.CRAFTING).forGetter(recipe -> recipe.upgradeType),
								Codec.INT.optionalFieldOf("tier", -1).forGetter(recipe -> recipe.tier),
								Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(recipe -> recipe.showNotification)
						)
						.apply(instance, UpgradeRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, UpgradeRecipe> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8,
				recipe -> recipe.group,
				Ingredient.CONTENTS_STREAM_CODEC,
				recipe -> recipe.center,
				Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list(4)),
				recipe -> recipe.catalysts,
				Ingredient.CONTENTS_STREAM_CODEC,
				recipe -> recipe.coreIngredient,
				ItemStack.OPTIONAL_STREAM_CODEC,
				recipe -> recipe.result,
				ByteBufCodecs.BOOL,
				recipe -> recipe.requireCore,
				UpgradeType.STREAM_CODEC,
				recipe -> recipe.upgradeType,
				ByteBufCodecs.INT,
				recipe -> recipe.tier,
				ByteBufCodecs.BOOL,
				recipe -> recipe.showNotification,
				UpgradeRecipe::new
		);

		@Override
		public MapCodec<UpgradeRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, UpgradeRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}

