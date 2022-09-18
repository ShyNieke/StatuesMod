package com.shynieke.statues.handler;

import com.shynieke.statues.config.StatuesConfigGen;
import com.shynieke.statues.entity.fakeentity.IFakeEntity;
import com.shynieke.statues.init.StatuesBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class DropHandler {

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		Entity source = event.getSource().getTrueSource();

		if (StatuesConfigGen.general.Tier1Crafting == false) {
			addSombrero(entity, source, event);

			if (entity instanceof EntitySlime) {
				if (entity instanceof EntityMagmaCube) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.magma_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.slime_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
			}

			if (entity instanceof EntityBlaze) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.blaze_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntitySnowman) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.snowgolem_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityCow) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.cow_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityMooshroom) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.mooshroom_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityChicken) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.chicken_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityCreeper) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.creeper_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityPig) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pig_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityRabbit) {
				EntityRabbit rabbit = (EntityRabbit) entity;

				if (rabbit.getRabbitType() == 0) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_br_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else if (rabbit.getRabbitType() == 1) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_wh_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else if (rabbit.getRabbitType() == 2) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_ws_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else if (rabbit.getRabbitType() == 3) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_bs_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else if (rabbit.getRabbitType() == 4) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_go_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else if (rabbit.getRabbitType() == 5) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.rabbit_bw_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
			}

			if (entity instanceof EntitySheep) {
				if (((EntitySheep) entity).getSheared() == true) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheepshaven_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.WHITE) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_white[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.ORANGE) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_orange[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.MAGENTA) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_magenta[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIGHT_BLUE) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightblue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.YELLOW) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_yellow[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.LIME) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lime[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PINK) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_pink[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GRAY) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_gray[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.SILVER) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_lightgray[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.CYAN) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_cyan[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.PURPLE) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_purple[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLUE) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_blue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BROWN) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_brown[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.GREEN) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_green[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.RED) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_red[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (((EntitySheep) entity).getFleeceColor() == EnumDyeColor.BLACK) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.sheep_statue_black[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
			}

			if (entity instanceof EntityZombie) {
				if (entity instanceof EntityHusk) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.husk_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else if (((EntityZombie) entity).isChild()) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.baby_zombie_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				} else {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.zombie_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
			}

			if (entity instanceof EntitySquid) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.squid_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityVillager) {
				EntityVillager villager = (EntityVillager) entity;
				if (villager.getProfessionForge() == VillagerRegistry.getById(0)) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_br_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (villager.getProfessionForge() == VillagerRegistry.getById(1)) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_wh_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (villager.getProfessionForge() == VillagerRegistry.getById(2)) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_pu_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
				if (villager.getProfessionForge() == VillagerRegistry.getById(5)) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.villager_gr_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}
			}

			if (entity instanceof EntityWitch) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.witch_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityShulker) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.shulker_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityEnderman) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.enderman_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityEndermite) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.endermite_statue, 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityGuardian) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.guardian_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityGhast) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.ghast_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}

			if (entity instanceof EntityEvoker || entity instanceof EntityVindicator || entity instanceof EntityVex) {
				if (entity instanceof EntityEvoker) {
					ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.evoker_statue[0], 1);
					DropLootStatues(entity, itemStackToDrop, source, event);
				}

				ItemStack itemStackToDrop2 = new ItemStack(StatuesBlocks.totemofundying_statue, 1);
				DropLootStatues(entity, itemStackToDrop2, source, event);
			}

			if (entity instanceof EntitySpider) {
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.spider_statue[0], 1);
				DropLootStatues(entity, itemStackToDrop, source, event);
			}
		}

		if (StatuesConfigGen.player.PlayersDropStatue == true) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack playerstatuestack = new ItemStack(StatuesBlocks.player_statue, 1).setStackDisplayName(player.getName());

				switch (StatuesConfigGen.player.PlayerStatueKillSource) {
					case PLAYER:
						if (source instanceof EntityPlayer && !(source instanceof FakePlayer) && entity != source) {
							int chance = StatuesConfigGen.player.PlayerDropChance;
							if (chance != 0) {
								int random = entity.world.rand.nextInt(chance);
								if (random < 1) {
									event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY + 0.5, entity.posZ, playerstatuestack));
								}
							}
						}
						break;
					case PLAYER_FAKEPLAYER:
						if (source instanceof EntityPlayer && entity != source) {
							int chance = StatuesConfigGen.player.PlayerDropChance;
							if (chance != 0) {
								int random = entity.world.rand.nextInt(chance);
								if (random < 1) {
									event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY + 0.5, entity.posZ, playerstatuestack));
								}
							}
						}
						break;
					case ALL:
						int chance = StatuesConfigGen.player.PlayerDropChance;
						if (chance != 0) {
							int random = entity.world.rand.nextInt(chance);
							if (random < 1) {
								event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY + 0.5, entity.posZ, playerstatuestack));
							}
						}
						break;
					default:
						break;
				}
			}
		}

		if (entity instanceof IFakeEntity) {
			event.setCanceled(true);
		}
	}

	public void DropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		double random_drop;
		double default_drop_chance = StatuesConfigGen.general.OldDropChance;

		switch (StatuesConfigGen.general.StatueKillSource) {
			case PLAYER:
				if (source instanceof EntityPlayer && !(source instanceof FakePlayer)) {
					EntityPlayerMP player = (EntityPlayerMP) source;
					String[] LuckyPlayers = StatuesConfigGen.luckyplayers.lucky_players;
					if (LuckyPlayers.length > 0) {
						for (int i = 0; i < LuckyPlayers.length; i++) {
							String luckyName = LuckyPlayers[i];
							String user = player.getName();

							if (!luckyName.isEmpty() && user.equals(luckyName) == true) {
								default_drop_chance = StatuesConfigGen.general.OldDropChance / 4;
							} else {
								default_drop_chance = StatuesConfigGen.general.OldDropChance;
							}
						}
					} else {
						default_drop_chance = StatuesConfigGen.general.OldDropChance;
					}

					if (StatuesConfigGen.othersettings.antiAfk) {
						if (!player.getEntityData().getBoolean(FishHandler.afkKey)) {
							random_drop = Math.random();
							if (random_drop < default_drop_chance) {
								event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
							}
						}
					} else {
						random_drop = Math.random();
						if (random_drop < default_drop_chance) {
							event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
						}
					}
				}
				break;
			case PLAYER_FAKEPLAYER:
				if (source instanceof EntityPlayer) {
					random_drop = Math.random();
					if (random_drop < default_drop_chance) {
						event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
					}
				}
				break;
			case ALL:
				random_drop = Math.random();
				if (random_drop < default_drop_chance) {
					event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
				}
				break;
			default:
				break;
		}
	}

	public void addSombrero(Entity entity, Entity source, LivingDropsEvent event) {
		double random_drop;
		double default_drop_chance = StatuesConfigGen.general.OldDropChance;

		switch (StatuesConfigGen.general.StatueKillSource) {
			case PLAYER:
				if (source instanceof EntityPlayer && !(source instanceof FakePlayer)) {
					EntityPlayerMP player = (EntityPlayerMP) source;
					String[] LuckyPlayers = StatuesConfigGen.luckyplayers.lucky_players;

					if (LuckyPlayers.length != 0) {
						for (int i = 0; (i < LuckyPlayers.length) && (LuckyPlayers[i] != null); i++) {
							if (player.getName().equals(LuckyPlayers[i])) ;
							{
								default_drop_chance = StatuesConfigGen.general.OldDropChance / 4;
							}
						}
					} else {
						default_drop_chance = StatuesConfigGen.general.OldDropChance;
					}
					if (StatuesConfigGen.othersettings.antiAfk) {
						if (!player.getEntityData().getBoolean(FishHandler.afkKey)) {
							random_drop = Math.random();
							if (random_drop < default_drop_chance) {
								Biome biome = entity.world.getBiomeForCoordsBody(entity.getPosition());
								if (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
									ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
									event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
								}
							}
						}
					} else {
						random_drop = Math.random();
						if (random_drop < default_drop_chance) {
							Biome biome = entity.world.getBiomeForCoordsBody(entity.getPosition());
							if (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
								ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
								event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
							}
						}
					}
				}
				break;
			case PLAYER_FAKEPLAYER:
				if (source instanceof EntityPlayer) {
					random_drop = Math.random();
					if (random_drop < default_drop_chance) {
						Biome biome = entity.world.getBiomeForCoordsBody(entity.getPosition());
						if (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
							ItemStack sombreroStack = new ItemStack(StatuesBlocks.sombrero);
							event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
						}
					}
				}
				break;
			case ALL:
				random_drop = Math.random();
				if (random_drop < default_drop_chance) {
					Biome biome = entity.world.getBiomeForCoordsBody(entity.getPosition());
					if (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
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