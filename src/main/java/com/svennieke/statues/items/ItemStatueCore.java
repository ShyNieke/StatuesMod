package com.svennieke.statues.items;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;

import net.minecraft.item.Item;

public class ItemStatueCore extends Item{
	public ItemStatueCore(String unlocalised) {
		setUnlocalizedName(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(Statues.instance.tabStatues);
	}
}
