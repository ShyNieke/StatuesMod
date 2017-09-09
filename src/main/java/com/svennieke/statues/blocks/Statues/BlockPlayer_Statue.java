package com.svennieke.statues.blocks.Statues;

import java.util.Random;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockPlayer;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockPlayer_Statue extends BlockPlayer implements iStatue, ITileEntityProvider{
	
	private String playername;
	
	public BlockPlayer_Statue(String unlocalised, String registry, String name) {
		super();
		this.playername = name;
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new PlayerStatueTileEntity();
	}
	
	private PlayerStatueTileEntity getTE(World world, BlockPos pos) {
        return (PlayerStatueTileEntity) world.getTileEntity(pos);
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		stack.setStackDisplayName(getTE(worldIn, pos).getName());
		super.harvestBlock(worldIn, player, pos, state, te, stack);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		String stackname = stack.getDisplayName();
		String tilename = getTE(worldIn, pos).getName();
		if (tilename != stackname)
		{
			this.playername = stackname;
			getTE(worldIn, pos).setName(playername);
		}
		
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
	        if (!world.isRemote) {
	        	System.out.print(getTE(world, pos).getName());
	        }
		return true;
	}
}
