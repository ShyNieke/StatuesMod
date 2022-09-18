package com.shynieke.statues.blocks.Statues;

import com.shynieke.statues.blocks.StatueBase.BlockEndermite;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEndermite_Statue extends BlockEndermite {

	public BlockEndermite_Statue(String unlocalised, String registry) {
		super();
		setTranslationKey(unlocalised);
		setRegistryName(registry);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
									EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_ENDERMITE_AMBIENT, SoundCategory.NEUTRAL, 1f, 1f);
			return true;
		} else
			return false;
	}
}
