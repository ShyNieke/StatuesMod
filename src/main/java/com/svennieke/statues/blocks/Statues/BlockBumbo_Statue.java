package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.StatueBase.BlockBumbo;

import net.minecraft.block.Block;

public class BlockBumbo_Statue extends BlockBumbo{
		
	public BlockBumbo_Statue(Block.Properties builder) {
		super(builder);
		//setUnlocalizedName(unlocalised);
	}
	
//    @Method(modid = "cactusmod")
//	@Override
//	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
//			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//    	if(!worldIn.isRemote)
//    	{
//    		int random = worldIn.rand.nextInt(100);
//
//			if (random < 1)
//			{
//				EntityGolem entity = new com.Mrbysco.CactusMod.entities.EntityCactoni(worldIn);
//				
//				entity.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
//				worldIn.spawnEntity(entity);
//				
//    			return true;
//			}
//    		return false;
//    	}
//    	else
//    		return false;
//	}
}
