package com.shynieke.statues.items;

import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.datacomponent.PlayerCompassData;
import com.shynieke.statues.registry.StatueDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PlayerCompassItem extends Item {

	public PlayerCompassItem(Item.Properties builder) {
		super(builder);
	}

	@NotNull
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player playerIn = context.getPlayer();
		if (!level.isClientSide() && playerIn != null) {
			if (playerIn.isShiftKeyDown() && !(level.getBlockState(pos).getBlock() instanceof PlayerStatueBlock)) {
				playerIn.setItemInHand(context.getHand(), new ItemStack(Items.COMPASS));
				return InteractionResult.PASS;
			}
			return super.useOn(context);
		}
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
		if (stack.has(StatueDataComponents.PLAYER_COMPASS_DATA.get())) {
			PlayerCompassData data = stack.get(StatueDataComponents.PLAYER_COMPASS_DATA.get());
			if (data != null) {
				tooltipAdder.accept(Component.translatable("statues.last.known.location", data.name()).withStyle(ChatFormatting.GOLD));
			}
		}
	}
}