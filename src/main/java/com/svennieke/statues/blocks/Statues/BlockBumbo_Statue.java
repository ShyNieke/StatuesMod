package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockBumbo;

public class BlockBumbo_Statue extends BlockBumbo implements iStatue{
		
	public BlockBumbo_Statue(String unlocalised, String registry) {
		super();
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
	}
}
