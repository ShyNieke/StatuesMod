package com.shynieke.statues.compat.jei.filling;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.ingredients.Ingredients;
import net.minecraft.item.ItemStack;

public class StatueFillingHandler implements IRecipeHandler<StatueFillingWrapper> {

	@Override
	public Class<StatueFillingWrapper> getRecipeClass() {
		return StatueFillingWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid(StatueFillingWrapper recipe) {
		return StatueFillingCategory.UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(StatueFillingWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(StatueFillingWrapper recipe) {
		IIngredients ingredients = new Ingredients();
		recipe.getIngredients(ingredients);
		return ingredients.getInputs(ItemStack.class).size() > 0 && ingredients.getOutputs(ItemStack.class).size() > 0;
	}
}
