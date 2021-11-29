package com.shynieke.statues.items;

import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class StatueTeaItem extends Item {

    public StatueTeaItem(Properties builder, Food food) {
        super(builder.food(food).stacksTo(8));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityIn) {
        if(entityIn instanceof PlayerEntity) {
            PlayerEntity playerIn = entityIn instanceof PlayerEntity ? (PlayerEntity)entityIn : null;
            playerIn.eat(worldIn, stack);

            if (playerIn instanceof ServerPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerIn, stack);
            }

            playerIn.awardStat(Stats.ITEM_USED.get(this));

            if (!playerIn.abilities.instabuild) {
                if (stack.isEmpty()) {
                    return new ItemStack(StatueRegistry.CUP.get());
                }

                playerIn.inventory.add(new ItemStack(StatueRegistry.CUP.get()));
            }
        }

        return stack;
    }
}
