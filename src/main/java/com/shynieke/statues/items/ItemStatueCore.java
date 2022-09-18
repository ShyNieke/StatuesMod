package com.shynieke.statues.items;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import net.minecraft.item.Item;

public class ItemStatueCore extends Item {
	public ItemStatueCore(String unlocalised) {
		setTranslationKey(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(Statues.instance.tabStatues);
	}
}
