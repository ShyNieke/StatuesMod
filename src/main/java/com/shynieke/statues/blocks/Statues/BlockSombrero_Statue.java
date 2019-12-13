package com.shynieke.statues.blocks.Statues;

import com.shynieke.statues.blocks.StatueBase.BlockSombrero;
import com.shynieke.statues.init.StatuesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSombrero_Statue extends BlockSombrero{
		
	public BlockSombrero_Statue(String unlocalised, String registry) {
		super();
		setTranslationKey(unlocalised);
		setRegistryName(registry);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
    	Block block = worldIn.getBlockState(pos.down()).getBlock();
		if (block == Blocks.CACTUS) {
    		worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, pos.down().getX(), pos.down().getY(), pos.down().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
    		worldIn.setBlockState(pos.down(), StatuesBlocks.bumbo_statue.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    	}
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
}
