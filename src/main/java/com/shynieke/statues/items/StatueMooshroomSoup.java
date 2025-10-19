package com.shynieke.statues.items;

import com.shynieke.statues.registry.StatueFoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

public class StatueMooshroomSoup extends Item {

	public StatueMooshroomSoup(Properties builder) {
		super(builder.food(StatueFoods.SOUP).stacksTo(8));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityIn) {
		if (entityIn instanceof Player playerIn && !playerIn.hasInfiniteMaterials()) {
			ItemStack bowlStack = new ItemStack(Items.BOWL);
			Inventory playerInv = playerIn.getInventory();
			Consumable consumable = stack.get(DataComponents.CONSUMABLE);
			if (consumable != null) {
				consumable.onConsume(level, playerIn, stack);
			}

			if (playerIn instanceof ServerPlayer) {
				CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerIn, stack);
			}

			if (!level.isClientSide()) {
				if (playerInv.getFreeSlot() == -1) {
					playerIn.spawnAtLocation((ServerLevel) level, bowlStack, 0F);
				} else {
					playerInv.add(bowlStack);
				}
			}
		}
		return stack;
	}
}
