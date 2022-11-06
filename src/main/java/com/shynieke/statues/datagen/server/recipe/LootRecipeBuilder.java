package com.shynieke.statues.datagen.server.recipe;

import com.google.gson.JsonObject;
import com.shynieke.statues.recipe.StatuesRecipes;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class LootRecipeBuilder {
	private final Ingredient statueIngredient;
	private ItemStack result, result2, result3 = ItemStack.EMPTY;
	private float result1Chance = 1.0F;
	private float result2Chance = 0.5F;
	private float result3Chance = 0.1F;
	private String group;

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

	public void build(Consumer<FinishedRecipe> consumerIn) {
		this.build(consumerIn, Registry.ITEM.getKey(this.statueIngredient.getItems()[0].getItem()));
	}

	public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new LootRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.statueIngredient,
				result, result1Chance, result2, result2Chance, result3, result3Chance));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final Ingredient statueIngredient;
		private final ItemStack result, result2, result3;
		private final float result1Chance, result2Chance, result3Chance;

		public Result(ResourceLocation idIn, String groupIn, Ingredient statueIngredient,
					  ItemStack result, float result1Chance,
					  ItemStack result2, float result2Chance,
					  ItemStack result3, float result3Chance) {
			this.id = idIn;
			this.group = groupIn;
			this.statueIngredient = statueIngredient;
			this.result = result;
			this.result1Chance = result1Chance;
			this.result2 = result2;
			this.result2Chance = result2Chance;
			this.result3 = result3;
			this.result3Chance = result3Chance;
		}

		public void serializeRecipeData(JsonObject json) {
			if (!this.group.isEmpty()) {
				json.addProperty("group", this.group);
			}

			json.add("ingredient", this.statueIngredient.toJson());

			addStack(json, "result1", this.result, this.result1Chance);
			addStack(json, "result2", this.result2, this.result2Chance);
			addStack(json, "result3", this.result3, this.result3Chance);
		}

		private void addStack(JsonObject json, String property, ItemStack stack, float chance) {
			if (stack != null && !stack.isEmpty()) {
				JsonObject object = new JsonObject();

				object.addProperty("item", Registry.ITEM.getKey(stack.getItem()).toString());
				if (stack.getCount() != 1) {
					object.addProperty("count", stack.getCount());
				}
				if (stack.hasTag()) {
					object.addProperty("tag", stack.getTag().toString());
				}
				json.add(property, object);
				json.addProperty(property + "Chance", chance);
			}
		}

		/**
		 * Gets the ID for the recipe.
		 */
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return StatuesRecipes.LOOT_SERIALIZER.get();
		}

		/**
		 * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
		 */
		@Nullable
		public JsonObject serializeAdvancement() {
			return null;
		}

		/**
		 * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementId()}
		 * is non-null.
		 */
		@Nullable
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}
