package com.shynieke.statues.compat.list;

import net.minecraft.item.ItemStack;

public class LootInfo {
	private ItemStack stack1;
	private ItemStack stack2;
	private ItemStack stack3;
	private String statue;

	public LootInfo(ItemStack stack1, ItemStack stack2, ItemStack stack3, String statue) {
		this.stack1 = stack1;
		this.stack2 = stack2;
		this.stack3 = stack3;
		this.statue = statue;
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

	public String getStatue() {
		return statue;
	}
}
