package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockBabyZombie;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBabyZombie_Statue extends BlockBabyZombie implements iStatue, ITileEntityProvider{
	
	private int TIER;
	
	public BlockBabyZombie_Statue(String unlocalised, String registry, int tier) {
		super();
		this.TIER = tier;
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
		if(this.TIER == 1)
		{
			Block block = worldIn.getBlockState(pos.down()).getBlock();
	    	if (block == Blocks.LAPIS_BLOCK) {
	    		worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, pos.down().getX(), pos.down().getY(), pos.down().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
	    		worldIn.setBlockState(pos.down(), StatuesBlocks.flood_statue.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
	    		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	   		 	worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
	    	}
		}
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (this.TIER == 2 || this.TIER == 3 || this.TIER == 4)
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(this.TIER == 2 || this.TIER == 3 || this.TIER == 4)
		{
	        if (!worldIn.isRemote) {
	        	int statuetier = getTE(worldIn, pos).getTier();
	        	if(statuetier != this.TIER)
	        	{
	        		getTE(worldIn, pos).setTier(this.TIER);
	        	}
	        	
	        	ItemStack flesh = new ItemStack(Items.ROTTEN_FLESH, 1);
	        	ItemStack iron = new ItemStack(Items.field_191525_da, 1);
	        	getTE(worldIn, pos).PlaySound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, pos, worldIn);
	        	getTE(worldIn, pos).StatueBehavior(flesh, null, iron, null, false, false, this, playerIn, worldIn, pos);
	        }
		}
		return true;
	}
}
