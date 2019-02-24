package com.svennieke.statues.blocks.StatueBase;

import com.svennieke.statues.blocks.BaseBlock.BaseCutout;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockSheep extends BaseCutout{
	
	private static final VoxelShape BOUNDING_BOX = Block.makeCuboidShape(5, 0, 5, 11, 8, 11);
	
	public BlockSheep(Block.Properties builder) {
		super(builder.sound(SoundType.CLOTH));
	}
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
}
