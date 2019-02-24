package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.StatueBase.BlockInfo;
import com.svennieke.statues.tileentity.StatueTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockInfo_Statue extends BlockInfo{
	private int TIER;
	
	public BlockInfo_Statue(Block.Properties builder) {
		super(builder);
		this.TIER = 1;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new StatueTileEntity(this.TIER);
	}
	
	private StatueTileEntity getTE(World world, BlockPos pos) {
        return (StatueTileEntity) world.getTileEntity(pos);
    }
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(this.TIER == 1)
		{
	        if (!worldIn.isRemote) {
	        	int statueTier = getTE(worldIn, pos).getTier();
	        	if(statueTier != this.TIER)
	        	{
	        		getTE(worldIn, pos).setTier(this.TIER);
	        	}
	        	
	        	getTE(worldIn, pos).SendInfoMessage(playerIn, worldIn, pos);
	        }
	        return true;
		}
		else
		return false;
	}
}
