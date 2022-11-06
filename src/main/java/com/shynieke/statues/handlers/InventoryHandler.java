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
				if (stack.getCount() == 1 && stack.getItem() instanceof StatueBlockItem statue &&
						upgraded(stack) && statue.matchesEntity(target)) {
					increaseKillCounter(stack);
					break;
				}
			}
		}
	}

	private boolean upgraded(ItemStack stack) {
		return stack.getTag() != null && stack.getTag().getBoolean(Reference.UPGRADED);
	}

	private void increaseKillCounter(ItemStack stack) {
		CompoundTag tag = new CompoundTag();
		tag.putInt(Reference.KILL_COUNT, tag.getInt(Reference.KILL_COUNT) + 1);
		int level = getLevel(tag.getInt(Reference.KILL_COUNT));
		if (tag.getInt(Reference.LEVEL) != level) {
			tag.putInt(Reference.LEVEL, level);
			tag.putInt(Reference.UPGRADE_SLOTS, tag.getInt(Reference.UPGRADE_SLOTS) + 1);
		}
		stack.addTagElement("BlockEntityTag", tag);

	}

	public int getLevel(int killedMobs) {
		if (killedMobs >= 6 && killedMobs <= 16) {
			return 1;
		} else if (killedMobs >= 16 && killedMobs <= 28) {
			return 2;
		} else if (killedMobs >= 28 && killedMobs <= 44) {
			return 3;
		} else if (killedMobs >= 44 && killedMobs <= 58) {
			return 4;
		} else if (killedMobs >= 58 && killedMobs <= 76) {
			return 5;
		} else if (killedMobs >= 76 && killedMobs <= 96) {
			return 6;
		}

		return 0;
	}
}
