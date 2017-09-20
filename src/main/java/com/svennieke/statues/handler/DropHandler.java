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
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropHandler {

	public static double random_drop;
    public static int dropped;
	
	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		Entity source = event.getSource().getTrueSource();
		
		if(StatuesConfigGen.general.Tier1Crafting == false)
		{
			if (entity instanceof EntitySlime) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.slime_statue, 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
			if (entity instanceof EntityBlaze) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.blaze_statue, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
			if (entity instanceof EntitySnowman) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.snowgolem_statue, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
			if (entity instanceof EntityCow) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.cow_statue, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
	        if (entity instanceof EntityMooshroom) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.mooshroom_statue, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityChicken) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.chicken_statue, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
	        
	        if (entity instanceof EntityCreeper) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.creeper_statue, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityPig) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pig_statue, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityRabbit) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_statue, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntitySheep) {
	        	random_drop = Math.random();
	        	if (((EntitySheep) entity).getSheared() == true)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheepshaven_statue, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.WHITE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_white, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.ORANGE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_orange, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.MAGENTA)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_magenta, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIGHT_BLUE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightblue, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.YELLOW)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_yellow, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIME)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lime, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PINK)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_pink, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GRAY)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_gray, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.SILVER)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightgray, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.CYAN)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_cyan, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PURPLE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_purple, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLUE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_blue, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BROWN)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_brown, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GREEN)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_green, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.RED)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_red, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLACK)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_black, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}   	
	        }
	        
	        if (entity instanceof EntityZombie) {
	        	if(((EntityZombie) entity).isChild())
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.baby_zombie_statue, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        }
	        
	        if (entity instanceof EntitySquid) {
        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.squid_statue, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityVillager) {
        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_statue, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
        }
		
		if(StatuesConfigGen.player.PlayersDropStatue == true)
		{
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack playerstatuestack = new ItemStack(StatuesBlocks.player_statue, 1).setStackDisplayName(player.getName());
				
				switch (StatuesConfigGen.player.PlayerStatueKillSource)
				{
					case PLAYER:
						if(source instanceof EntityPlayer && !(source instanceof FakePlayer))
						{
							int random = entity.world.rand.nextInt(StatuesConfigGen.player.PlayerDropChance);
							if(random < 1)
							{
							event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
							}
						}
					case PLAYER_FAKEPLAYER:
						if(source instanceof EntityPlayer)
						{
							int random = entity.world.rand.nextInt(StatuesConfigGen.player.PlayerDropChance);
							if(random < 1)
							{
							event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
							}
						}
					case ALL:
						int random = entity.world.rand.nextInt(StatuesConfigGen.player.PlayerDropChance);
						if(random < 1)
						{
						event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
						}
				}
			}
		}
	}
	
	public void DropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		switch (StatuesConfigGen.general.StatueKillSource)
		{
			case PLAYER:
				if(source instanceof EntityPlayer && !(source instanceof FakePlayer))
				{
					random_drop = Math.random();
					if ( random_drop < StatuesConfigGen.general.OldDropChance )
		            {
		            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		            }
				}
			case PLAYER_FAKEPLAYER:
				if(source instanceof EntityPlayer)
				{
					random_drop = Math.random();
					if ( random_drop < StatuesConfigGen.general.OldDropChance )
		            {
		            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		            }
				}
			case ALL:
				random_drop = Math.random();
				if ( random_drop < StatuesConfigGen.general.OldDropChance )
	            {
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
		}
	}
}