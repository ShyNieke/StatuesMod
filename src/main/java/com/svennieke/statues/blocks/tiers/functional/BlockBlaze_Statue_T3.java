package com.svennieke.statues.blocks.tiers.functional;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockBlaze_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlaze_Statue_T3 extends BlockBlaze_Statue{
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockBlaze_Statue_T3() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.BLAZESTATUET3.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.BLAZESTATUET3.getRegistryName());
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		
		//playerIn.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 1F, 1F);
		//playerIn.dropItem(new ItemStack(Items.BLAZE_ROD, 1), true);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockBlaze_Statue_T3 statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 1F, 1F);
		playerIn.dropItem(new ItemStack(Items.BLAZE_ROD, 1), true);
		
		return 0;
	}

}
