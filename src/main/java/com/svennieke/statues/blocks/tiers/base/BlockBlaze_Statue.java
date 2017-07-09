package com.svennieke.statues.blocks.tiers.base;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.BaseBlock.BaseNormal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBlaze_Statue extends BaseNormal{

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 4, 0.0625 * 1, 0.0625 * 4, 0.0625 * 12, 0.0625 * 10, 0.0625 * 12);
	
	public BlockBlaze_Statue() {
		super(Material.TNT);
		this.setCreativeTab(Statues.instance.tabStatues);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.GLASS);
		this.setLightLevel(0.5F);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
}
