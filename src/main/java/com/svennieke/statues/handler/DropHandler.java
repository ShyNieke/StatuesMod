package com.svennieke.statues.handler;

import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropHandler {

	public static double random_drop;
    public static int dropped;
	
	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		if(StatuesConfigGen.general.Tier1Crafting == false)
		{
			if (entity instanceof EntitySlime) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.slime_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
			if (entity instanceof EntityBlaze) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.blaze_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
			if (entity instanceof EntitySnowman) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.snowgolem_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
			if (entity instanceof EntityCow) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.cow_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
	        if (entity instanceof EntityMooshroom) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.mooshroom_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
	        }
	        
	        if (entity instanceof EntityChicken) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.chicken_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
	        
	        if (entity instanceof EntityCreeper) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.creeper_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityPig) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pig_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityRabbit) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntitySheep) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityZombie) {
	        	random_drop = Math.random();
	        	if(((EntityZombie) entity).isChild())
	        	{
		        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.baby_zombie_statue, 1);
		        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        }
	        
	        if (entity instanceof EntitySquid) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.squid_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityVillager) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
        }
	}
}