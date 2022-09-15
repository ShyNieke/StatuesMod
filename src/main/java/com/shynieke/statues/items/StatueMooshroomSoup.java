package com.shynieke.statues.items;

import com.shynieke.statues.init.StatueFoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class StatueMooshroomSoup extends Item {

	public StatueMooshroomSoup(Properties builder) {
		super(builder.food(StatueFoods.SOUP).stacksTo(8));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityIn) {
		if (entityIn instanceof Player playerIn && !((Player) entityIn).getAbilities().instabuild) {
			ItemStack bowlStack = new ItemStack(Items.BOWL);
			Inventory playerInv = playerIn.getInventory();
			playerIn.eat(level, stack);

			if (playerIn instanceof ServerPlayer) {
				CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerIn, stack);
			}

			if (!level.isClientSide) {
				if (playerInv.getFreeSlot() == -1) {
					playerIn.spawnAtLocation(bowlStack, 0F);
				} else {
					playerInv.add(bowlStack);
				}
			}
		}
		return stack;
	}
}
