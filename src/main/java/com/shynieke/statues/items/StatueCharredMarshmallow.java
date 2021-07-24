package com.shynieke.statues.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class StatueCharredMarshmallow extends Item {
    public StatueCharredMarshmallow(Properties builder) {
        super(builder);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 500;
    }
}
