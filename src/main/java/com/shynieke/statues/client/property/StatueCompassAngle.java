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

	public StatueCompassAngle(boolean b) {
		this(new StatueCompassAngleState(b));
	}

	private StatueCompassAngle(StatueCompassAngleState p_388477_) {
		this.state = p_388477_;
	}

	@Override
	public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity, int p_387210_) {
		return this.state.get(stack, level, livingEntity, p_387210_);
	}

	@Override
	public MapCodec<? extends RangeSelectItemModelProperty> type() {
		return MAP_CODEC;
	}
}
