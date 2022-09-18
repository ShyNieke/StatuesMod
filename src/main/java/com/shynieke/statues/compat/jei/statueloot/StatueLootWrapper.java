package com.shynieke.statues.compat.jei.statueloot;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class StatueLootWrapper extends BlankRecipeWrapper {
	private final ItemStack statue;
	private final ItemStack stack1;
	private final ItemStack stack2;
	private final ItemStack stack3;

	public StatueLootWrapper(ItemStack statue, ItemStack stack1, ItemStack stack2, ItemStack stack3) {
		this.statue = statue;
		this.stack1 = stack1;
		this.stack2 = stack2;
		this.stack3 = stack3;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<ItemStack> outputStacks = new ArrayList<>();
		outputStacks.add(stack1);
		outputStacks.add(stack2);
		outputStacks.add(stack3);

		ingredients.setInput(ItemStack.class, statue);
		ingredients.setOutputs(ItemStack.class, outputStacks);
	}
}