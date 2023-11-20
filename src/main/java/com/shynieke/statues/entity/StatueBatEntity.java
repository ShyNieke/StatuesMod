package com.shynieke.statues.entity;

import com.shynieke.statues.config.StatuesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import org.jetbrains.annotations.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class StatueBatEntity extends Bat {
	public StatueBatEntity(EntityType<? extends StatueBatEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Bat.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		int random = getRandom().nextInt(10);
		if (random < 5) {
			addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000 * 20, 2, true, false));
		}
		return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) &&
				source.getDirectEntity() instanceof LivingEntity livingEntity) {
			livingEntity.hurt(this.damageSources().thorns(this), 2.0F);
		}

		return super.hurt(source, amount);
	}

	public static boolean canSpawnHere(EntityType<StatueBatEntity> batIn, LevelAccessor levelAccessor, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
		if (!StatuesConfig.COMMON.statueBatSpawning.get()) {
			return false;
		}
		if (pos.getY() >= levelAccessor.getSeaLevel()) {
			return false;
		} else {
			int i = levelAccessor.getMaxLocalRawBrightness(pos);
			int j = 4;
			if (isNearHalloween()) {
				j = 7;
			} else if (randomIn.nextBoolean()) {
				return false;
			}

			return i > randomIn.nextInt(j) ? false : checkMobSpawnRules(batIn, levelAccessor, reason, pos, randomIn);
		}
	}

	private static boolean isNearHalloween() {
		LocalDate localdate = LocalDate.now();
		int i = localdate.get(ChronoField.DAY_OF_MONTH);
		int j = localdate.get(ChronoField.MONTH_OF_YEAR);
		return j == 10 && i >= 20 || j == 11 && i <= 3;
	}
}
