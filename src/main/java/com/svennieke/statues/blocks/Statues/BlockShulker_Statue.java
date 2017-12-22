package com.svennieke.statues.blocks.Statues;

import java.util.List;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockShulker;
import com.svennieke.statues.entity.fakeentity.FakeShulker;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class BlockShulker_Statue extends BlockShulker implements iStatue, ITileEntityProvider{
	
	private int TIER;
	
	public BlockShulker_Statue(String unlocalised, String registry, int tier) {
		super();
		this.TIER = tier;
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
	
	        	getTE(worldIn, pos).ShootBullet(pos, worldIn, playerIn, facing.getAxis());
	        	getTE(worldIn, pos).PlaySound(SoundEvents.ENTITY_SHULKER_AMBIENT, pos, worldIn);
	        	
	        	getTE(worldIn, pos).holidayCheck(new FakeShulker(worldIn), worldIn, pos, false);
	        }
	        return true;
		}
		else
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.RED + I18n.translateToLocal("shulker.info"));
	}
}
