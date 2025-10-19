package com.shynieke.statues.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MultipleRecipeInput(List<ItemStack> items) implements RecipeInput {

	@NotNull
	@Override
	public ItemStack getItem(int index) {
		if (index >= 0 && index < this.items.size()) {
			return this.items.get(index);
		} else {
			throw new IllegalArgumentException("No item for index " + index);
		}
	}

	@Override
	public int size() {
		return this.items.size();
	}
}