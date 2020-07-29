package com.shynieke.statues.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class StatueTransBeeItem extends StatueBlockItem {

	public StatueTransBeeItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	public static boolean isTrans(ItemStack stack) {
		return stack.getDisplayName().getUnformattedComponentText().equalsIgnoreCase("Trans Bee");
	}
}
