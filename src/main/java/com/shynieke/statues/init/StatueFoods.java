package com.shynieke.statues.init;

import net.minecraft.world.food.FoodProperties;

public class StatueFoods {
    public static final FoodProperties ROYAL_NUGGET = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.1F).build();
    public static final FoodProperties TEA = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).build();
    public static final FoodProperties CUP = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).build();
    public static final FoodProperties SOUP = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.3F).build();

    public static final FoodProperties MARSHMALLOW = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.2F).build();
    public static final FoodProperties COOKED_MARSHMALLOW = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).build();
    public static final FoodProperties GOLDEN_MARSHMALLOW = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.2F).build();
}
