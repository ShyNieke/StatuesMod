package com.shynieke.statues.blocks.decorative;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PebbleBlock extends FallingBlock {
    public PebbleBlock(Block.Properties properties) {
        super(properties.strength(0.6F).sound(SoundType.GRAVEL));
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState blockState) {
        return -8356741;
    }
}
