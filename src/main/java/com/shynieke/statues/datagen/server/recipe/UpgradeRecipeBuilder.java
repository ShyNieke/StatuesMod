package com.shynieke.statues.datagen.server.recipe;

import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.recipe.UpgradeType;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpgradeRecipeBuilder implements RecipeBuilder {
	private final Ingredient center;
	private final Ingredient core;
	private final List<Ingredient> catalysts = new ArrayList<>();
	private ItemStack result = ItemStack.EMPTY;
	private boolean requireCore = false;
	private UpgradeType upgradeType = UpgradeType.CRAFTING;
	private int tier = -1;
	private String group;
	private boolean showNotification = true;

	private UpgradeRecipeBuilder(Ingredient center, Ingredient core) {
		this.center = center;
		this.core = core;
	}

	public static UpgradeRecipeBuilder upgrade(Ingredient statueIngredient, Ingredient core, List<Ingredient> catalysts) {
		UpgradeRecipeBuilder builder = new UpgradeRecipeBuilder(statueIngredient, core);
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

	public void save(RecipeOutput recipeOutput, Identifier recipeID) {
		save(recipeOutput, ResourceKey.create(Registries.RECIPE, recipeID));
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
		UpgradeRecipe upgradeRecipe = new UpgradeRecipe(
				Objects.requireNonNullElse(this.group, ""),
				center, catalysts, core, result, requireCore, upgradeType, tier, showNotification);

		recipeOutput.accept(resourceKey, upgradeRecipe, null);
	}
}
