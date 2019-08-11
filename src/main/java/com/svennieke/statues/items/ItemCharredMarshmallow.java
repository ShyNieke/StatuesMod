package com.svennieke.statues.items;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCharredMarshmallow extends Item {
	public ItemCharredMarshmallow(String unlocalised) {
		setTranslationKey(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(Statues.tabStatues);
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 500;
	}
}
