package com.svennieke.statues.items;

import java.util.List;

import com.svennieke.statues.Statues;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemRoyalNugget extends ItemFood {
	public ItemRoyalNugget(int amount, float saturation, String unlocalised, String registry) {
		super(amount, saturation, false);
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        	tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("royalnugget.info"));
    }
}
