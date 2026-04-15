package com.shynieke.statues.client.property;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.datacomponent.PlayerCompassData;
import com.shynieke.statues.registry.StatueDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.NeedleDirectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class StatueCompassAngleState extends NeedleDirectionHelper {
	public static final MapCodec<StatueCompassAngleState> MAP_CODEC = RecordCodecBuilder.mapCodec(
			wobble -> wobble.group(
							Codec.BOOL.optionalFieldOf("wobble", Boolean.FALSE).forGetter(StatueCompassAngleState::wobble)
					)
					.apply(wobble, StatueCompassAngleState::new)
	);
	private final NeedleDirectionHelper.Wobbler wobbler;
	private final NeedleDirectionHelper.Wobbler noTargetWobbler;
	private final RandomSource random = RandomSource.create();

	public StatueCompassAngleState(boolean wobble) {
		super(wobble);
		this.wobbler = this.newWobbler(0.8F);
		this.noTargetWobbler = this.newWobbler(0.8F);
	}

	@Override
	protected float calculate(ItemStack stack, ClientLevel level, int seed, ItemOwner owner) {
		PlayerCompassData compassData = stack.get(StatueDataComponents.PLAYER_COMPASS_DATA.get());
		GlobalPos globalpos = compassData != null ? compassData.globalPos() : null;
		long i = level.getGameTime();
		boolean valid = isValidCompassTargetPos(owner, globalpos);
		return !valid
				? this.getRandomlySpinningRotation(seed, i)
				: this.getRotationTowardsCompassTarget(owner, i, globalpos.pos());
	}

	private float getRandomlySpinningRotation(int seed, long gameTime) {
		if (this.noTargetWobbler.shouldUpdate(gameTime)) {
			this.noTargetWobbler.update(gameTime, this.random.nextFloat());
		}

		float f = this.noTargetWobbler.rotation() + (float) hash(seed) / 2.1474836E9F;
		return Mth.positiveModulo(f, 1.0F);
	}

	private float getRotationTowardsCompassTarget(ItemOwner owner, long gameTime, BlockPos targetOis) {
		float f = (float) getAngleFromEntityToPos(owner, targetOis);
		float f1 = getWrappedVisualRotationY(owner);
		if (owner instanceof Player player && player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
			if (this.wobbler.shouldUpdate(gameTime)) {
				this.wobbler.update(gameTime, 0.5F - (f1 - 0.25F));
			}

			float f3 = f + this.wobbler.rotation();
			return Mth.positiveModulo(f3, 1.0F);
		}

		float f2 = 0.5F - (f1 - 0.25F - f);
		return Mth.positiveModulo(f2, 1.0F);
	}

	private static boolean isValidCompassTargetPos(ItemOwner owner, @Nullable GlobalPos pos) {
		return pos != null
				&& pos.dimension() == owner.level().dimension()
				&& !(pos.pos().distToCenterSqr(owner.position()) < 1.0E-5F);
	}

	private static double getAngleFromEntityToPos(ItemOwner owner, BlockPos pos) {
		Vec3 vec3 = Vec3.atCenterOf(pos);
		return Math.atan2(vec3.z() - owner.position().z(), vec3.x() - owner.position().x()) / (float) (Math.PI * 2);
	}

	private static float getWrappedVisualRotationY(ItemOwner owner) {
		return Mth.positiveModulo(owner.getVisualRotationYInDegrees() / 360.0F, 1.0F);
	}

	private static int hash(int seed) {
		return seed * 1327217883;
	}
}