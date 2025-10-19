package com.shynieke.statues.items;

import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class StatueBeeItem extends StatueBlockItem {
	public StatueBeeItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
		if (stack.has(DataComponents.CUSTOM_NAME) && !level.isClientSide()) {
			final String name = stack.getHoverName().getString();
			if (entity instanceof Player player) {
				int itemSlot = player.getInventory().findSlotMatchingItem(stack);
				if (name.equalsIgnoreCase("Trans Bee")) {
					player.getInventory().setItem(itemSlot, new ItemStack(StatueRegistry.TRANS_BEE_STATUE.toStack().getItemHolder(), stack.getCount(), stack.getComponentsPatch()));
				} else if (name.equalsIgnoreCase("Tropibee")) {
					player.getInventory().setItem(itemSlot, new ItemStack(StatueRegistry.TROPIBEE.toStack().getItemHolder(), stack.getCount(), stack.getComponentsPatch()));
				}
			}
		}
	}
}
