package com.shynieke.statues.items;

import com.shynieke.statues.init.StatueTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PlayerCompassItem extends Item {

    public PlayerCompassItem(Item.Properties builder) {
        super(builder.group(StatueTabs.STATUES_ITEMS));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World reader, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(stack.hasTag())
        {
            CompoundNBT tag = stack.getTag();
            if (!tag.getString("playerTracking").isEmpty()) {
                tooltip.add(new TranslationTextComponent("statues.last.known.location", new Object[] {tag.getString("playerTracking")}).mergeStyle(TextFormatting.GOLD));
            }
        }
    }
}