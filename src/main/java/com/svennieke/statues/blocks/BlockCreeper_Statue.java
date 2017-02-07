package com.svennieke.statues.blocks;

import java.util.List;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.BaseBlock.BaseCutout;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCreeper_Statue extends BaseCutout{
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 3, 0, 0.0625 * 3, 0.0625 * 13, 0.0625 * 12, 0.0625 * 13);
	
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockCreeper_Statue() {
		super(Material.TNT);
		setUnlocalizedName(Reference.StatuesBlocks.CREEPERSTATUE.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.CREEPERSTATUE.getRegistryName());
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.PLANT);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn, worldIn, pos);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockCreeper_Statue statue, EntityPlayer playerIn, World worldIn, BlockPos pos) {
		playerIn.playSound(SoundEvents.ENTITY_CREEPER_HURT , 1F, 1F);
		if (cooldown < 0.01){
			playerIn.dropItem(new ItemStack(Items.GUNPOWDER, 1), true);
		}
		else
		if (!worldIn.isRemote)
		{
        EntityCreeper entitycreeper = new EntityCreeper(worldIn);
        entitycreeper.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        NBTTagCompound tag = new NBTTagCompound();
        entitycreeper.writeEntityToNBT(tag);
        tag.setShort("ExplosionRadius", (short)0);
        tag.setShort("Fuse", (short)0);
        entitycreeper.readEntityFromNBT(tag);
        worldIn.spawnEntityInWorld(entitycreeper);
        entitycreeper.spawnExplosionParticle();
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
