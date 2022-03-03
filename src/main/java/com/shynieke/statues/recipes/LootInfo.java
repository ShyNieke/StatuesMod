package com.shynieke.statues.recipes;

import net.minecraft.world.item.ItemStack;

public class LootInfo {
	public static final LootInfo EMPTY = new LootInfo(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
	private ItemStack stack1;
	private ItemStack stack2;
	private ItemStack stack3;

	public LootInfo(ItemStack stack1, ItemStack stack2, ItemStack stack3) {
		this.stack1 = stack1;
		this.stack2 = stack2;
		this.stack3 = stack3;
	}

	public ItemStack getStack1() {
		return stack1;
	}

	public void setStack1(ItemStack stack1) {
		this.stack1 = stack1;
	}

	public ItemStack getStack2() {
		return stack2;
	}

	public void setStack2(ItemStack stack2) {
		this.stack2 = stack2;
	}

	public ItemStack getStack3() {
		return stack3;
	}

	public void setStack3(ItemStack stack3) {
		this.stack3 = stack3;
	}

	public boolean hasLoot() {
		return !stack1.isEmpty() && !stack2.isEmpty() && !stack3.isEmpty();
	}
}
