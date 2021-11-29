package com.shynieke.statues.items;

import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StatueBeeItem extends StatueBlockItem {
    public StatueBeeItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.hasCustomHoverName() && !worldIn.isClientSide) {
            final String name = stack.getHoverName().getContents();
            if (entityIn instanceof PlayerEntity) {
                if(name.equalsIgnoreCase("Trans Bee")) {
                    ((PlayerEntity) entityIn).inventory.setItem(itemSlot, new ItemStack(StatueRegistry.TRANS_BEE.get(), stack.getCount(), stack.getTag()));
                } else if (name.equalsIgnoreCase("Tropibee")) {
                    ((PlayerEntity) entityIn).inventory.setItem(itemSlot, new ItemStack(StatueRegistry.TROPIBEE.get(), stack.getCount(), stack.getTag()));
                }
            }
        }
    }
}
