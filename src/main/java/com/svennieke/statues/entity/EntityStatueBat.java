package com.svennieke.statues.entity;

import java.util.Calendar;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class EntityStatueBat extends EntityBat{

    private int angerLevel;
	public static String NAME = "Statue Bat";
	
	public EntityStatueBat(World worldIn) {
		super(worldIn);
	}
	
	//public static final ResourceLocation LOOT = new ResourceLocation(Reference.MOD_ID, "entities/statue_bat");
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
    }

	public static void registerFixesStatueBat(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, "StatueBat");
    }
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		int random = new Random().nextInt(10);
		if(random < 5) {
			addPotionEffect(new PotionEffect(MobEffects.SPEED, 2000 * 20, 2, true, false));
		}
		return super.onInitialSpawn(difficulty, livingdata);
	}
	 
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)source.getSourceOfDamage();

            if (!source.isExplosion())
            {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            }
        }
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (blockpos.getY() >= this.worldObj.getSeaLevel())
        {
            return false;
        }
        else
        {
            int i = this.worldObj.getLightFromNeighbors(blockpos);
            int j = 4;

            if (this.isDateAroundHalloween(this.worldObj.getCurrentDate()))
            {
                j = 7;
            }
            else if (this.rand.nextBoolean())
            {
                return false;
            }

            return i > this.rand.nextInt(j) ? false : super.getCanSpawnHere() && dimension == DimensionType.OVERWORLD.getId();
        }
	}
	
	private boolean isDateAroundHalloween(Calendar p_175569_1_)
    {
        return p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20 || p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3;
    }
	
    @Override
    protected ResourceLocation getLootTable()
    {
    	return new ResourceLocation("statues:entity/statuebat");
    }
}
