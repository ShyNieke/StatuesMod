package com.svennieke.statues.blocks.tiers.functional;

import javax.annotation.Nullable;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockChicken_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChicken_Statue_T3 extends BlockChicken_Statue{
		
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockChicken_Statue_T3() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.CHICKENSTATUET3.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.CHICKENSTATUET3.getRegistryName());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
	public int StatueBehavior(BlockChicken_Statue_T3 statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.ENTITY_CHICKEN_AMBIENT , 1F, 1F);
		
		if (cooldown < 0.01){
			playerIn.dropItem(new ItemStack(Items.EGG, 1), true);
		}
		else
		playerIn.dropItem(new ItemStack(Items.FEATHER, 1), true);
		
		return 0;
	}
}
