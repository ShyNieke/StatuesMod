package com.shynieke.statues.items;

import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StatueTeaItem extends Item {

	public StatueTeaItem(Properties builder, FoodProperties food) {
		super(builder.food(food).stacksTo(8));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityIn) {
		if (entityIn instanceof Player) {
			Player playerIn = entityIn instanceof Player ? (Player) entityIn : null;
			playerIn.eat(level, stack);

			if (playerIn instanceof ServerPlayer) {
				CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerIn, stack);
			}

			playerIn.awardStat(Stats.ITEM_USED.get(this));

			if (!playerIn.getAbilities().instabuild) {
				if (stack.isEmpty()) {
					return new ItemStack(StatueRegistry.CUP.get());
				}

				playerIn.getInventory().add(new ItemStack(StatueRegistry.CUP.get()));
			}
		}

		return stack;
	}
}
