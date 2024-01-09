package com.shynieke.statues.datagen.server.recipe;

import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.recipe.UpgradeType;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class UpgradeRecipeBuilder implements RecipeBuilder {
	private final Ingredient center;
	private final NonNullList<Ingredient> catalysts = NonNullList.create();
	private ItemStack result = ItemStack.EMPTY;
	private boolean requireCore = false;
	private UpgradeType upgradeType = UpgradeType.CRAFTING;
	private int tier = -1;
	private String group;
	private boolean showNotification = true;

	private UpgradeRecipeBuilder(Ingredient center) {
		this.center = center;
	}

	public static UpgradeRecipeBuilder upgrade(Ingredient statueIngredient, List<Ingredient> catalysts) {
		UpgradeRecipeBuilder builder = new UpgradeRecipeBuilder(statueIngredient);
		builder.catalysts.addAll(catalysts);
		return builder;
	}

	public UpgradeRecipeBuilder result(ItemLike resultIn) {
		this.result = new ItemStack(resultIn.asItem());
		return this;
	}

	public UpgradeRecipeBuilder result(ItemStack resultIn) {
		this.result = resultIn;
		return this;
	}

	public UpgradeRecipeBuilder upgradeType(UpgradeType upgradeType) {
		this.upgradeType = upgradeType;
		return this;
	}

	public UpgradeRecipeBuilder tier(int tier) {
		this.tier = tier;
		return this;
	}

	public UpgradeRecipeBuilder requiresCore() {
		this.requireCore = true;
		return this;
	}

	public UpgradeRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public UpgradeRecipeBuilder setShowNotification(boolean showNotification) {
		this.showNotification = showNotification;
		return this;
	}

	@Override
	public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
		return null;
	}

	@Override
	public Item getResult() {
		return result.getItem();
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		UpgradeRecipe upgradeRecipe = new UpgradeRecipe(
				Objects.requireNonNullElse(this.group, ""),
				center, catalysts, result, requireCore, upgradeType, tier, showNotification);

		recipeOutput.accept(id, upgradeRecipe, null);
	}
}
