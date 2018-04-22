package com.svennieke.statues.compat.jei.filling;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class StatueFillingWrapper extends BlankRecipeWrapper {
	private final ItemStack statue;
	private final ItemStack input;
    private final ItemStack output;

    public StatueFillingWrapper(ItemStack statue, ItemStack input, ItemStack output) {
    	this.statue = statue;
    	this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
    	ArrayList<ItemStack> inputStacks = new ArrayList<>();
    	inputStacks.add(statue);
    	inputStacks.add(input);
    	
    	ingredients.setInputs(ItemStack.class, inputStacks);	
    	ingredients.setOutput(ItemStack.class, output);
    }
}