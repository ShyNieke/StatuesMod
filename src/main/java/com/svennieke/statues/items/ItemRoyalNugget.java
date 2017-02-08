package com.svennieke.statues.items;

import com.svennieke.statues.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemRoyalNugget extends ItemFood {
	public ItemRoyalNugget(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setUnlocalizedName(Reference.StatuesItems.ROYALNUGGET.getUnlocalisedName());
		setRegistryName(Reference.StatuesItems.ROYALNUGGET.getRegistryName());
		setCreativeTab(CreativeTabs.FOOD);
	}
}
