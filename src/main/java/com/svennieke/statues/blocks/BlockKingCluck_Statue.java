package com.svennieke.statues.blocks;

import java.util.List;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.BaseBlock.BaseCutout;
import com.svennieke.statues.init.StatuesItems;

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

public class BlockKingCluck_Statue extends BaseCutout{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 6.5, 0, 0.0625 * 6, 0.0625 * 9.5, 0.0625 * 4.5, 0.0625 * 9);
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockKingCluck_Statue() {
		super(Material.TNT);
		setUnlocalizedName(Reference.StatuesBlocks.KINGCLUCKSTATUE.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.KINGCLUCKSTATUE.getRegistryName());
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.CLOTH);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockKingCluck_Statue statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.ENTITY_CHICKEN_AMBIENT , 1F, 1F);
		if (cooldown < 0.01){
			playerIn.dropItem(new ItemStack(Items.GOLD_NUGGET, 1), true);
		}
		else
		playerIn.dropItem(new ItemStack(StatuesItems.nugget, 1), true);
		
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
