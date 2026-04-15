package com.shynieke.statues.datagen.server.recipe;

import com.shynieke.statues.Reference;
import com.shynieke.statues.recipe.LootRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class LootRecipeBuilder implements RecipeBuilder {
	private final Ingredient statueIngredient;
	private Optional<ItemStackTemplate> result = Optional.empty();
	private Optional<ItemStackTemplate> result2 = Optional.empty();
	private Optional<ItemStackTemplate> result3 = Optional.empty();
	private float result1Chance = 1.0F;
	private float result2Chance = 0.5F;
	private float result3Chance = 0.1F;
	private String group;
	private boolean showNotification = true;

	private LootRecipeBuilder(Ingredient statueIngredient) {
		this.statueIngredient = statueIngredient;
	}

	public static LootRecipeBuilder loot(Ingredient statueIngredient) {
		return new LootRecipeBuilder(statueIngredient);
	}

	public LootRecipeBuilder result1(ItemLike resultIn) {
		this.result = Optional.of(new ItemStackTemplate(resultIn.asItem()));
		return this;
	}

	public LootRecipeBuilder result1(ItemStackTemplate resultIn) {
		this.result = Optional.of(resultIn);
		return this;
	}

	public LootRecipeBuilder result1(ItemLike resultIn, float chance) {
		this.result = Optional.of(new ItemStackTemplate(resultIn.asItem()));
		this.result1Chance = chance;
		return this;
	}

	public LootRecipeBuilder result1(ItemStackTemplate resultIn, float chance) {
		this.result = Optional.of(resultIn);
		this.result1Chance = chance;
		return this;
	}

	public LootRecipeBuilder result2(ItemLike result2In) {
		this.result2 = Optional.of(new ItemStackTemplate(result2In.asItem()));
		return this;
	}

	public LootRecipeBuilder result2(ItemStackTemplate result2In) {
		this.result2 = Optional.of(result2In);
		return this;
	}

	public LootRecipeBuilder result2(ItemLike result2In, float chance2) {
		this.result2 = Optional.of(new ItemStackTemplate(result2In.asItem()));
		this.result2Chance = chance2;
		return this;
	}

	public LootRecipeBuilder result2(ItemStackTemplate result2In, float chance2) {
		this.result2 = Optional.of(result2In);
		this.result2Chance = chance2;
		return this;
	}

	public LootRecipeBuilder result3(ItemLike result3In) {
		this.result3 = Optional.of(new ItemStackTemplate(result3In.asItem()));
		return this;
	}

	public LootRecipeBuilder result3(ItemStackTemplate result3In) {
		this.result3 = Optional.of(result3In);
		return this;
	}

	public LootRecipeBuilder result3(ItemLike result3In, float chance3) {
		this.result3 = Optional.of(new ItemStackTemplate(result3In.asItem()));
		this.result3Chance = chance3;
		return this;
	}

	public LootRecipeBuilder result3(ItemStackTemplate result3In, float chance3) {
		this.result3 = Optional.of(result3In);
		this.result3Chance = chance3;
		return this;
	}

	public LootRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return ResourceKey.create(Registries.RECIPE, Reference.modLoc("please_provide_a_recipe_id"));
	}

	public LootRecipeBuilder setShowNotification(boolean showNotification) {
		this.showNotification = showNotification;
		return this;
	}

	@Override
	public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
		return null;
	}

	@Override
	public void save(RecipeOutput recipeOutput) {
		Identifier itemKey = this.statueIngredient.getValues().get(0).getKey().identifier();
		Identifier recipeID = Identifier.fromNamespaceAndPath(itemKey.getNamespace(), "loot/" + itemKey.getPath());

		save(recipeOutput, ResourceKey.create(Registries.RECIPE, recipeID));
	}

	static Identifier getDefaultRecipeId(ItemLike itemLike) {
		return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
	}

	public void save(RecipeOutput recipeOutput, Identifier recipeID) {
		save(recipeOutput, ResourceKey.create(Registries.RECIPE, recipeID));
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
		LootRecipe lootRecipe = new LootRecipe(
				Objects.requireNonNullElse(this.group, ""),
				this.statueIngredient,
				this.result,
				this.result1Chance,
				this.result2,
				this.result2Chance,
				this.result3,
				this.result3Chance,
				this.showNotification);

		recipeOutput.accept(resourceKey, lootRecipe, null);
	}
}
