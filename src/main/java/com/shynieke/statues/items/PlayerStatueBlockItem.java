package com.shynieke.statues.items;

import com.shynieke.statues.blockentities.PlayerBlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.Block;

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
}
