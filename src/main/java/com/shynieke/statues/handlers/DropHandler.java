package com.shynieke.statues.handlers;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.blocks.statues.SheepStatueBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Fox.Type;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropHandler {
	private static final Random rand = new Random();

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		Entity source = event.getSource().getEntity();

		if (entity instanceof Villager) {
			switch (entity.level.random.nextInt(4)) {
				default ->
						dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_BR_STATUE.get()), source, event);
				case 1 ->
						dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_WH_STATUE.get()), source, event);
				case 2 ->
						dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_PU_STATUE.get()), source, event);
				case 3 ->
						dropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_GR_STATUE.get()), source, event);
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
			if (entity.level.random.nextBoolean()) {
				itemStackToDrop = new ItemStack(StatueRegistry.ANGRY_BEE_STATUE.get());
			}
			String trans = "Trans Bee";
			if (bee.getDisplayName().getString().equalsIgnoreCase(trans)) {
				itemStackToDrop = new ItemStack(StatueRegistry.BEE_STATUE.get());
				itemStackToDrop.setHoverName(Component.literal(trans));
			}
			String tropi = "Tropibee";
			if (bee.getDisplayName().getString().equalsIgnoreCase(trans)) {
				itemStackToDrop = new ItemStack(StatueRegistry.BEE_STATUE.get());
				itemStackToDrop.setHoverName(Component.literal(tropi));
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Rabbit rabbit) {
			new ItemStack(StatueRegistry.RABBIT_BR_STATUE.get());
			ItemStack itemStackToDrop = switch (rabbit.getRabbitType()) {
				case 1 -> new ItemStack(StatueRegistry.RABBIT_WH_STATUE.get());
				case 2 -> new ItemStack(StatueRegistry.RABBIT_WS_STATUE.get());
				case 3 -> new ItemStack(StatueRegistry.RABBIT_BS_STATUE.get());
				case 4 -> new ItemStack(StatueRegistry.RABBIT_GO_STATUE.get());
				case 5 -> new ItemStack(StatueRegistry.RABBIT_BW_STATUE.get());
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
			CatVariant variant = cat.getCatVariant();
			if (variant == CatVariant.TABBY) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_TABBY_STATUE.get());
			} else if (variant == CatVariant.BLACK) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_TUXEDO_STATUE.get());
			} else if (variant == CatVariant.RED) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_RED_STATUE.get());
			} else if (variant == CatVariant.SIAMESE) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_SIAMESE_STATUE.get());
			} else if (variant == CatVariant.BRITISH_SHORTHAIR) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get());
			} else if (variant == CatVariant.CALICO) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_CALICO_STATUE.get());
			} else if (variant == CatVariant.PERSIAN) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_PERSIAN_STATUE.get());
			} else if (variant == CatVariant.RAGDOLL) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_RAGDOLL_STATUE.get());
			} else if (variant == CatVariant.WHITE) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_WHITE_STATUE.get());
			} else if (variant == CatVariant.JELLIE) {
				itemStackToDrop = new ItemStack(StatueRegistry.CAT_JELLIE_STATUE.get());
			} else if (variant == CatVariant.ALL_BLACK) {
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
			if (mooshroom.getMushroomType() == MushroomCow.MushroomType.BROWN) {
				itemStackToDrop = new ItemStack(StatueRegistry.BROWN_MOOSHROOM_STATUE.get());
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else if (entity instanceof Fox fox) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.FOX_STATUE.get());
			if (fox.getFoxType() == Type.SNOW) {
				itemStackToDrop = new ItemStack(StatueRegistry.FOX_SNOW_STATUE.get());
			}
			dropLootStatues(entity, itemStackToDrop, source, event);
		} else {
			List<RegistryObject<Block>> matchingStatues = new ArrayList<>();
			for (RegistryObject<Block> block : StatueRegistry.BLOCKS.getEntries()) {
				if (block.get() instanceof AbstractStatueBase statue) {
					if (statue.getEntity().equals(entity.getType()) && !statue.isHiddenStatue()) {
						matchingStatues.add(block);
					}
				}
			}
			if (!matchingStatues.isEmpty()) {
				RegistryObject<Block> block = matchingStatues.get(rand.nextInt(matchingStatues.size()));
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
			ItemStack playerStatueStack = new ItemStack(StatueRegistry.PLAYER_STATUE.get()).setHoverName(player.getName());
			double random_drop = Math.random();
			double playerDropChance = StatuesConfig.COMMON.playerStatueDropChance.get();
			BlockPos entityPos = entity.blockPosition();

			switch (StatuesConfig.COMMON.playerStatueKillSource.get()) {
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
//                                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
//                            }
//                        }
//                    }
//                    else
//                    {
						if (random_drop <= playerDropChance) {
							event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
						}
//                    }
					}
					break;
				case PLAYER_FAKEPLAYER:
					if (source instanceof ServerPlayer) {
						if (random_drop <= playerDropChance) {
							event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
						}
					}
					break;
				case ALL:
					if (random_drop <= playerDropChance) {
						event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
					}
					break;
			}
		}
	}

	public void dropBossStatue(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		BlockPos entityPos = entity.blockPosition();

		switch (StatuesConfig.COMMON.statueKillSource.get()) {
			default:
				if (source instanceof ServerPlayer && !(source instanceof FakePlayer)) {
					event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				}
				break;
			case PLAYER_FAKEPLAYER:
				if (source instanceof ServerPlayer) {
					event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				}
				break;
			case ALL:
				event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				break;
		}
	}

	public void dropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
		double random_drop = Math.random();
		double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();
		BlockPos entityPos = entity.blockPosition();

		switch (StatuesConfig.COMMON.statueKillSource.get()) {
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
//                                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
//                            }
//                        }
//                    }
//                    else
//                    {
					if (random_drop <= default_drop_chance) {
						event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
					}
//                    }
				}
				break;
			case PLAYER_FAKEPLAYER:
				if (source instanceof ServerPlayer) {
					if (random_drop <= default_drop_chance) {
						event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
					}
				}
				break;
			case ALL:
				if (random_drop <= default_drop_chance) {
					event.getDrops().add(new ItemEntity(entity.level, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
				}
				break;
		}
	}
}