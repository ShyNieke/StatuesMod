package com.svennieke.statues;

import com.svennieke.statues.init.StatuesItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class StatuesTab extends CreativeTabs{
	
	public StatuesTab() {
		super(Reference.MOD_ID);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(StatuesItems.core);
	}
}
