package com.svennieke.statues.blocks.StatueBase;

import com.svennieke.statues.blocks.BaseBlock.BaseCutout;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockPlayer extends BaseCutout{
	
	private static final VoxelShape BOUNDING_BOX = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
	
	public BlockPlayer(Block.Properties builder) {
		super(builder.sound(SoundType.PLANT));
//		this.setCreativeTab(Statues.instance.tabStatues);
	}
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		//Disabled until we re-do all bounding boxes
		/*
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
		if(enumfacing == enumfacing.NORTH || enumfacing == enumfacing.SOUTH)
		{
			return BOUNDING_BOX;
		}
		else
		{
			return BOUNDING_BOX;
		}
		*/
		
		return BOUNDING_BOX;
    }
	
//    @Override
//    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, VoxelShape entityBox, List<VoxelShape> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
//    {
//    	addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
//    }
}
