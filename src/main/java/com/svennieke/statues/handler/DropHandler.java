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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.util.Random;

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
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
			if (entity instanceof EntityBlaze) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.blaze_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
			if (entity instanceof EntitySnowman) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.snowgolem_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
			if (entity instanceof EntityCow) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.cow_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
			
	        if (entity instanceof EntityMooshroom) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.mooshroom_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
	        }
	        
	        if (entity instanceof EntityChicken) {
				random_drop = Math.random();
	            if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.chicken_statue, 1);
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
			}
	        
	        if (entity instanceof EntityCreeper) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.creeper_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityPig) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pig_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityRabbit) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntitySheep) {
	        	random_drop = Math.random();
	        	if (((EntitySheep) entity).getSheared() == true)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheepshaven_statue, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.WHITE)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_white, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.ORANGE)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_orange, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.MAGENTA)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_magenta, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIGHT_BLUE)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightblue, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.YELLOW)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_yellow, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIME)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lime, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PINK)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_pink, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GRAY)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_gray, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.SILVER)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightgray, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.CYAN)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_cyan, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PURPLE)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_purple, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLUE)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_blue, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BROWN)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_brown, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GREEN)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_green, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.RED)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_red, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLACK)
	        	{
	        		if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_black, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}   	
	        }
	        
	        if (entity instanceof EntityZombie) {
	        	random_drop = Math.random();
	        	if(((EntityZombie) entity).isChild())
	        	{
		        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
		        	{
		        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.baby_zombie_statue, 1);
		        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		        	}
	        	}
	        }
	        
	        if (entity instanceof EntitySquid) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.squid_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
	        
	        if (entity instanceof EntityVillager) {
	        	random_drop = Math.random();
	        	if ( random_drop < StatuesConfigGen.general.OldDropChance )
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_statue, 1);
	        		event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	        	}
	        }
        }
		
		if(StatuesConfigGen.general.PlayersDropStatue == true)
		{
			if (entity instanceof EntityPlayer) {
				if(event.getSource().getTrueSource() instanceof EntityPlayer)
				{
					int random = entity.world.rand.nextInt(StatuesConfigGen.general.PlayerDropChance);
					
					EntityPlayer player = (EntityPlayer) entity;
					if(random < 1)
					{
						ItemStack playerstatuestack = new ItemStack(StatuesBlocks.player_statue, 1).setStackDisplayName(player.getName());
						event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
					}
				}
			}
		}
	}
}