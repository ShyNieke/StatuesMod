package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class VillagerStatue extends AbstractStatueBase {

	public VillagerStatue(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.VILLAGER;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.VILLAGER_AMBIENT;
	}
}
