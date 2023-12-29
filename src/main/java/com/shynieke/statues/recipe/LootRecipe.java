package com.shynieke.statues.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LootRecipe implements Recipe<Container> {
	protected final String group;
	protected final Ingredient ingredient;
	protected final ItemStack result, result2, result3;
	protected final float resultChance, result2Chance, result3Chance;
	protected final boolean showNotification;

	public LootRecipe(String group, Ingredient ingredient, ItemStack stack, float resultChance,
					  ItemStack stack2, float result2Chance, ItemStack stack3, float result3Chance, boolean showNotification) {
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
	public boolean matches(Container container, Level level) {
		return this.getIngredients().get(0).test(container.getItem(0));
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

	/**
	 * @return the first result's drop chance
	 */
	public float getChance1() {
		return resultChance;
	}

	/**
	 * @return the second result item
	 */
	public ItemStack getResultItem2(RegistryAccess access) {
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
	public ItemStack getResultItem3(RegistryAccess access) {
		return this.result3;
	}

	/**
	 * @return the third result's drop chance
	 */
	public float getChance3() {
		return result3Chance;
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
		private static final Codec<LootRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("result", ItemStack.EMPTY).forGetter(recipe -> recipe.result),
								Codec.FLOAT.optionalFieldOf("result_chance", 1.0F).forGetter(recipe -> recipe.resultChance),
								ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("result2", ItemStack.EMPTY).forGetter(recipe -> recipe.result2),
								Codec.FLOAT.optionalFieldOf("result_chance2", 0.5F).forGetter(recipe -> recipe.result2Chance),
								ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("result3", ItemStack.EMPTY).forGetter(recipe -> recipe.result3),
								Codec.FLOAT.optionalFieldOf("result_chance3", 0.1F).forGetter(recipe -> recipe.result3Chance),
								ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(recipe -> recipe.showNotification)
						)
						.apply(instance, LootRecipe::new)
		);

		@Override
		public Codec<LootRecipe> codec() {
			return CODEC;
		}

		@Override
		public @Nullable LootRecipe fromNetwork(FriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(byteBuf);
			ItemStack result1 = byteBuf.readItem();
			float chance1 = byteBuf.readFloat();
			ItemStack result2 = byteBuf.readItem();
			float chance2 = byteBuf.readFloat();
			ItemStack result3 = byteBuf.readItem();
			float chance3 = byteBuf.readFloat();
			boolean showNotification = byteBuf.readBoolean();
			return new LootRecipe(s, ingredient, result1, chance1, result2, chance2, result3, chance3, showNotification);
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
			byteBuf.writeBoolean(recipe.showNotification);
		}
	}
}
