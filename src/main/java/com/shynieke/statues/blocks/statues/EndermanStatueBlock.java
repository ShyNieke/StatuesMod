package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class EndermanStatueBlock extends AbstractStatueBase {

	public EndermanStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENDERMAN_AMBIENT;
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.ENDERMAN;
	}
}
