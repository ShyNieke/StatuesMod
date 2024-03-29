package com.shynieke.statues.blocks.decorative;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class PebbleBlock extends FallingBlock {
	public PebbleBlock(Block.Properties properties) {
		super(properties.strength(0.6F).sound(SoundType.GRAVEL));
	}

	@Override
	public int getDustColor(BlockState state, BlockGetter getter, BlockPos pos) {
		return -8356741;
	}
}
