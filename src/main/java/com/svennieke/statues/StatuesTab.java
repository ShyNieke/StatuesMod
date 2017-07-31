package com.svennieke.statues;

import java.util.Arrays;
import java.util.List;

import com.svennieke.statues.init.StatuesItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class StatuesTab extends CreativeTabs{
	
	public StatuesTab() {
		super(Reference.MOD_ID);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(StatuesItems.core);
	}
}
