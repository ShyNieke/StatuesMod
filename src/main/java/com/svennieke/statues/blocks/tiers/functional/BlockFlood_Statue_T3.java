package com.svennieke.statues.blocks.tiers.functional;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockFlood_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFlood_Statue_T3 extends BlockFlood_Statue{
	public boolean WATER = true;
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockFlood_Statue_T3() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.FLOODSTATUET3.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.FLOODSTATUET3.getRegistryName());
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn, worldIn, hand, pos, hitZ, hitZ, hitZ);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
	public int StatueBehavior(BlockFlood_Statue_T3 statue, EntityPlayer playerIn, World worldIn, EnumHand hand, BlockPos pos, float hitX, float hitY, float hitZ) {
		playerIn.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 1F, 1F);

		ItemStack stack = playerIn.inventory.getCurrentItem();
		
		EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), stack);
		
		if(!worldIn.isRemote)
		if (cooldown < 0.05){
			 worldIn.spawnEntity(entityfireworkrocket);
		}
		
		if(WATER){
			if (stack.getItem() == Items.BUCKET && !playerIn.capabilities.isCreativeMode)
			{
				playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
				stack.shrink(1);
				
				ItemStack floodbucket = new ItemStack(Items.WATER_BUCKET); 
				floodbucket.setStackDisplayName("The Flood");
				
				if (stack.isEmpty())
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
