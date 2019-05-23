package com.svennieke.statues.blocks.Statues;

import java.util.ArrayList;

import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.StatueBase.BlockSheep;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSheep_Statue extends BlockSheep implements IStatue, ITileEntityProvider{
	
	private int TIER;
	private EnumDyeColor COLOR;
	
	public BlockSheep_Statue(String unlocalised) {
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
	public Block setColor(EnumDyeColor color) {
		this.COLOR = color;

		return this;
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
		ItemStack stack = playerIn.getHeldItem(hand);
		
		if(this.TIER >= 2)
		{
	        if (!worldIn.isRemote) {
	        	StatueTileEntity tile = getTE(worldIn, pos);
	        	
	        	int statuetier = tile.getTier();
	        	if(statuetier != this.TIER)
	        	{
	        		tile.setTier(this.TIER);
	        	}
	        	
	        	ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep"));
	        	ItemStack stack1 = getWool();
        		ItemStack stack2 = stackList.get(1);
        		ItemStack stack3 = stackList.get(2);
        		
	        	tile.GiveItem(stack1, stack2, stack3, playerIn);
	        	tile.PlaySound(SoundEvents.ENTITY_SHEEP_AMBIENT, pos, worldIn);
	        	tile.SpawnMob(new EntitySheep(worldIn), worldIn);
	        }
	        return true;
		}

		if(stack != ItemStack.EMPTY && stack.getItem() instanceof ItemDye)
		{
			ChangeColor(stack, worldIn, pos, this.TIER, playerIn);
			return true;
		}
		else
		return false;
	}
	
	public ItemStack getWool() {
		ItemStack stack1 = null;
		
		if(this.COLOR == EnumDyeColor.WHITE)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 0);
    	}
    	if(this.COLOR == EnumDyeColor.ORANGE)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 1);
    	}
    	if(this.COLOR == EnumDyeColor.MAGENTA)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 2);
    	}
    	if(this.COLOR == EnumDyeColor.LIGHT_BLUE)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 3);
    	}
    	if(this.COLOR == EnumDyeColor.YELLOW)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 4);
    	}
    	if(this.COLOR == EnumDyeColor.LIME)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 5);
    	}
    	if(this.COLOR == EnumDyeColor.PINK)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 6);
    	}
    	if(this.COLOR == EnumDyeColor.GRAY)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 7);
    	}
    	if(this.COLOR == EnumDyeColor.SILVER)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 8);
    	}
    	if(this.COLOR == EnumDyeColor.CYAN)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 9);
    	}
    	if(this.COLOR == EnumDyeColor.PURPLE)
    	{
        	stack1 = new ItemStack(Blocks.WOOL, 1, 10);
    	}
    	if(this.COLOR == EnumDyeColor.BLUE)
    	{
    		stack1 = new ItemStack(Blocks.WOOL, 1, 11);
    	}
    	if(this.COLOR == EnumDyeColor.BROWN)
    	{
    		stack1 = new ItemStack(Blocks.WOOL, 1, 12);
    	}
    	if(this.COLOR == EnumDyeColor.GREEN)
    	{
    		stack1 = new ItemStack(Blocks.WOOL, 1, 13);
    	}
    	if(this.COLOR == EnumDyeColor.RED)
    	{
    		stack1 = new ItemStack(Blocks.WOOL, 1, 14);
    	}
    	if(this.COLOR == EnumDyeColor.BLACK)
    	{
    		stack1 = new ItemStack(Blocks.WOOL, 1, 15);
    	}
    	
    	return stack1;
	}
	
	public EnumDyeColor getCOLOR() {
		return COLOR;
	}
	
	public void ChangeColor(ItemStack stack, World worldIn, BlockPos pos, int TIER, EntityLivingBase placer) {

		EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
		
		if (this.COLOR != enumdyecolor)
		{
			stack.shrink(1);
			if(enumdyecolor == EnumDyeColor.WHITE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.ORANGE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.MAGENTA) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIGHT_BLUE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.YELLOW) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIME) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.PINK) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.GRAY) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.SILVER) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.CYAN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.PURPLE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BLUE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BROWN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.GREEN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.RED) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BLACK) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black[0].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black[1].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black[2].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black[3].getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
		}
	}
}
