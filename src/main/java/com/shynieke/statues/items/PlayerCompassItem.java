package com.shynieke.statues.items;

import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.init.StatueTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
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
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity playerIn = context.getPlayer();
        if(!worldIn.isRemote && playerIn != null && playerIn.isSneaking() && worldIn.getBlockState(pos).getBlock() instanceof PlayerStatueBlock) {
            playerIn.setHeldItem(context.getHand(), new ItemStack(Items.COMPASS));
        }
        return super.onItemUse(context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World reader, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(stack.hasTag()) {
            CompoundNBT tag = stack.getTag();
            if (!tag.getString("playerTracking").isEmpty()) {
                tooltip.add(new TranslationTextComponent("statues.last.known.location", tag.getString("playerTracking")).mergeStyle(TextFormatting.GOLD));
            }
        }
    }
}