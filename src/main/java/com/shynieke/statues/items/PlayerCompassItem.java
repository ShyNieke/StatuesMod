package com.shynieke.statues.items;

import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.init.StatueTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlayerCompassItem extends Item {

	public PlayerCompassItem(Item.Properties builder) {
		super(builder.tab(StatueTabs.STATUES_ITEMS));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player playerIn = context.getPlayer();
		if (!level.isClientSide && playerIn != null && playerIn.isShiftKeyDown() && level.getBlockState(pos).getBlock() instanceof PlayerStatueBlock) {
			playerIn.setItemInHand(context.getHand(), new ItemStack(Items.COMPASS));
		}
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level reader, List<Component> tooltip, TooltipFlag flag) {
		CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
		if (tag != null && !tag.getString("playerTracking").isEmpty()) {
			tooltip.add(Component.translatable("statues.last.known.location", tag.getString("playerTracking")).withStyle(ChatFormatting.GOLD));
		}
	}
}