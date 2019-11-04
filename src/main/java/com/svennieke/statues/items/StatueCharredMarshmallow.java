package com.svennieke.statues.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StatueCharredMarshmallow extends Item {
    public StatueCharredMarshmallow(Properties builder) {
        super(builder);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 500;
    }
}
