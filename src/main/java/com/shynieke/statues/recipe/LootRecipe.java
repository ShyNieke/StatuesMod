package com.shynieke.statues.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class LootRecipe implements Recipe<RecipeInput> {

	protected final String group;
	protected final Ingredient ingredient;
	protected final Optional<ItemStackTemplate> result, result2, result3;
	protected final float resultChance, result2Chance, result3Chance;
	protected final boolean showNotification;

	public LootRecipe(String group, Ingredient ingredient, Optional<ItemStackTemplate> stack, float resultChance,
	                  Optional<ItemStackTemplate> stack2, float result2Chance,
	                  Optional<ItemStackTemplate> stack3, float result3Chance,
	                  boolean showNotification) {
		this.group = group;
		this.ingredient = ingredient;
		this.result = stack;
		this.resultChance = resultChance;
		this.result2 = stack2;
		this.result2Chance = result2Chance;
		this.result3 = stack3;
		this.result3Chance = result3Chance;
		this.showNotification = showNotification;
	}

	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.ingredient);
		return nonnulllist;
	}

	@Override
	public boolean showNotification() {
		return this.showNotification;
	}

	@Override
	public String group() {
		return group;
	}

	@Override
	public boolean matches(RecipeInput recipeInput, Level level) {
		return this.getIngredients().getFirst().test(recipeInput.getItem(0));
	}

	@Override
	public ItemStack assemble(RecipeInput recipeInput) {
		return this.getResultItem();
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
	public ItemStack getResultItem() {
		return this.result.map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
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
		return this.result2.map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
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
		return this.result3.map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
	}

	/**
	 * @return the third result's drop chance
	 */
	public float getChance3() {
		return result3Chance;
	}

	@Override
	public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
		return StatuesRecipes.LOOT_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends Recipe<RecipeInput>> getType() {
		return StatuesRecipes.LOOT_RECIPE.get();
	}

	@Override
	public PlacementInfo placementInfo() {
		return null;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return null;
	}

	public static class Serializer {
		private static final MapCodec<LootRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								ItemStackTemplate.CODEC.optionalFieldOf("result").forGetter(recipe -> recipe.result),
								Codec.FLOAT.optionalFieldOf("result_chance", 1.0F).forGetter(recipe -> recipe.resultChance),
								ItemStackTemplate.CODEC.optionalFieldOf("result2").forGetter(recipe -> recipe.result2),
								Codec.FLOAT.optionalFieldOf("result_chance2", 0.5F).forGetter(recipe -> recipe.result2Chance),
								ItemStackTemplate.CODEC.optionalFieldOf("result3").forGetter(recipe -> recipe.result3),
								Codec.FLOAT.optionalFieldOf("result_chance3", 0.1F).forGetter(recipe -> recipe.result3Chance),
								Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(recipe -> recipe.showNotification)
						)
						.apply(instance, LootRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, LootRecipe> STREAM_CODEC = StreamCodec.of(
				LootRecipe.Serializer::toNetwork, LootRecipe.Serializer::fromNetwork
		);
		public static final RecipeSerializer<LootRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

		private static LootRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf(32767);
			Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
			Optional<ItemStackTemplate> result1 = ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC).decode(byteBuf);
			float chance1 = byteBuf.readFloat();
			Optional<ItemStackTemplate> result2 = ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC).decode(byteBuf);
			float chance2 = byteBuf.readFloat();
			Optional<ItemStackTemplate> result3 = ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC).decode(byteBuf);
			float chance3 = byteBuf.readFloat();
			boolean showNotification = byteBuf.readBoolean();
			return new LootRecipe(s, ingredient, result1, chance1, result2, chance2, result3, chance3, showNotification);
		}

		private static void toNetwork(RegistryFriendlyByteBuf byteBuf, LootRecipe recipe) {
			byteBuf.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, recipe.ingredient);
			ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC).encode(byteBuf, recipe.result);
			byteBuf.writeFloat(recipe.resultChance);
			ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC).encode(byteBuf, recipe.result2);
			byteBuf.writeFloat(recipe.result2Chance);
			ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC).encode(byteBuf, recipe.result3);
			byteBuf.writeFloat(recipe.result3Chance);
			byteBuf.writeBoolean(recipe.showNotification);
		}
	}
}
