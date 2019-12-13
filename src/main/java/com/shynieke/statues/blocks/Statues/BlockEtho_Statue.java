package com.shynieke.statues.blocks.Statues;

import com.shynieke.statues.blocks.IStatue;
import com.shynieke.statues.blocks.StatueBase.BlockEtho;
import com.shynieke.statues.compat.list.StatueLootList;
import com.shynieke.statues.entity.fakeentity.FakeCreeper;
import com.shynieke.statues.init.StatuesItems;
import com.shynieke.statues.tileentity.StatueTileEntity;
import com.shynieke.statues.util.RandomLists;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockEtho_Statue extends BlockEtho implements ITileEntityProvider, IStatue {
	
	private int TIER;

	public BlockEtho_Statue(String unlocalised) {
		super();
		setTranslationKey(unlocalised);
	}
	
	@Override
	public Block setTier(int tier)
	{
		this.TIER = tier;
		setTranslationKey(super.getTranslationKey().replace("tile.", "") + (tier > 1 ? "t" + tier : ""));
		setRegistryName("block" + super.getTranslationKey().replace("tile.", ""));
		return this;
	}
	
	@Override
	public int getTier()
	{
		return this.TIER;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new StatueTileEntity(this.TIER);
	}
	
	private StatueTileEntity getTE(World world, BlockPos pos) {
        return (StatueTileEntity) world.getTileEntity(pos);
    }
	
    @SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		if (rand.nextInt(24) == 0)
        {
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.3F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
	}
    
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(this.TIER >= 2)
		{
	        if (!worldIn.isRemote) {
	        	StatueTileEntity tile = getTE(worldIn, pos);
	        	
	        	int statuetier = tile.getTier();
	        	if(statuetier != this.TIER)
	        	{
	        		tile.setTier(this.TIER);
	        	}
	        	
	        	ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("etho"));
	        	ItemStack stack1 = stackList.get(0);
        		ItemStack stack2 = stackList.get(1);
        		ItemStack stack3 = stackList.get(2);
        		
        		if(stack1.getItem() != StatuesItems.marshmallow)
        		{
        			tile.PlaySound(RandomLists.getRandomCampfire(), pos, worldIn);
    	        	tile.GiveItem(stack1, stack2, stack3, playerIn);
        		}
        		else
        		{
    	        	tile.CampfireBehavior(worldIn, pos, playerIn, stack1, stack2, stack3);
        		}
        		
	        	tile.FakeMobs(getGeneral(worldIn), worldIn, pos, false);
	        }
	        return true;
		}
		else
		return false;
	}
	
	public FakeCreeper getGeneral(World worldIn)
	{
		FakeCreeper general = new FakeCreeper(worldIn);
		general.setCustomNameTag("General Spazz");
        
        return general;
	}
}
