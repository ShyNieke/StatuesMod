package com.svennieke.statues.entity;

import com.svennieke.statues.init.StatuesEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;

public class EntityStatueBat extends EntityBat{
	
    public EntityStatueBat(World worldIn) {
		super(worldIn);
	}
    
	@Override
	public EntityType<?> getType() {
		return StatuesEntity.STATUE_BAT;
	}
	
	public static String NAME = "Statue Bat";
	
	@Override
	protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
    }
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entityLivingData,
			NBTTagCompound itemNbt) {
		int random = new Random().nextInt(10);
		if(random < 5) {
			addPotionEffect(new PotionEffect(MobEffects.SPEED, 2000 * 20, 2, true, false));
		}
		return super.onInitialSpawn(difficulty, entityLivingData, itemNbt);
	}
	 
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();

            if (!source.isExplosion())
            {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            }
        }
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean canSpawn(IWorld worldIn, boolean value) {
		BlockPos blockpos = new BlockPos(this.posX, this.getBoundingBox().minY, this.posZ);
	    if (blockpos.getY() >= worldIn.getSeaLevel()) {
	    	return false;
	    } else {
	        int i = worldIn.getLight(blockpos);
	        int j = 4;
	        if (this.isDateAroundHalloween()) {
	        	 j = 7;
	        } else if (this.rand.nextBoolean()) {
	        	 return false;
	        }

	        return i > this.rand.nextInt(j) ? false : super.canSpawn(worldIn, value) && dimension.getId() == DimensionType.OVERWORLD.getId();
	    }
	}
	
	private boolean isDateAroundHalloween() {
	     LocalDate localdate = LocalDate.now();
	     int i = localdate.get(ChronoField.DAY_OF_MONTH);
	     int j = localdate.get(ChronoField.MONTH_OF_YEAR);
	     return j == 10 && i >= 20 || j == 11 && i <= 3;
	}
	
    @Override
    protected ResourceLocation getLootTable()
    {
    	return new ResourceLocation("statues:entity/statuebat");
    }
}
