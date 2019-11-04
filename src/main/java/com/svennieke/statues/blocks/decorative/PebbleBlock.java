package com.svennieke.statues.blocks.decorative;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PebbleBlock extends FallingBlock {
    public PebbleBlock(Properties properties) {
        super(properties.hardnessAndResistance(0.6F).sound(SoundType.GROUND));
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState p_189876_1_) {
        return -8356741;
    }
}
