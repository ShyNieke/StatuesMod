package com.shynieke.statues.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LootRecipe implements Recipe<Container> {
	protected final ResourceLocation id;
	protected final String group;
	protected final Ingredient ingredient;
	protected final ItemStack result, result2, result3;
	protected final float resultChance, result2Chance, result3Chance;

	public LootRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack stack, float resultChance,
					  ItemStack stack2, float result2Chance, ItemStack stack3, float result3Chance) {
		this.id = id;
		this.group = group;
		this.ingredient = ingredient;
		this.result = stack;
		this.resultChance = resultChance;
		this.result2 = stack2;
		this.result2Chance = result2Chance;
		this.result3 = stack3;
		this.result3Chance = result3Chance;
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.ingredient);
		return nonnulllist;
	}


	@Override
	public boolean matches(Container container, Level level) {
		return this.getIngredients().get(0).test(container.getItem(0));
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

	/**
	 * @return the first result's drop chance
	 */
	public float getChance1() {
		return resultChance;
	}

	/**
	 * @return the second result item
	 */
	public ItemStack getResultItem2() {
		return this.result2;
	}

	/**
	 * @return the secpmd result's drop chance
	 */
	public float getChance2() {
		return result2Chance;
	}

	/**
	 * @return the third result item
	 */
	public ItemStack getResultItem3() {
		return this.result3;
	}

	/**
	 * @return the third result's drop chance
	 */
	public float getChance3() {
		return result3Chance;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return StatuesRecipes.LOOT_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return StatuesRecipes.LOOT_RECIPE.get();
	}

	public static class Serializer implements RecipeSerializer<LootRecipe> {

		@Override
		public LootRecipe fromJson(ResourceLocation recipeID, JsonObject jsonObject) {
			String s = GsonHelper.getAsString(jsonObject, "group", "");
			JsonElement jsonelement = (JsonElement) (GsonHelper.isArrayNode(jsonObject, "ingredient") ?
					GsonHelper.getAsJsonArray(jsonObject, "ingredient") :
					GsonHelper.getAsJsonObject(jsonObject, "ingredient"));

			Ingredient ingredient = Ingredient.fromJson(jsonelement);

			ItemStack[] stacks = new ItemStack[3];
			float[] chances = new float[3];
			for (int i = 0; i < 3; i++) {
				String resultName = "result" + (i + 1);
				if (jsonObject.has(resultName)) {
					if (jsonObject.get(resultName).isJsonObject())
						stacks[i] = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, resultName));
					else {
						String s1 = GsonHelper.getAsString(jsonObject, resultName);
						ResourceLocation resourcelocation = new ResourceLocation(s1);
						stacks[i] = new ItemStack(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() ->
								new IllegalStateException("Item: " + s1 + " does not exist")));
					}
				} else {
					stacks[i] = ItemStack.EMPTY;
				}

				float defaultChance;
				switch (i) {
					default -> defaultChance = 1.0F;
					case 1 -> defaultChance = 0.5F;
					case 2 -> defaultChance = 0.1F;
				}
				chances[i] = GsonHelper.getAsFloat(jsonObject, resultName + "Chance", defaultChance);

			}

			return new LootRecipe(recipeID, s, ingredient, stacks[0], chances[0], stacks[1], chances[1], stacks[2], chances[2]);
		}

		@Override
		public @Nullable LootRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(byteBuf);
			ItemStack result1 = byteBuf.readItem();
			float chance1 = byteBuf.readFloat();
			ItemStack result2 = byteBuf.readItem();
			float chance2 = byteBuf.readFloat();
			ItemStack result3 = byteBuf.readItem();
			float chance3 = byteBuf.readFloat();
			return new LootRecipe(recipeID, s, ingredient, result1, chance1, result2, chance2, result3, chance3);
		}

		@Override
		public void toNetwork(FriendlyByteBuf byteBuf, LootRecipe recipe) {
			byteBuf.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(byteBuf);
			byteBuf.writeItem(recipe.result);
			byteBuf.writeFloat(recipe.resultChance);
			byteBuf.writeItem(recipe.result2);
			byteBuf.writeFloat(recipe.result2Chance);
			byteBuf.writeItem(recipe.result3);
			byteBuf.writeFloat(recipe.result3Chance);
		}
	}
}
