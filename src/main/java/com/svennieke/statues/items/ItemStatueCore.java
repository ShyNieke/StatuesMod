package com.svennieke.statues.items;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;

import net.minecraft.item.Item;

public class ItemStatueCore extends Item{
	public ItemStatueCore() {
		setUnlocalizedName(Reference.StatuesItems.STATUECORE.getUnlocalisedName());
		setRegistryName(Reference.StatuesItems.STATUECORE.getRegistryName());
		setCreativeTab(Statues.instance.tabStatues);
	}
}
