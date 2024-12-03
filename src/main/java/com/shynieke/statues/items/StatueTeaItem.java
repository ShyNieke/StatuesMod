package com.shynieke.statues.items;

import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

public class StatueTeaItem extends Item {

	public StatueTeaItem(Properties builder, FoodProperties food) {
		super(builder.food(food).stacksTo(8));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof Player playerIn) {
			Consumable consumable = stack.get(DataComponents.CONSUMABLE);
			if (consumable != null) {
				consumable.onConsume(level, playerIn, stack);
			}

			if (playerIn instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
			}

			playerIn.awardStat(Stats.ITEM_USED.get(this));

			if (!playerIn.hasInfiniteMaterials()) {
				if (stack.isEmpty()) {
					return new ItemStack(StatueRegistry.CUP.get());
				}

				playerIn.getInventory().add(new ItemStack(StatueRegistry.CUP.get()));
			}
		}

		return stack;
	}
}
