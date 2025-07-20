package com.shynieke.statues.entity;

import com.shynieke.statues.config.StatuesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

public class StatueBatEntity extends Bat {
	public StatueBatEntity(EntityType<? extends StatueBatEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Bat.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, EntitySpawnReason reason, @Nullable SpawnGroupData spawnDataIn) {
		SpawnGroupData data = super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
		int random = getRandom().nextInt(10);
		if (random < 5) {
			addEffect(new MobEffectInstance(MobEffects.SPEED, 2000 * 20, 2, true, false));
		}
		return data;
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) &&
				source.getDirectEntity() instanceof LivingEntity livingEntity) {
			livingEntity.hurtServer(serverLevel, this.damageSources().thorns(this), 2.0F);
		}

		return super.hurtServer(serverLevel, source, amount);
	}

	public static boolean canSpawnHere(EntityType<StatueBatEntity> batIn, LevelAccessor levelAccessor, EntitySpawnReason reason, BlockPos pos, RandomSource randomIn) {
		if (!StatuesConfig.COMMON.statueBatSpawning.get()) {
			return false;
		}
		if (pos.getY() >= levelAccessor.getSeaLevel()) {
			return false;
		} else {
			int i = levelAccessor.getMaxLocalRawBrightness(pos);
			int j = 4;
			if (isHalloween()) {
				j = 7;
			} else if (randomIn.nextBoolean()) {
				return false;
			}

			return i > randomIn.nextInt(j) ? false : checkMobSpawnRules(batIn, levelAccessor, reason, pos, randomIn);
		}
	}

	private static boolean isHalloween() {
		LocalDate localdate = LocalDate.now();
		int i = localdate.getDayOfMonth();
		int j = localdate.getMonthValue();
		return j == 10 && i >= 20 || j == 11 && i <= 3;
	}
}
