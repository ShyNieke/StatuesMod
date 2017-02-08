package com.svennieke.statues.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.BaseBlock.BaseNormal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCow_Statue extends BaseNormal{
	public boolean milk = true;

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 4, 0, 0.0625 * 5, 0.0625 * 12, 0.0625 * 6, 0.0625 * 12);
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockCow_Statue() {
		super(Material.TNT);
		setUnlocalizedName(Reference.StatuesBlocks.COWSTATUE.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.COWSTATUE.getRegistryName());
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.GLASS);
		this.setLightLevel(0.5F);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.9) cooldown = StatueBehavior(this, playerIn, worldIn, hand, heldItem);
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockCow_Statue statue, EntityPlayer playerIn, World worldIn, EnumHand hand, @Nullable ItemStack stack) {
		playerIn.playSound(SoundEvents.ENTITY_COW_AMBIENT, 1F, 1F);
		ItemStack i = playerIn.inventory.getCurrentItem();
				
		if(!worldIn.isRemote)
			//System.out.println(i);
		if(milk){
			if (stack != null && stack.getItem() == Items.BUCKET)
			{
				if (--stack.stackSize == 0)
	            {
	                playerIn.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
	            }
	            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET)))
	            {
	            	playerIn.dropItem(new ItemStack(Items.MILK_BUCKET), false);
	            }
				//System.out.println(playerIn.getDisplayName());
			}
		}
		return 0;
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
    		List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
    	super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn);
    }
}
