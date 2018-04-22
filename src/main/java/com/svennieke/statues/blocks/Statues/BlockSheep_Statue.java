package com.svennieke.statues.blocks.Statues;

import java.util.ArrayList;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockSheep;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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

public class BlockSheep_Statue extends BlockSheep implements iStatue, ITileEntityProvider{
	
	private int TIER;
	private EnumDyeColor COLOR;
	
	public BlockSheep_Statue(String unlocalised, String registry, int tier, EnumDyeColor COLOR) {
		super();
		this.TIER = tier;
		this.COLOR = COLOR;
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (this.TIER == 2 || this.TIER == 3 || this.TIER == 4)
		{
			return new StatueTileEntity();
		}
		else
		return null;
	}
	
	private StatueTileEntity getTE(World world, BlockPos pos) {
        return (StatueTileEntity) world.getTileEntity(pos);
    }

	public ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep"));
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		
		if(this.TIER == 2 || this.TIER == 3 || this.TIER == 4)
		{
	        if (!worldIn.isRemote) {
	        	int statuetier = getTE(worldIn, pos).getTier();
	        	if(statuetier != this.TIER)
	        	{
	        		getTE(worldIn, pos).setTier(this.TIER);
	        	}
	        	
	        	ItemStack stack1 = getWool();
        		ItemStack stack2 = stackList.get(1);
        		ItemStack stack3 = stackList.get(2);
        		
        		getTE(worldIn, pos).StatueBehavior(stack1, stack2, stack3, null, false, false, this, playerIn, worldIn, pos);
	        	getTE(worldIn, pos).PlaySound(SoundEvents.ENTITY_SHEEP_AMBIENT, pos, worldIn);
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
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_whitet2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_whitet3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_whitet4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.ORANGE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_oranget2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_oranget3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_oranget4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.MAGENTA) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magentat2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magentat3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magentat4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIGHT_BLUE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightbluet2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightbluet3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightbluet4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.YELLOW) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellowt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellowt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellowt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIME) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_limet2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_limet3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_limet4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.PINK) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pinkt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pinkt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pinkt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.GRAY) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_grayt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_grayt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_grayt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.SILVER) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgrayt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgrayt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgrayt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.CYAN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyant2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyant3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyant4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.PURPLE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purplet2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purplet3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purplet4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BLUE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_bluet2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_bluet3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_bluet4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BROWN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brownt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brownt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brownt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.GREEN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_greent2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_greent3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_greent4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.RED) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_redt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_redt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_redt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BLACK) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blackt2.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blackt3.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blackt4.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));}
			}
		}
	}
}
