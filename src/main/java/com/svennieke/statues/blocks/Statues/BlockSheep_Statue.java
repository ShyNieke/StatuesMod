package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.StatueBase.BlockSheep;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.tileentity.StatueTileEntity;
import net.minecraft.block.Block;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockSheep_Statue extends BlockSheep implements IStatue{
	
	private int TIER;
	private EnumDyeColor COLOR;
	
	public BlockSheep_Statue(Block.Properties builder) {
		super(builder);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Block setTier(int tier)
	{
		this.TIER = tier;
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
			return new StatueTileEntity(this.TIER);
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
		ItemStack stack = playerIn.getHeldItem(hand);
		
		if(this.TIER >= 2)
		{
	        if (!worldIn.isRemote) {
	        	int statuetier = getTE(worldIn, pos).getTier();
	        	if(statuetier != this.TIER)
	        	{
	        		getTE(worldIn, pos).setTier(this.TIER);
	        	}
	        	
	        	ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep"));
	        	ItemStack stack1 = getWool();
        		ItemStack stack2 = stackList.get(1);
        		ItemStack stack3 = stackList.get(2);
        		
	        	getTE(worldIn, pos).GiveItem(stack1, stack2, stack3, playerIn);
	        	getTE(worldIn, pos).PlaySound(SoundEvents.ENTITY_SHEEP_AMBIENT, pos, worldIn);
	        	getTE(worldIn, pos).SpawnMob(new EntitySheep(worldIn), worldIn);
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
        	stack1 = new ItemStack(Blocks.WHITE_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.ORANGE)
    	{
        	stack1 = new ItemStack(Blocks.ORANGE_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.MAGENTA)
    	{
        	stack1 = new ItemStack(Blocks.MAGENTA_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.LIGHT_BLUE)
    	{
        	stack1 = new ItemStack(Blocks.LIGHT_BLUE_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.YELLOW)
    	{
        	stack1 = new ItemStack(Blocks.YELLOW_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.LIME)
    	{
        	stack1 = new ItemStack(Blocks.LIME_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.PINK)
    	{
        	stack1 = new ItemStack(Blocks.PINK_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.GRAY)
    	{
        	stack1 = new ItemStack(Blocks.GRAY_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.LIGHT_GRAY)
    	{
        	stack1 = new ItemStack(Blocks.LIGHT_GRAY_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.CYAN)
    	{
        	stack1 = new ItemStack(Blocks.CYAN_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.PURPLE)
    	{
        	stack1 = new ItemStack(Blocks.PURPLE_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.BLUE)
    	{
    		stack1 = new ItemStack(Blocks.BLUE_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.BROWN)
    	{
    		stack1 = new ItemStack(Blocks.BROWN_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.GREEN)
    	{
    		stack1 = new ItemStack(Blocks.GREEN_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.RED)
    	{
    		stack1 = new ItemStack(Blocks.RED_WOOL, 1);
    	}
    	if(this.COLOR == EnumDyeColor.BLACK)
    	{
    		stack1 = new ItemStack(Blocks.BLACK_WOOL, 1);
    	}
    	
    	return stack1;
	}
	
	public EnumDyeColor getColor() {
		return COLOR;
	}
	
	public void ChangeColor(ItemStack stack, World worldIn, BlockPos pos, int TIER, EntityLivingBase placer) {
		
		EnumDyeColor enumdyecolor = EnumDyeColor.getColor(stack);
		
		if (this.COLOR != enumdyecolor)
		{
			stack.shrink(1);
			if(enumdyecolor == EnumDyeColor.WHITE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_white_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.ORANGE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_orange_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.MAGENTA) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_magenta_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIGHT_BLUE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightblue_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.YELLOW) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_yellow_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIME) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lime_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.PINK) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_pink_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.GRAY) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_gray_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.LIGHT_GRAY) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_lightgray_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.CYAN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_cyan_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.PURPLE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_purple_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BLUE) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_blue_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BROWN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_brown_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.GREEN) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_green_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.RED) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_red_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
			if(enumdyecolor == EnumDyeColor.BLACK) {
				if(TIER == 1) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black_t1.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 2) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black_t2.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 3) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black_t3.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 4) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black_t4.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
				if(TIER == 5) {worldIn.setBlockState(pos, StatuesBlocks.sheep_statue_black_t5.getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));}
			}
		}
	}
}
