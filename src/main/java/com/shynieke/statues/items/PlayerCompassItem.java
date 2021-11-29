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
        super(builder.tab(StatueTabs.STATUES_ITEMS));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        PlayerEntity playerIn = context.getPlayer();
        if(!worldIn.isClientSide && playerIn != null && playerIn.isShiftKeyDown() && worldIn.getBlockState(pos).getBlock() instanceof PlayerStatueBlock) {
            playerIn.setItemInHand(context.getHand(), new ItemStack(Items.COMPASS));
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World reader, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        if (tag != null && !tag.getString("playerTracking").isEmpty()) {
            tooltip.add(new TranslationTextComponent("statues.last.known.location", tag.getString("playerTracking")).withStyle(TextFormatting.GOLD));
        }
    }
}