package com.shynieke.statues.blocks.StatueBase;

import com.shynieke.statues.Statues;
import com.shynieke.statues.blocks.BaseBlock.BaseCutout;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockEtho extends BaseCutout{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	
	public BlockEtho() {
		super(Material.TNT);
		this.setCreativeTab(Statues.instance.tabStatues);
		this.setSoundType(SoundType.PLANT);
		this.setLightLevel(0.25F);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
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
	
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }
}
