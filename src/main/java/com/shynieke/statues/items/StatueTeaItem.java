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
        super(builder.food(food).maxStackSize(8));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityIn) {
        if(entityIn instanceof PlayerEntity)
        {
            PlayerEntity playerIn = entityIn instanceof PlayerEntity ? (PlayerEntity)entityIn : null;
            playerIn.onFoodEaten(worldIn, stack);

            if (playerIn == null || !playerIn.abilities.isCreativeMode)
            {
                stack.shrink(1);
            }

            if (playerIn instanceof ServerPlayerEntity)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerIn, stack);
            }

            if (playerIn != null)
            {
                playerIn.addStat(Stats.ITEM_USED.get(this));
            }

            if (playerIn == null || !playerIn.abilities.isCreativeMode)
            {
                if (stack.isEmpty())
                {
                    return new ItemStack(StatueRegistry.CUP.get());
                }

                if (playerIn != null)
                {
                    playerIn.inventory.addItemStackToInventory(new ItemStack(StatueRegistry.CUP.get()));
                }
            }
        }

        return stack;
    }
}
