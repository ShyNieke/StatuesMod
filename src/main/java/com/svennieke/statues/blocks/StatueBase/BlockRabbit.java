package com.svennieke.statues.blocks.StatueBase;

import java.util.List;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.BaseBlock.BaseCutout;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRabbit extends BaseCutout{
																
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 6, 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 6, 0.0625 * 10);
	
	public BlockRabbit() {
		super(Material.TNT);
		this.setCreativeTab(Statues.tabStatues);
		this.setSoundType(SoundType.GLASS);
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
    		List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
    	super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }
}
