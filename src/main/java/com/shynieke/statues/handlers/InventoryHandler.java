package com.shynieke.statues.handlers;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InventoryHandler {
	@SubscribeEvent
	public void onKill(LivingDeathEvent event) {
		final LivingEntity target = event.getEntity();
		Entity source = event.getSource().getEntity();
		if (source instanceof Player player) {
			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack stack = inventory.getItem(i);
				if (stack.getItem() instanceof StatueBlockItem statue && statue.matchesEntity(target)) {
					increaseKillCounter(stack);
					break;
				}
			}
		}
	}

	private void increaseKillCounter(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt(Reference.KILL_COUNT, tag.getInt(Reference.KILL_COUNT) + 1);
		tag.putInt(Reference.LEVEL, getLevel(tag.getInt(Reference.KILL_COUNT)));
		stack.setTag(tag);
	}

	public int getLevel(int killedMobs) {
		if (killedMobs >= 0 && killedMobs <= 9) {
			return 1;
		} else if (killedMobs >= 10 && killedMobs <= 29) {
			return 2;
		} else if (killedMobs >= 30 && killedMobs <= 49) {
			return 3;
		}

		return 0;
	}
}
