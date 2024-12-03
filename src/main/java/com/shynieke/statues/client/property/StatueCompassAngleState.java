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
							Codec.BOOL.optionalFieldOf("wobble", Boolean.valueOf(true)).forGetter(StatueCompassAngleState::wobble)
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
	protected boolean wobble() {
		return super.wobble();
	}

	@Override
	protected float calculate(ItemStack stack, ClientLevel level, int p_388073_, Entity p_388489_) {
		PlayerCompassData compassData = stack.get(StatueDataComponents.PLAYER_COMPASS_DATA.get());
		GlobalPos globalpos = compassData != null ? compassData.globalPos() : null;
		long i = level.getGameTime();
		return !isValidCompassTargetPos(p_388489_, globalpos)
				? this.getRandomlySpinningRotation(p_388073_, i)
				: this.getRotationTowardsCompassTarget(p_388489_, i, globalpos.pos());
	}

	private float getRandomlySpinningRotation(int p_388932_, long p_387198_) {
		if (this.noTargetWobbler.shouldUpdate(p_387198_)) {
			this.noTargetWobbler.update(p_387198_, this.random.nextFloat());
		}

		float f = this.noTargetWobbler.rotation() + (float) hash(p_388932_) / 2.1474836E9F;
		return Mth.positiveModulo(f, 1.0F);
	}

	private float getRotationTowardsCompassTarget(Entity p_387599_, long p_387654_, BlockPos p_388263_) {
		float f = (float) getAngleFromEntityToPos(p_387599_, p_388263_);
		float f1 = getWrappedVisualRotationY(p_387599_);
		if (p_387599_ instanceof Player player && player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
			if (this.wobbler.shouldUpdate(p_387654_)) {
				this.wobbler.update(p_387654_, 0.5F - (f1 - 0.25F));
			}

			float f3 = f + this.wobbler.rotation();
			return Mth.positiveModulo(f3, 1.0F);
		}

		float f2 = 0.5F - (f1 - 0.25F - f);
		return Mth.positiveModulo(f2, 1.0F);
	}

	private static boolean isValidCompassTargetPos(Entity p_386563_, @Nullable GlobalPos p_387891_) {
		return p_387891_ != null
				&& p_387891_.dimension() == p_386563_.level().dimension()
				&& !(p_387891_.pos().distToCenterSqr(p_386563_.position()) < 1.0E-5F);
	}

	private static double getAngleFromEntityToPos(Entity p_388327_, BlockPos p_387426_) {
		Vec3 vec3 = Vec3.atCenterOf(p_387426_);
		return Math.atan2(vec3.z() - p_388327_.getZ(), vec3.x() - p_388327_.getX()) / (float) (Math.PI * 2);
	}

	private static float getWrappedVisualRotationY(Entity p_386969_) {
		return Mth.positiveModulo(p_386969_.getVisualRotationYInDegrees() / 360.0F, 1.0F);
	}

	private static int hash(int p_387430_) {
		return p_387430_ * 1327217883;
	}
}