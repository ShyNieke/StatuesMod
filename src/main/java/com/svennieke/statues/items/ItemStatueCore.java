package com.svennieke.statues.items;

import com.svennieke.statues.Statues;

import net.minecraft.item.Item;

public class ItemStatueCore extends Item{
	public ItemStatueCore(String unlocalised, String registry) {
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
		setCreativeTab(Statues.instance.tabStatues);
	}
}
