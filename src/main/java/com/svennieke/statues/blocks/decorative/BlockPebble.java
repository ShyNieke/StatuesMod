package com.svennieke.statues.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockPebble extends BlockFalling{

	public BlockPebble(Block.Properties builder) {
	    super(builder);
	}

	@Override
	public MaterialColor getMapColor(IBlockState p_180659_1_, IBlockReader p_180659_2_, BlockPos p_180659_3_) {
        return blockMapColor.STONE;
	}

	@Override
	public int getDustColor(IBlockState p_189876_1_) {
		return -8356741;
	}
}
