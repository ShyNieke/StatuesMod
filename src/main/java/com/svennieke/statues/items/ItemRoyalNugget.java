package com.svennieke.statues.items;

import java.util.List;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class ItemRoyalNugget extends ItemFood {
	public ItemRoyalNugget(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setUnlocalizedName(Reference.StatuesItems.ROYALNUGGET.getUnlocalisedName());
		setRegistryName(Reference.StatuesItems.ROYALNUGGET.getRegistryName());
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("royalnugget.info"));
	}
}
