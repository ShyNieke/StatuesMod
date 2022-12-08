package com.shynieke.statues.blocks.statues;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class BrownMooshroomStatueBlock extends MooshroomStatueBlock {
	public BrownMooshroomStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.COW_AMBIENT;
	}
}
