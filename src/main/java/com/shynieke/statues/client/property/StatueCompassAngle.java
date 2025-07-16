package com.shynieke.statues.client.property;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class StatueCompassAngle implements RangeSelectItemModelProperty {
	public static final MapCodec<StatueCompassAngle> MAP_CODEC = StatueCompassAngleState.MAP_CODEC.xmap(StatueCompassAngle::new,
			angle -> angle.state);
	private final StatueCompassAngleState state;

	public StatueCompassAngle(boolean wobble) {
		this(new StatueCompassAngleState(wobble));
	}

	private StatueCompassAngle(StatueCompassAngleState state) {
		this.state = state;
	}

	@Override
	public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity, int seed) {
		return this.state.get(stack, level, livingEntity, seed);
	}

	@Override
	public MapCodec<? extends RangeSelectItemModelProperty> type() {
		return MAP_CODEC;
	}
}
