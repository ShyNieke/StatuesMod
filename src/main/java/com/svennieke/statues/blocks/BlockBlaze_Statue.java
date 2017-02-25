package com.svennieke.statues.blocks;

import java.util.List;

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

public class BlockBlaze_Statue extends BaseNormal{

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 4, 0.0625 * 1, 0.0625 * 4, 0.0625 * 12, 0.0625 * 10, 0.0625 * 12);
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockBlaze_Statue() {
		super(Material.TNT);
		setUnlocalizedName(Reference.StatuesBlocks.BLAZESTATUE.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.BLAZESTATUE.getRegistryName());
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.GLASS);
		this.setLightLevel(0.5F);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		
		//playerIn.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 1F, 1F);
		//playerIn.dropItem(new ItemStack(Items.BLAZE_ROD, 1), true);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockBlaze_Statue statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 1F, 1F);
		playerIn.dropItem(new ItemStack(Items.BLAZE_ROD, 1), true);
		
		return 0;
}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
		// TODO Auto-generated method stub
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
	}

}
