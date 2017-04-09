package com.svennieke.statues.blocks.tiers.muted;

import javax.annotation.Nullable;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockFlood_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFlood_Statue_T4 extends BlockFlood_Statue{
	public boolean WATER = true;
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockFlood_Statue_T4() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.FLOODSTATUET4.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.FLOODSTATUET4.getRegistryName());
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.9) cooldown = StatueBehavior(this, playerIn, worldIn, hand, heldItem, pos, hitZ, hitZ, hitZ);
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockFlood_Statue_T4 statue, EntityPlayer playerIn, World worldIn, EnumHand hand, @Nullable ItemStack stack, BlockPos pos, float hitX, float hitY, float hitZ) {
		ItemStack i = playerIn.inventory.getCurrentItem();
		
		EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), stack);
		
		if(!worldIn.isRemote)
		if (cooldown < 0.05){
			 worldIn.spawnEntityInWorld(entityfireworkrocket);
		}
		
		if(WATER){
			if (stack != null && stack.getItem() == Items.BUCKET)
			{
				ItemStack floodbucket = new ItemStack(Items.WATER_BUCKET); 
				floodbucket.setStackDisplayName("The Flood");
				if (--stack.stackSize == 0)
	            {
	                playerIn.setHeldItem(hand, floodbucket);
	            }
	            else if (!playerIn.inventory.addItemStackToInventory(floodbucket))
	            {
	            	playerIn.dropItem(floodbucket, false);
	            }
				//System.out.println(playerIn.getDisplayName());
			}
		}
		return 0;
	}
}
