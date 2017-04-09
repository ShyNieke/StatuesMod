package com.svennieke.statues.blocks.tiers.decorative;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockBabyZombie_Statue;
import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBabyZombie_Statue_T1 extends BlockBabyZombie_Statue{
		
	public BlockBabyZombie_Statue_T1() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.BABYZOMBIESTATUE.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.BABYZOMBIESTATUE.getRegistryName());
	}
	
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer) {
    	Block block = worldIn.getBlockState(pos.down()).getBlock();
    	if (block == Blocks.LAPIS_BLOCK) {
    		worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, pos.down().getX(), pos.down().getY(), pos.down().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
    		worldIn.setBlockState(pos.down(), StatuesBlocks.flood_statue.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
   		 	worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    		 return Blocks.AIR.getDefaultState();
    	}
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }
}
