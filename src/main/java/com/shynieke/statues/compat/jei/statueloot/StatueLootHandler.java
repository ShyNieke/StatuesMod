package com.shynieke.statues.compat.jei.statueloot;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.ingredients.Ingredients;
import net.minecraft.item.ItemStack;

public class StatueLootHandler implements IRecipeHandler<StatueLootWrapper>{

	@Override
	public Class<StatueLootWrapper> getRecipeClass() {
		return StatueLootWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid(StatueLootWrapper recipe) {
		return StatueLootCategory.UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(StatueLootWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(StatueLootWrapper recipe) {
		IIngredients ingredients = new Ingredients();
        recipe.getIngredients(ingredients);
        return ingredients.getInputs(ItemStack.class).size() > 0 && ingredients.getOutputs(ItemStack.class).size() > 0;
	}

}
