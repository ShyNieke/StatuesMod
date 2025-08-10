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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class StatueCompassAngleState extends NeedleDirectionHelper {
	public static final MapCodec<StatueCompassAngleState> MAP_CODEC = RecordCodecBuilder.mapCodec(
			p_387422_ -> p_387422_.group(
							Codec.BOOL.optionalFieldOf("wobble", Boolean.TRUE).forGetter(StatueCompassAngleState::wobble)
					)
					.apply(p_387422_, StatueCompassAngleState::new)
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
	protected float calculate(ItemStack stack, ClientLevel level, int seed, Entity targetPos) {
		PlayerCompassData compassData = stack.get(StatueDataComponents.PLAYER_COMPASS_DATA.get());
		GlobalPos globalpos = compassData != null ? compassData.globalPos() : null;
		long i = level.getGameTime();
		return !isValidCompassTargetPos(targetPos, globalpos)
				? this.getRandomlySpinningRotation(seed, i)
				: this.getRotationTowardsCompassTarget(targetPos, i, globalpos.pos());
	}

	private float getRandomlySpinningRotation(int seed, long gameTime) {
		if (this.noTargetWobbler.shouldUpdate(gameTime)) {
			this.noTargetWobbler.update(gameTime, this.random.nextFloat());
		}

		float f = this.noTargetWobbler.rotation() + (float) hash(seed) / 2.1474836E9F;
		return Mth.positiveModulo(f, 1.0F);
	}

	private float getRotationTowardsCompassTarget(Entity entity, long gameTime, BlockPos targetOis) {
		float f = (float) getAngleFromEntityToPos(entity, targetOis);
		float f1 = getWrappedVisualRotationY(entity);
		if (entity instanceof Player player && player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
			if (this.wobbler.shouldUpdate(gameTime)) {
				this.wobbler.update(gameTime, 0.5F - (f1 - 0.25F));
			}

			float f3 = f + this.wobbler.rotation();
			return Mth.positiveModulo(f3, 1.0F);
		}

		float f2 = 0.5F - (f1 - 0.25F - f);
		return Mth.positiveModulo(f2, 1.0F);
	}

	private static boolean isValidCompassTargetPos(Entity entity, @Nullable GlobalPos pos) {
		return pos != null
				&& pos.dimension() == entity.level().dimension()
				&& !(pos.pos().distToCenterSqr(entity.position()) < 1.0E-5F);
	}

	private static double getAngleFromEntityToPos(Entity entity, BlockPos pos) {
		Vec3 vec3 = Vec3.atCenterOf(pos);
		return Math.atan2(vec3.z() - entity.getZ(), vec3.x() - entity.getX()) / (float) (Math.PI * 2);
	}

	private static float getWrappedVisualRotationY(Entity entity) {
		return Mth.positiveModulo(entity.getVisualRotationYInDegrees() / 360.0F, 1.0F);
	}

	private static int hash(int seed) {
		return seed * 1327217883;
	}
}