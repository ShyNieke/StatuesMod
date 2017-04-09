package com.svennieke.statues.blocks.tiers.sounds;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockBabyZombie_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBabyZombie_Statue_T2 extends BlockBabyZombie_Statue{
		
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockBabyZombie_Statue_T2() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.BABYZOMBIESTATUET2.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.BABYZOMBIESTATUET2.getRegistryName());

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn, worldIn, pos);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockBabyZombie_Statue_T2 statue, EntityPlayer playerIn, World worldIn, BlockPos pos) {
		playerIn.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT , 1F, 1F);
		
		return 0;
	}
}
