package com.svennieke.statues.entity;

import java.util.Calendar;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
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
        EntityLiving.registerFixesMob(fixer, EntityStatueBat.class);
    }
	
	@Override
	public boolean getCanSpawnHere() {
		BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (blockpos.getY() >= this.world.getSeaLevel())
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);
            int j = 4;

            if (this.isDateAroundHalloween(this.world.getCurrentDate()))
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
	
	/*
	@Override
	@Nullable
    protected ResourceLocation getLootTable()
    {
        return LOOT;
    }
    */
}
