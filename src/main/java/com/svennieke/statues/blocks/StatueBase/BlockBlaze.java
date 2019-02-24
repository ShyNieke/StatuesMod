package com.svennieke.statues.blocks.StatueBase;

import com.svennieke.statues.blocks.BaseBlock.BaseNormal;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockBlaze extends BaseNormal{

	private static final VoxelShape BOUNDING_BOX = Block.makeCuboidShape(4, 1, 4, 12, 10, 12);
	
	public BlockBlaze(Block.Properties builder) {
		super(builder.sound(SoundType.GLASS).lightValue(7));
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return BOUNDING_BOX;
	}
	
}
