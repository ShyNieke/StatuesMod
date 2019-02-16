package com.svennieke.statues.handler;

import com.svennieke.statues.Reference;
import com.svennieke.statues.config.StatuesConfig;
import com.svennieke.statues.entity.fakeentity.IFakeEntity;
import com.svennieke.statues.init.StatuesBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DropHandler {
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onLivingDrop(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		Entity source = event.getSource().getTrueSource();
		
		if(!StatuesConfig.COMMON.tier1Crafting.get())
		{
			addSombrero(entity, source, event);
			
			if (entity instanceof EntitySlime) {
				if(entity instanceof EntityMagmaCube)
				{
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.magma_statue_t1, 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				else
				{
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.slime_statue_t1, 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
			}
			
			if (entity instanceof EntityBlaze) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.blaze_statue_t1, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
			if (entity instanceof EntitySnowman) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.snowgolem_statue_t1, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
			if (entity instanceof EntityCow) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.cow_statue_t1, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
			
	        if (entity instanceof EntityMooshroom) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.mooshroom_statue_t1, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityChicken) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.chicken_statue_t1, 1);
            	DropLootStatues(entity, itemStackToDrop, source, event);
			}
	        
	        if (entity instanceof EntityCreeper) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.creeper_statue_t1, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityPig) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pig_statue_t1, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityRabbit) {
	        	EntityRabbit rabbit = (EntityRabbit)entity;
	        	
	        	if(rabbit.getRabbitType() == 0)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_br_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	else if(rabbit.getRabbitType() == 1)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_wh_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	else if(rabbit.getRabbitType() == 2)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_ws_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	else if(rabbit.getRabbitType() == 3)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_bs_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	else if(rabbit.getRabbitType() == 4)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_go_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	else if(rabbit.getRabbitType() == 5)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_bw_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        }
	        
	        if (entity instanceof EntitySheep) {
	        	if (((EntitySheep) entity).getSheared() == true)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_shaven_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.WHITE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_white_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.ORANGE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_orange_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.MAGENTA)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_magenta_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIGHT_BLUE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightblue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.YELLOW)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_yellow_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIME)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lime_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PINK)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_pink_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GRAY)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_gray_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIGHT_GRAY)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightgray_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.CYAN)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_cyan_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PURPLE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_purple_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLUE)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_blue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BROWN)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_brown_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GREEN)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_green_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.RED)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_red_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLACK)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_black_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}   	
	        }
	        
	        if (entity instanceof EntityZombie) {
	        	if (entity instanceof EntityHusk) {
		        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.husk_statue_t1, 1);
		        	DropLootStatues(entity, itemStackToDrop, source, event);
		        }
	        	else if(((EntityZombie) entity).isChild())
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.baby_zombie_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	else
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.zombie_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        }
	        
	        if (entity instanceof EntitySquid) {
        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.squid_statue_t1, 1);
        		DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityVillager) {
	        	EntityVillager villager = (EntityVillager)entity;
	        	if (villager.getProfessionForge() == VillagerRegistry.getById(0))
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_br_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (villager.getProfessionForge() == VillagerRegistry.getById(1))
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_wh_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (villager.getProfessionForge() == VillagerRegistry.getById(2))
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_pu_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	if (villager.getProfessionForge() == VillagerRegistry.getById(5))
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_gr_statue_t1, 1);
	        		DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        }
	        
	        if (entity instanceof EntityWitch) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.witch_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityShulker) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.shulker_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityEnderman) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.enderman_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityEndermite) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.endermite_statue, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityGuardian) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.guardian_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityGhast) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.ghast_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if(entity instanceof EntityEvoker || entity instanceof EntityVindicator || entity instanceof EntityVex)
	        {
	        	if(entity instanceof EntityEvoker)
	        	{
	        		ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.evoker_statue_t1, 1);
		        	DropLootStatues(entity, itemStackToDrop, source, event);
	        	}
	        	
	        	ItemStack itemStackToDrop2 = new ItemStack(StatuesBlocks.totemofundying_statue, 1);
	        	DropLootStatues(entity, itemStackToDrop2, source, event);
	        }
	        
	        if (entity instanceof EntitySpider) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.spider_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
	        
	        if (entity instanceof EntityPufferFish) {
	        	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pufferfish_statue_t1, 1);
	        	DropLootStatues(entity, itemStackToDrop, source, event);
	        }
        }
		
		if(StatuesConfig.COMMON.playersDropStatue.get())
		{
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)entity;
				ItemStack playerstatuestack = new ItemStack(StatuesBlocks.player_statue, 1).setDisplayName(player.getName());
				
				switch (StatuesConfig.COMMON.playerStatueKillSource.get())
				{
					case 1:
						if(source instanceof EntityPlayer && !(source instanceof FakePlayer) && entity != source)
						{
							int chance = StatuesConfig.COMMON.playerDropChance.get();
							if(chance != 0)
							{
								int random = entity.world.rand.nextInt(chance);
								if(random < 1)
								{
									event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
								}
							}
						}
						break;
					case 2:
						if(source instanceof EntityPlayer && entity != source)
						{
							int chance = StatuesConfig.COMMON.playerDropChance.get();
							if(chance != 0)
							{
								int random = entity.world.rand.nextInt(chance);
								if(random < 1)
								{
									event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
								}
							}
						}
						break;
					case 0:
						int chance = StatuesConfig.COMMON.playerDropChance.get();
						if(chance != 0)
						{
							int random = entity.world.rand.nextInt(chance);
							if(random < 1)
							{
								event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY +0.5, entity.posZ, playerstatuestack));
							}
						}
						break;
					default:
						break;
				}
			}
		}
		
		if(entity instanceof IFakeEntity)
		{
			event.setCanceled(true);
		}
	}
	
	public static void DropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		double random_drop;
		double default_drop_chance = StatuesConfig.COMMON.oldDropChance.get();

		switch (StatuesConfig.COMMON.statueKillSource.get())
		{
			case 1:
				if(source instanceof EntityPlayer && !(source instanceof FakePlayer))
				{
					EntityPlayerMP player = (EntityPlayerMP)source;
					List<String> LuckyPlayers = StatuesConfig.COMMON.lucky_players.get();
					if(!LuckyPlayers.isEmpty())
					{
						for (int i = 0; i < LuckyPlayers.size(); i++) 
						{
							String luckyName = LuckyPlayers.get(i);
							String user = player.getName().getString();
							
							if(!luckyName.isEmpty() && user.equals(luckyName) == true)
							{
								default_drop_chance = StatuesConfig.COMMON.oldDropChance.get() / 4;
							}
							else
							{
								default_drop_chance = StatuesConfig.COMMON.oldDropChance.get();
							}
						}
					}
					else
					{
						default_drop_chance = StatuesConfig.COMMON.oldDropChance.get();
					}
					
//					if(StatuesConfigGen.othersettings.antiAfk)
//					{
//						if(!player.getEntityData().getBoolean(FishHandler.afkKey))
//						{
//							random_drop = Math.random();
//							if ( random_drop < default_drop_chance )
//				            {
//				            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
//				            }
//						}
//					}
//					else
//					{
						random_drop = Math.random();
						if ( random_drop < default_drop_chance )
			            {
			            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
			            }
//					}
				}
				break;
			case 2:
				if(source instanceof EntityPlayer)
				{
					random_drop = Math.random();
					if ( random_drop < default_drop_chance )
		            {
		            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
		            }
				}
				break;
			case 0:
				random_drop = Math.random();
				if ( random_drop < default_drop_chance )
	            {
	            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
	            }
				break;
			default:
				break;
		}
	}
	
	public static void addSombrero(Entity entity, Entity source, LivingDropsEvent event)
	{
		double random_drop;
		double default_drop_chance = StatuesConfig.COMMON.oldDropChance.get();
		
		switch (StatuesConfig.COMMON.statueKillSource.get())
		{
			case 1:
				if(source instanceof EntityPlayer && !(source instanceof FakePlayer))
				{
					EntityPlayerMP player = (EntityPlayerMP)source;
					List<String> LuckyPlayers = StatuesConfig.COMMON.lucky_players.get();
					
					if(!LuckyPlayers.isEmpty())
					{
						for (int i = 0; (i < LuckyPlayers.size()) && (!LuckyPlayers.get(i).isEmpty()); i++) {
							if(player.getName().getString().equals(LuckyPlayers.get(i)));
							{
								default_drop_chance = StatuesConfig.COMMON.oldDropChance.get() / 4;
							}
						}
					}
					else
					{
						default_drop_chance = StatuesConfig.COMMON.oldDropChance.get();
					}
//					if(StatuesConfigGen.othersettings.antiAfk)
//					{
//						if(!player.getEntityData().getBoolean(FishHandler.afkKey))
//						{
//							random_drop = Math.random();
//							if (random_drop < default_drop_chance )
//					    	{
//								Biome biome = entity.world.getBiomeForCoordsBody(entity.getPosition());
//								if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
//								{
//									ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
//					            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
//								}
//					    	}
//						}
//					}
//					else
//					{
						random_drop = Math.random();
						if (random_drop < default_drop_chance )
				    	{
							Biome biome = entity.world.getBiomeBody(entity.getPosition());
							if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.DESERT_LAKES || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
							{
								ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
				            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
							}
				    	}
//					}
				}
				break;
			case 2:
				if(source instanceof EntityPlayer)
				{
					random_drop = Math.random();
					if (random_drop < default_drop_chance )
			    	{
						Biome biome = entity.world.getBiomeBody(entity.getPosition());
						if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.DESERT_LAKES || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
						{
							ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
			            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
						}
			    	}
				}
				break;
			case 0:
				random_drop = Math.random();
				if (random_drop < default_drop_chance )
		    	{
					Biome biome = entity.world.getBiomeBody(entity.getPosition());
					if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.DESERT_LAKES || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
					{
						ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
		            	event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
					}
		    	}
				break;
			default:
				break;
		}
	}
}