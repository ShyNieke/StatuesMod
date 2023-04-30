package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class WastelandStatueBlock extends AbstractStatueBase {

	public WastelandStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return RANDOM.nextBoolean() ? StatueSounds.WASTELAND_HELLO.get() : StatueSounds.WASTELAND_ONWARDS.get();
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.PIG;
	}
}
