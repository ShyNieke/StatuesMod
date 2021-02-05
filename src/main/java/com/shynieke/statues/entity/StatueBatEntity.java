package com.shynieke.statues.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;

public class StatueBatEntity extends BatEntity {
    public StatueBatEntity(EntityType<? extends StatueBatEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int random = getRNG().nextInt(10);
        if(random < 5) {
            addPotionEffect(new EffectInstance(Effects.SPEED, 2000 * 20, 2, true, false));
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof LivingEntity) {
            LivingEntity entitylivingbase = (LivingEntity)source.getImmediateSource();

            if (!source.isExplosion()) {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    public static boolean canSpawnHere(EntityType<StatueBatEntity> batIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        if (pos.getY() >= worldIn.getSeaLevel()) {
            return false;
        } else {
            int i = worldIn.getLight(pos);
            int j = 4;
            if (isNearHalloween()) {
                j = 7;
            } else if (randomIn.nextBoolean()) {
                return false;
            }

            return i > randomIn.nextInt(j) ? false : canSpawnOn(batIn, worldIn, reason, pos, randomIn);
        }
    }

    private static boolean isNearHalloween() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 10 && i >= 20 || j == 11 && i <= 3;
    }
}
