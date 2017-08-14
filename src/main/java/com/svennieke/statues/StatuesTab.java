package com.svennieke.statues;

import com.svennieke.statues.init.StatuesItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class StatuesTab extends CreativeTabs{
	
	public StatuesTab() {
		super(Reference.MOD_ID);
	}
	
	@Override
	public Item getTabIconItem() {
		return StatuesItems.core;
	}
}
