package com.svennieke.statues.blocks.StatueBase;

import com.svennieke.statues.blocks.BaseBlock.BaseNormal;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockChickenJockey extends BaseNormal{
	
	private static final VoxelShape BOUNDING_BOX = Block.makeCuboidShape(6.5, 0, 6.5, 9.5, 8.5, 9.5);
	
	public BlockChickenJockey(Block.Properties builder) {
		super(builder.sound(SoundType.CLOTH));
	}
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }
	
}
