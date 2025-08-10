package com.shynieke.statues.handlers;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.blocks.statues.SheepStatueBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.CatVariants;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.MushroomCow.Variant;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class DropHandler {
	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		Entity source = event.getSource().getEntity();
		Level level = entity.level();
		RandomSource rand = level.random;

		if (entity instanceof Villager) {
			switch (rand.nextInt(4)) {
				case 1: {
					dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_WH_STATUE.get()), source, event);
					break;
				}
				case 2: {
					dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_PU_STATUE.get()), source, event);
					break;
				}
				case 3: {
					dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_GR_STATUE.get()), source, event);
					break;
				}
				default: {
					dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_BR_STATUE.get()), source, event);
					break;
				}
			}
		} else if (entity instanceof Evoker) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.EVOKER_STATUE.get());
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Vindicator) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.VINDICATOR_STATUE.get());
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Sheep sheep) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.SHEEP_SHAVEN_STATUE.get());
			if (!sheep.isSheared()) {
				itemStackToDrop = new ItemStack(SheepStatueBlock.getStatue(sheep.getColor()));
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Bee bee) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.BEE_STATUE.get());
			if (rand.nextBoolean()) {
				itemStackToDrop = new ItemStack(StatueRegistry.ANGRY_BEE_STATUE.get());
			}
			String trans = "Trans Bee";
			if (bee.getDisplayName().getString().equalsIgnoreCase(trans)) {
				itemStackToDrop = new ItemStack(StatueRegistry.BEE_STATUE.get());
				itemStackToDrop.set(DataComponents.CUSTOM_NAME, Component.literal(trans));
			}
			String tropi = "Tropibee";
			if (bee.getDisplayName().getString().equalsIgnoreCase(tropi)) {
				itemStackToDrop = new ItemStack(StatueRegistry.TROPIBEE.get());
				itemStackToDrop.set(DataComponents.CUSTOM_NAME, Component.literal(tropi));
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Rabbit rabbit) {
			new ItemStack(StatueRegistry.RABBIT_BR_STATUE.get());
			ItemStack itemStackToDrop = switch (rabbit.getVariant()) {
				case WHITE -> new ItemStack(StatueRegistry.RABBIT_WH_STATUE.get());
				case WHITE_SPLOTCHED -> new ItemStack(StatueRegistry.RABBIT_WS_STATUE.get());
				case BLACK -> new ItemStack(StatueRegistry.RABBIT_BS_STATUE.get());
				case GOLD -> new ItemStack(StatueRegistry.RABBIT_GO_STATUE.get());
				case BROWN -> new ItemStack(StatueRegistry.RABBIT_BW_STATUE.get());
				default -> new ItemStack(StatueRegistry.RABBIT_BR_STATUE.get());
			};
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Panda panda) {
			new ItemStack(StatueRegistry.PANDA_NORMAL_STATUE.get());
			ItemStack itemStackToDrop = switch (panda.getMainGene()) {
				case AGGRESSIVE -> new ItemStack(StatueRegistry.PANDA_ANGRY_STATUE.get());
				case BROWN -> new ItemStack(StatueRegistry.PANDA_BROWN_STATUE.get());
				case LAZY -> new ItemStack(StatueRegistry.PANDA_LAZY_STATUE.get());
				case PLAYFUL -> new ItemStack(StatueRegistry.PANDA_PLAYFUL_STATUE.get());
				case WEAK -> new ItemStack(StatueRegistry.PANDA_WEAK_STATUE.get());
				case WORRIED -> new ItemStack(StatueRegistry.PANDA_WORRIED_STATUE.get());
				default -> new ItemStack(StatueRegistry.PANDA_NORMAL_STATUE.get());
			};
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Cat cat) {
			ItemStack itemStackToDrop = ItemStack.EMPTY;
			ResourceKey<CatVariant> catKey = cat.getVariant().unwrapKey().orElse(null);
			if (catKey == CatVariants.TABBY) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_TABBY_STATUE.get());
			} else if (catKey == CatVariants.BLACK) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_TUXEDO_STATUE.get());
			} else if (catKey == CatVariants.RED) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_RED_STATUE.get());
			} else if (catKey == CatVariants.SIAMESE) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_SIAMESE_STATUE.get());
			} else if (catKey == CatVariants.BRITISH_SHORTHAIR) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get());
			} else if (catKey == CatVariants.CALICO) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_CALICO_STATUE.get());
			} else if (catKey == CatVariants.PERSIAN) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_PERSIAN_STATUE.get());
			} else if (catKey == CatVariants.RAGDOLL) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_RAGDOLL_STATUE.get());
			} else if (catKey == CatVariants.WHITE) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_WHITE_STATUE.get());
			} else if (catKey == CatVariants.JELLIE) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_JELLIE_STATUE.get());
			} else if (catKey == CatVariants.ALL_BLACK) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_BLACK_STATUE.get());
			} else {
				//Fallback to tabby
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_TABBY_STATUE.get());
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof ElderGuardian) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.ELDER_GUARDIAN_STATUE.get());
			dropBossStatue(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Ravager) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.RAVAGER_STATUE.get());
			if (rand.nextFloat() <= 0.25F) {
				dropBossStatue(entity, itemStackToDrop, source, event);
			}
		} else if (entity instanceof MushroomCow mooshroom) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.MOOSHROOM_STATUE.get());
			if (mooshroom.getVariant() == Variant.BROWN) {
				itemStackToDrop = new ItemStack(StatueRegistry.BROWN_MOOSHROOM_STATUE.get());
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Fox fox) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.FOX_STATUE.get());
			if (fox.getVariant() == Fox.Variant.SNOW) {
				itemStackToDrop = new ItemStack(StatueRegistry.FOX_SNOW_STATUE.get());
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Axolotl axolotl) {
			ItemStack itemStackToDrop = switch (axolotl.getVariant()) {
				case WILD -> new ItemStack(StatueRegistry.AXOLOTL_WILD_STATUE.get());
				case GOLD -> new ItemStack(StatueRegistry.AXOLOTL_GOLD_STATUE.get());
				case CYAN -> new ItemStack(StatueRegistry.AXOLOTL_CYAN_STATUE.get());
				case BLUE -> new ItemStack(StatueRegistry.AXOLOTL_BLUE_STATUE.get());
				default -> new ItemStack(StatueRegistry.AXOLOTL_LUCY_STATUE.get());
			};
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Frog frog) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.FROG_TEMPERATE_STATUE.get());
			ResourceKey<FrogVariant> frogVariant = frog.getVariant().unwrapKey().orElseThrow();
			if (frogVariant.equals(FrogVariants.WARM)) {
				itemStackToDrop = new ItemStack(StatueRegistry.FROG_WARM_STATUE.get());
			} else if (frogVariant.equals(FrogVariants.COLD)) {
				itemStackToDrop = new ItemStack(StatueRegistry.FROG_COLD_STATUE.get());
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Warden) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.WARDEN_STATUE.get());
			dropBossStatue(entity, itemStackToDrop, source, event);
		} else {
			List<DeferredHolder<Block, ? extends Block>> matchingStatues = new ArrayList<>();
			for (DeferredHolder<Block, ? extends Block> block : StatueRegistry.BLOCKS.getEntries()) {
				if (block.get() instanceof AbstractStatueBase statue) {
					if (statue.getEntity().equals(entity.getType()) && !statue.isHiddenStatue()) {
						matchingStatues.add(block);
					}
				}
			}
			if (!matchingStatues.isEmpty()) {
				DeferredHolder<Block, ? extends Block> block = matchingStatues.get(rand.nextInt(matchingStatues.size()));
				AbstractStatueBase statue = (AbstractStatueBase) block.get();
				ItemStack itemStackToDrop = new ItemStack(statue);
				if (entity instanceof Mob) {
					if (((Mob) entity).isBaby() == statue.isBaby()) {
						dropLootStatues(entity, itemStackToDrop, source, event);
					}
				} else {
					dropLootStatues(entity, itemStackToDrop, source, event);
				}
			}
		}

		if (StatuesConfig.COMMON.playerDropsStatue.get() && entity instanceof Player player) {
			ItemStack playerStatueStack = new ItemStack(StatueRegistry.PLAYER_STATUE.get());
			playerStatueStack.set(DataComponents.CUSTOM_NAME, Component.literal(player.getName().getString()));
			double random_drop = Math.random();
			double playerDropChance = StatuesConfig.COMMON.playerStatueDropChance.get();
			BlockPos entityPos = entity.blockPosition();

			switch (StatuesConfig.COMMON.playerStatueKillSource.get()) {
				case PLAYER_FAKEPLAYER:
					if (source instanceof ServerPlayer) {
						if (random_drop <= playerDropChance) {
							event.getDrops().add(new ItemEntity(level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
						}
					}
					break;
				case ALL:
					if (random_drop <= playerDropChance) {
						event.getDrops().add(new ItemEntity(level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
					}
					break;
				default:
					if (source instanceof ServerPlayer sourcePlayer && !(source instanceof FakePlayer)) {
						List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
						if (!luckyPlayers.isEmpty()) {
							for (String luckyName : luckyPlayers) {
								String user = sourcePlayer.getName().getString();

								if (!luckyName.isEmpty() && user.equals(luckyName)) {
									playerDropChance = StatuesConfig.COMMON.playerStatueDropChance.get() / 4;
								}
							}
						}

//                    if(StatuesConfig.othersettings.antiAfk)
//                    {
//                        if(!player.getEntityData().getBoolean(FishHandler.afkKey))
//                        {
//                            random_drop = Math.random();
//                            if ( random_drop < default_drop_chance )
//                            {
//                                event.getDrops().add(new EntityItem(entity.level, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
//                            }
//                        }
//                    }
//                    else
//                    {
						if (random_drop <= playerDropChance) {
							event.getDrops().add(new ItemEntity(level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
						}
//                    }
					}
					break;
			}
		}
	}

	public void dropBossStatue(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		BlockPos entityPos = entity.blockPosition();

		switch (StatuesConfig.COMMON.statueKillSource.get()) {
			case PLAYER_FAKEPLAYER:
				if (source instanceof ServerPlayer) {
					event.getDrops().add(new ItemEntity(entity.level(), entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				}
				break;
			case ALL:
				event.getDrops().add(new ItemEntity(entity.level(), entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				break;
			default:
				if (source instanceof ServerPlayer && !(source instanceof FakePlayer)) {
					event.getDrops().add(new ItemEntity(entity.level(), entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				}
				break;
		}
	}

	public void dropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		double random_drop = Math.random();
		double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();
		BlockPos entityPos = entity.blockPosition();

		switch (StatuesConfig.COMMON.statueKillSource.get()) {
			case PLAYER_FAKEPLAYER:
				if (source instanceof ServerPlayer) {
					if (random_drop <= default_drop_chance) {
						event.getDrops().add(new ItemEntity(entity.level(), entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
					}
				}
				break;
			case ALL:
				if (random_drop <= default_drop_chance) {
					event.getDrops().add(new ItemEntity(entity.level(), entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				}
				break;
			default:
				if (source instanceof ServerPlayer player && !(source instanceof FakePlayer)) {
					List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
					if (!luckyPlayers.isEmpty()) {
						for (String luckyName : luckyPlayers) {
							String user = player.getName().getString();

							if (!luckyName.isEmpty() && user.equals(luckyName)) {
								default_drop_chance = StatuesConfig.COMMON.statueDropChance.get() / 4;
							}
						}
					}

//                    if(StatuesConfig.othersettings.antiAfk)
//                    {
//                        if(!player.getEntityData().getBoolean(FishHandler.afkKey))
//                        {
//                            random_drop = Math.random();
//                            if ( random_drop < default_drop_chance )
//                            {
//                                event.getDrops().add(new EntityItem(entity.level, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
//                            }
//                        }
//                    }
//                    else
//                    {
					if (random_drop <= default_drop_chance) {
						event.getDrops().add(new ItemEntity(entity.level(), entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
					}
//                    }
				}
				break;
		}
	}
}