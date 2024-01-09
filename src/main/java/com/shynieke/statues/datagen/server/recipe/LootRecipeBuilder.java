package com.shynieke.statues.datagen.server.recipe;

import com.shynieke.statues.recipe.LootRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LootRecipeBuilder implements RecipeBuilder {
	private final Ingredient statueIngredient;
	private ItemStack result = ItemStack.EMPTY;
	private ItemStack result2 = ItemStack.EMPTY;
	private ItemStack result3 = ItemStack.EMPTY;
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
		this.result = new ItemStack(resultIn.asItem());
		return this;
	}

	public LootRecipeBuilder result1(ItemStack resultIn) {
		this.result = resultIn;
		return this;
	}

	public LootRecipeBuilder result1(ItemLike resultIn, float chance) {
		this.result = new ItemStack(resultIn.asItem());
		this.result1Chance = chance;
		return this;
	}

	public LootRecipeBuilder result1(ItemStack resultIn, float chance) {
		this.result = resultIn;
		this.result1Chance = chance;
		return this;
	}

	public LootRecipeBuilder result2(ItemLike result2In) {
		this.result2 = new ItemStack(result2In.asItem());
		return this;
	}

	public LootRecipeBuilder result2(ItemStack result2In) {
		this.result2 = result2In;
		return this;
	}

	public LootRecipeBuilder result2(ItemLike result2In, float chance2) {
		this.result2 = new ItemStack(result2In.asItem());
		this.result2Chance = chance2;
		return this;
	}

	public LootRecipeBuilder result2(ItemStack result2In, float chance2) {
		this.result2 = result2In;
		this.result2Chance = chance2;
		return this;
	}

	public LootRecipeBuilder result3(ItemLike result3In) {
		this.result3 = new ItemStack(result3In.asItem());
		return this;
	}

	public LootRecipeBuilder result3(ItemStack result3In) {
		this.result3 = result3In;
		return this;
	}

	public LootRecipeBuilder result3(ItemLike result3In, float chance3) {
		this.result3 = new ItemStack(result3In.asItem());
		this.result3Chance = chance3;
		return this;
	}

	public LootRecipeBuilder result3(ItemStack result3In, float chance3) {
		this.result3 = result3In;
		this.result3Chance = chance3;
		return this;
	}

	public LootRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
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
	public Item getResult() {
		return Items.AIR;
	}

	@Override
	public void save(RecipeOutput recipeOutput) {
		ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(this.statueIngredient.getItems()[0].getItem());
		ResourceLocation recipeID = new ResourceLocation(itemKey.getNamespace(), "loot/" + itemKey.getPath());

		save(recipeOutput, recipeID);
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
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

		recipeOutput.accept(id, lootRecipe, null);
	}
}
