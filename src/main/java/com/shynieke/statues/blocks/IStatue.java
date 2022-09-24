package com.shynieke.statues.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public interface IStatue {
	<T extends Block> T setTier(int tier);

	int getTier();

	default Block setColor(EnumDyeColor color) {
		return Blocks.AIR;
	}

	default EnumDyeColor getColor() {
		return EnumDyeColor.WHITE;
	}
}
