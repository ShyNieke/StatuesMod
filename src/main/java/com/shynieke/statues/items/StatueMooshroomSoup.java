package com.shynieke.statues.items;

import com.shynieke.statues.init.StatueFoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class StatueMooshroomSoup extends Item {

    public StatueMooshroomSoup(Properties builder) {
        super(builder.food(StatueFoods.SOUP).stacksTo(8));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityIn) {
        if (entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).abilities.instabuild) {
            ItemStack bowlStack = new ItemStack(Items.BOWL);
            PlayerEntity playerIn = (PlayerEntity)entityIn;
            PlayerInventory playerInv = playerIn.inventory;
            playerIn.eat(worldIn, stack);

            if (playerIn instanceof ServerPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, stack);
            }

            if(!worldIn.isClientSide) {
                if(playerInv.getFreeSlot() == -1) {
                    playerIn.spawnAtLocation(bowlStack, 0F);
                } else {
                    playerInv.add(bowlStack);
                }
            }
        }
        return stack;
    }
}
