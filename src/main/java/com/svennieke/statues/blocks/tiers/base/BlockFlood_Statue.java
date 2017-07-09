package com.svennieke.statues.blocks.tiers.base;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.BaseBlock.BaseNormal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFlood_Statue extends BaseNormal{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
	
	public BlockFlood_Statue() {
		super(Material.TNT);
		this.setCreativeTab(Statues.instance.tabStatues);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
}
