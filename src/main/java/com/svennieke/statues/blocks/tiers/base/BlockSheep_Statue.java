package com.svennieke.statues.blocks.tiers.base;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.BaseBlock.BaseCutout;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSheep_Statue extends BaseCutout{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0, 0.0625 * 5, 0.0625 * 11, 0.0625 * 8, 0.0625 * 11);
	
	public BlockSheep_Statue() {
		super(Material.TNT);
		this.setCreativeTab(Statues.instance.tabStatues);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.CLOTH);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
}
