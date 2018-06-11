package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.StatueBase.BlockBumbo;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Method;

public class BlockBumbo_Statue extends BlockBumbo{
		
	public BlockBumbo_Statue(String unlocalised, String registry) {
		super();
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
	}
	
    @Method(modid = "cactusmod")
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if(!worldIn.isRemote)
    	{
    		int random = worldIn.rand.nextInt(100);

			if (random < 1)
			{
				EntityGolem entity = new com.Mrbysco.CactusMod.entities.EntityCactoni(worldIn);
				
				entity.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
				worldIn.spawnEntity(entity);
				
    			return true;
			}
    		return false;
    	}
    	else
    		return false;
	}
}
