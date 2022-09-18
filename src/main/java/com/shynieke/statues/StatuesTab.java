package com.shynieke.statues;

import com.shynieke.statues.init.StatuesItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class StatuesTab extends CreativeTabs {

	public StatuesTab() {
		super(Reference.MOD_ID);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(StatuesItems.core);
	}
}
