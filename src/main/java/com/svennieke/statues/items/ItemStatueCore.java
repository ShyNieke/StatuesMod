package com.svennieke.statues.items;

import com.svennieke.statues.Statues;

import net.minecraft.item.Item;

public class ItemStatueCore extends Item{
	public ItemStatueCore(Item.Properties builder) {
		super(builder.group(Statues.tabStatues));
//		setUnlocalizedName(Reference.MOD_PREFIX + unlocalised);
//		setCreativeTab(Statues.instance.tabStatues);
	}
}
