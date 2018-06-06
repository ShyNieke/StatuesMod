package com.svennieke.statues.blocks.Statues;

import java.util.ArrayList;
import java.util.List;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockVillager;
import com.svennieke.statues.compat.list.StatueLootList;
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

public class BlockEvoker_Statue extends BlockVillager implements iStatue{
		
	public BlockEvoker_Statue(String unlocalised, String registry) {
		super();
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.RED + I18n.translateToLocal("evoker.info"));
	}
}
