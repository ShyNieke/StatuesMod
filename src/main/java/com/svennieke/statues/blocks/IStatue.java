package com.svennieke.statues.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public interface IStatue {
    public <T extends Block> T setTier(int tier);
    public int getTier();

    default public Block setColor(EnumDyeColor color) { return Blocks.AIR; };
    default public EnumDyeColor getColor() { return EnumDyeColor.WHITE; };
}
