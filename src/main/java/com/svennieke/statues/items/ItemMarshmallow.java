package com.svennieke.statues.items;

import com.svennieke.statues.Statues;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemMarshmallow extends ItemFood {
	public ItemMarshmallow(Item.Properties builder, int amount, float saturation, String registry) {
		super(amount, saturation, false, builder.group(ItemGroup.FOOD).group(Statues.tabStatues));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("statues.marshmallow.info").applyTextStyle(TextFormatting.GOLD));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
