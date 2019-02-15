package com.svennieke.statues.blocks.Statues;

import java.util.ArrayList;

import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.StatueBase.BlockWastelandPig;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockWastelandPig_Statue extends BlockWastelandPig implements IStatue{
	
	private int TIER;
	
	public BlockWastelandPig_Statue(Block.Properties builder) {
		super(builder);
		//setRegistryName(registry);
		//setUnlocalizedName(unlocalised);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Block setTier(int tier)
	{
		this.TIER = tier;
//		setUnlocalizedName(super.getUnlocalizedName().replace("tile.", "") + (tier > 1 ? "t" + tier : ""));
		//setRegistryName("block" + super.getTranslationKey().replace("tile.", ""));
		return this;
	}
	
	@Override
	public int getTier()
	{
		return this.TIER;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		if (this.TIER >= 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		if (this.TIER >= 2)
		{
			return new StatueTileEntity();
		}
		else
		{
			return null;
		}
	}
	
	private StatueTileEntity getTE(World world, BlockPos pos) {
        return (StatueTileEntity) world.getTileEntity(pos);
    }
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(this.TIER >= 2)
		{
	        if (!worldIn.isRemote) {
	        	int statuetier = getTE(worldIn, pos).getTier();
	        	if(statuetier != this.TIER)
	        	{
	        		getTE(worldIn, pos).setTier(this.TIER);
	        	}
	        	
	        	ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("wasteland_pig"));
	        	ItemStack stack1 = stackList.get(0);
        		ItemStack stack2 = stackList.get(1);
        		ItemStack stack3 = stackList.get(2);
        		
        		if(stack1.getItem() != StatuesItems.tea)
        		{
        			getTE(worldIn, pos).PlaySound(SoundEvents.ENTITY_PIG_AMBIENT, pos, worldIn);
    	        	getTE(worldIn, pos).GiveItem(stack1, stack2, stack3, playerIn);
        		}
        		else
        		{
    	        	getTE(worldIn, pos).WastelandBehavior(worldIn, pos, playerIn, stack1, stack2, stack3);
        		}
        		
        		EntityPig pig = new EntityPig(worldIn);
        		pig.setCustomName(new TextComponentString("Wasteland Pig"));
	        	getTE(worldIn, pos).SpawnMob(pig, worldIn);
	        }
	        return true;
		}
		else
		return false;
	}
}
