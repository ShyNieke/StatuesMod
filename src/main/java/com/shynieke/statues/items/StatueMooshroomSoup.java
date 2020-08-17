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
        super(builder.food(StatueFoods.SOUP).maxStackSize(8));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityIn) {
        if (entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).abilities.isCreativeMode) {
            ItemStack bowlStack = new ItemStack(Items.BOWL);
            PlayerEntity playerIn = (PlayerEntity)entityIn;
            PlayerInventory playerInv = playerIn.inventory;
            playerIn.onFoodEaten(worldIn, stack);

            if (playerIn instanceof ServerPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, stack);
            }

            if(!worldIn.isRemote) {
                if(playerInv.getFirstEmptyStack() == -1) {
                    playerIn.entityDropItem(bowlStack, 0F);
                } else {
                    playerInv.addItemStackToInventory(bowlStack);
                }
            }
        }
        return stack;
    }
}
