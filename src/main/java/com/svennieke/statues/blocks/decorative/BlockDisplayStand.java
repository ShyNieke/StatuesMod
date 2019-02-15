package com.svennieke.statues.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockDisplayStand extends Block{

	public BlockDisplayStand(Block.Properties properties) {
		super(properties); //
//		setUnlocalizedName(unlocalised);
//		setHardness(0.6F);
//		setSoundType(SoundType.STONE);
//		this.setCreativeTab(Statues.instance.tabStatues);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean propagatesSkylightDown(IBlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }
}
