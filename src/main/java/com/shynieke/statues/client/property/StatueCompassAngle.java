package com.shynieke.statues.client.property;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

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
	public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
		return this.state.get(stack, level, owner, seed);
	}

	@Override
	public MapCodec<? extends RangeSelectItemModelProperty> type() {
		return MAP_CODEC;
	}
}
