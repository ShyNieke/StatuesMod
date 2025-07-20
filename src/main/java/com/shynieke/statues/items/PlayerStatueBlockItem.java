package com.shynieke.statues.items;

import com.shynieke.statues.blockentities.PlayerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class PlayerStatueBlockItem extends StatueBlockItem {

	public PlayerStatueBlockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public Component getName(ItemStack stack) {
		ResolvableProfile resolvableprofile = stack.get(DataComponents.PROFILE);
		return (Component) (resolvableprofile != null && resolvableprofile.name().isPresent()
				? Component.translatable(this.getDescriptionId() + ".named", resolvableprofile.name().get())
				: super.getName(stack));
	}

	@Override
	public void verifyComponentsAfterLoad(ItemStack stack) {
		ResolvableProfile resolvableprofile = stack.get(DataComponents.PROFILE);
		if (resolvableprofile != null && !resolvableprofile.isResolved()) {
			PlayerBlockEntity.resolve(resolvableprofile)
					.thenAcceptAsync(profile -> stack.set(DataComponents.PROFILE, profile), PlayerBlockEntity.CHECKED_MAIN_THREAD_EXECUTOR);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
		if (Screen.hasShiftDown()) {
			MutableComponent userComponent = Component.literal("Username: ").withStyle(ChatFormatting.GOLD);
			userComponent.append(stack.getHoverName().plainCopy().withStyle(ChatFormatting.WHITE));
			tooltipAdder.accept(userComponent);

			if (stack.has(DataComponents.PROFILE)) {
				ResolvableProfile profile = stack.get(DataComponents.PROFILE);
				profile.id().ifPresent((id) -> {
					MutableComponent UUIDComponent = Component.literal("UUID: ").withStyle(ChatFormatting.GOLD);
					UUIDComponent.append(Component.literal(id.toString()).withStyle(ChatFormatting.WHITE));
					tooltipAdder.accept(UUIDComponent);
				});
			}
		}
		super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
	}
}
