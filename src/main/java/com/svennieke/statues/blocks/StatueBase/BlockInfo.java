package com.svennieke.statues.blocks.StatueBase;

import java.util.List;

import javax.annotation.Nullable;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.BaseBlock.BaseNormal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInfo extends BaseNormal{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 4, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 8, 0.0625 * 12);
	
	public BlockInfo() {
		super(Material.TNT);
		this.setCreativeTab(Statues.instance.tabStatues);
		this.setSoundType(SoundType.STONE);
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }
}
