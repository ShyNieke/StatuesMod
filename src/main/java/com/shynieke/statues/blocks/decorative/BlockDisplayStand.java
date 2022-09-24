package com.shynieke.statues.blocks.decorative;

import com.shynieke.statues.Statues;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockDisplayStand extends Block {

	public BlockDisplayStand(String unlocalised, String registry) {
		super(Material.ROCK);
		setTranslationKey(unlocalised);
		setRegistryName(registry);
		setHardness(0.6F);
		setSoundType(SoundType.STONE);
		this.setCreativeTab(Statues.tabStatues);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
