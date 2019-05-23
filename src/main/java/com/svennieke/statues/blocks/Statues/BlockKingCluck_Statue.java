package com.svennieke.statues.blocks.Statues;

import java.util.ArrayList;
import java.util.List;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.StatueBase.BlockKingCluck;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKingCluck_Statue extends BlockKingCluck implements IStatue, ITileEntityProvider{
	
	private int TIER;
	
	public BlockKingCluck_Statue(String unlocalised) {
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
		if (this.TIER >= 2)
		{
			return new StatueTileEntity(this.TIER);
		}
		else
		return null;
	}
	
	private StatueTileEntity getTE(World world, BlockPos pos) {
        return (StatueTileEntity) world.getTileEntity(pos);
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
	        	
	        	ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("king_cluck"));
	        	ItemStack stack1 = stackList.get(0);
        		ItemStack stack2 = stackList.get(1);
        		ItemStack stack3 = stackList.get(2);
        		
	        	tile.PlaySound(SoundEvents.ENTITY_CHICKEN_AMBIENT, pos, worldIn);
	        	tile.GiveItem(stack1, stack2, stack3, playerIn);
	        	
	        	EntityChicken chicken = new EntityChicken(worldIn);
	        	chicken.setCustomNameTag("King Cluck");
	        	tile.SpawnMob(chicken, worldIn);
	        }
	        return true;
		}
		else
		return false;
	}
	
	public static ItemStack getNugget() {
		return new ItemStack(StatuesItems.nugget, 1);
	}
	
	public static ItemStack getGold() {
		return new ItemStack(Items.GOLD_NUGGET, 1);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        	tooltip.add(TextFormatting.GOLD + I18n.format(Reference.MOD_PREFIX + "cluckington.info"));
    }
}
