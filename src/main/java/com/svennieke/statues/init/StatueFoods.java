package com.svennieke.statues.init;

import net.minecraft.item.Food;

public class StatueFoods {
    public static final Food ROYAL_NUGGET = (new Food.Builder()).hunger(4).saturation(0.1F).build();
    public static final Food TEA = (new Food.Builder()).hunger(4).saturation(0.5F).build();
    public static final Food CUP = (new Food.Builder()).hunger(1).saturation(0.2F).build();
    public static final Food SOUP = (new Food.Builder()).hunger(6).saturation(0.3F).build();

    public static final Food MARSHMALLOW = (new Food.Builder()).hunger(4).saturation(0.2F).build();
    public static final Food COOKED_MARSHMALLOW = (new Food.Builder()).hunger(6).saturation(0.4F).build();
    public static final Food GOLDEN_MARSHMALLOW = (new Food.Builder()).hunger(5).saturation(0.2F).build();
}
