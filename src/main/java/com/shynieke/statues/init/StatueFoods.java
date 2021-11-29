package com.shynieke.statues.init;

import net.minecraft.item.Food;

public class StatueFoods {
    public static final Food ROYAL_NUGGET = (new Food.Builder()).nutrition(4).saturationMod(0.1F).build();
    public static final Food TEA = (new Food.Builder()).nutrition(4).saturationMod(0.5F).build();
    public static final Food CUP = (new Food.Builder()).nutrition(1).saturationMod(0.2F).build();
    public static final Food SOUP = (new Food.Builder()).nutrition(6).saturationMod(0.3F).build();

    public static final Food MARSHMALLOW = (new Food.Builder()).nutrition(4).saturationMod(0.2F).build();
    public static final Food COOKED_MARSHMALLOW = (new Food.Builder()).nutrition(6).saturationMod(0.4F).build();
    public static final Food GOLDEN_MARSHMALLOW = (new Food.Builder()).nutrition(5).saturationMod(0.2F).build();
}
