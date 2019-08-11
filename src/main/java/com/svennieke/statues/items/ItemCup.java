package com.svennieke.statues.items;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemCup extends ItemFood {
	public ItemCup(int amount, float saturation, String unlocalised) {
		super(amount, saturation, false);
		setTranslationKey(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
		setAlwaysEdible();
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        tooltip.add(TextFormatting.GOLD + I18n.format(Reference.MOD_PREFIX + "cup.info"));
    }
}
