package com.svennieke.statues.blocks.tiers.muted;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockBabyZombie_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBabyZombie_Statue_T4 extends BlockBabyZombie_Statue{
		
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockBabyZombie_Statue_T4() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.BABYZOMBIESTATUET4.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.BABYZOMBIESTATUET4.getRegistryName());

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn, worldIn, pos);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockBabyZombie_Statue_T4 statue, EntityPlayer playerIn, World worldIn, BlockPos pos) {
		if (cooldown < 0.001){
			playerIn.dropItem(new ItemStack(Items.IRON_INGOT, 1), true);
		}
		else
		playerIn.dropItem(new ItemStack(Items.ROTTEN_FLESH, 1), true);
		
		return 0;
	}
}
