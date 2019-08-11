package com.svennieke.statues.blocks.StatueBase;

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

import javax.annotation.Nullable;
import java.util.List;

public class BlockFlood extends BaseNormal{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
	
	public BlockFlood() {
		super(Material.TNT);
		this.setCreativeTab(Statues.instance.tabStatues);
		this.setSoundType(SoundType.GLASS);
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
