package com.shynieke.statues.blocks.decorative;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PebbleBlock extends FallingBlock {
    public PebbleBlock(Properties properties) {
        super(properties.strength(0.6F).sound(SoundType.GRAVEL));
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState blockState) {
        return -8356741;
    }
}
