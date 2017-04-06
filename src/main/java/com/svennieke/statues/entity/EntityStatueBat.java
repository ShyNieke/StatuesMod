package com.svennieke.statues.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityStatueBat extends EntityBat{

    private int angerLevel;
	public static String NAME = "Statue Bat";
	
	public EntityStatueBat(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
    }

}
