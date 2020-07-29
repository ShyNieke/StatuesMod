package com.shynieke.statues.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StatueTransBeeItem extends StatueBlockItem {

	public StatueTransBeeItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
		this.addPropertyOverride(new ResourceLocation("trans"), (p_210312_0_, p_210312_1_, p_210312_2_) -> {
			return isTrans(p_210312_0_) ? 1.0F : 0.0F;
		});
	}

	public static boolean isTrans(ItemStack stack) {
		return stack.getDisplayName().getUnformattedComponentText().equalsIgnoreCase("Trans Bee");
	}
}
