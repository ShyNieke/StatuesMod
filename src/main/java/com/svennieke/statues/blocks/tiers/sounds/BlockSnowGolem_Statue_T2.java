package com.svennieke.statues.blocks.tiers.sounds;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockSnowGolem_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSnowGolem_Statue_T2 extends BlockSnowGolem_Statue{

	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockSnowGolem_Statue_T2() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.SNOWGOLEMSTATUET2.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.SNOWGOLEMSTATUET2.getRegistryName());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
	public int StatueBehavior(BlockSnowGolem_Statue_T2 statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.BLOCK_SNOW_STEP, 1F, 1F);
		
		return 0;
	}
}
