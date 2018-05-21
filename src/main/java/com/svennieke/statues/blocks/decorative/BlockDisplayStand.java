package com.svennieke.statues.blocks.decorative;

import com.svennieke.statues.Statues;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDisplayStand extends Block{

	public BlockDisplayStand(String unlocalised, String registry) {
		super(Material.ROCK);
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
		setHardness(0.6F);
		setSoundType(SoundType.STONE);
		this.setCreativeTab(Statues.instance.tabStatues);
	}
}
