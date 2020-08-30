package com.shynieke.statues.handlers;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.blocks.statues.SheepStatueBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropHandler {
    private static final Random rand = new Random();

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        Entity source = event.getSource().getTrueSource();

        addSombrero(entity, source, event);

        if (entity instanceof VillagerEntity) {
            switch(entity.world.rand.nextInt(4)) {
                default:
                    DropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_BR_STATUE.get()), source, event);
                    break;
                case 1:
                    DropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_WH_STATUE.get()), source, event);
                    break;
                case 2:
                    DropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_PU_STATUE.get()), source, event);
                    break;
                case 3:
                    DropLootStatues(entity, new ItemStack(StatueRegistry.VILLAGER_GR_STATUE.get()), source, event);
                    break;
            }
        } else if(entity instanceof EvokerEntity || entity instanceof VindicatorEntity || entity instanceof VexEntity)
        {
            if(entity instanceof EvokerEntity)
            {
                ItemStack itemStackToDrop = new ItemStack(StatueRegistry.EVOKER_STATUE.get());
                DropLootStatues(entity, itemStackToDrop, source, event);
            }

            ItemStack itemStackToDrop2 = new ItemStack(StatueRegistry.TOTEM_OF_UNDYING_STATUE.get());
            DropLootStatues(entity, itemStackToDrop2, source, event);
        } else if(entity instanceof SheepEntity) {
            SheepEntity sheep = (SheepEntity) entity;
            ItemStack itemStackToDrop = new ItemStack(StatueRegistry.SHEEP_SHAVEN_STATUE.get());
            if (!sheep.getSheared()) {
                itemStackToDrop = new ItemStack(SheepStatueBlock.getStatue(sheep.getFleeceColor()));
            }
            DropLootStatues(entity, itemStackToDrop, source, event);
        } else if(entity instanceof BeeEntity) {
            BeeEntity bee = (BeeEntity)entity;
            ItemStack itemStackToDrop = new ItemStack(StatueRegistry.BEE_STATUE.get());
            if (entity.world.rand.nextBoolean()) {
                itemStackToDrop = new ItemStack(StatueRegistry.ANGRY_BEE_STATUE.get());
            }
            String trans = "Trans Bee";
            if(bee.getDisplayName().getUnformattedComponentText().equalsIgnoreCase(trans)) {
                itemStackToDrop = new ItemStack(StatueRegistry.BEE_STATUE.get());
                itemStackToDrop.setDisplayName(new StringTextComponent(trans));
            }
            DropLootStatues(entity, itemStackToDrop, source, event);
        } else {
            List<RegistryObject<Block>> matchingStatues = new ArrayList<>();
            for (RegistryObject<Block> block : StatueRegistry.BLOCKS.getEntries()) {
                if (block.get() instanceof AbstractStatueBase) {
                    AbstractStatueBase statue = (AbstractStatueBase) block.get();
                    if (statue.getEntity().equals(entity.getType()) && !statue.isHiddenStatue()) {
                        matchingStatues.add(block);
                    }
                }
            }
            if(!matchingStatues.isEmpty()) {
                RegistryObject block = matchingStatues.get(rand.nextInt(matchingStatues.size()));
                AbstractStatueBase statue = (AbstractStatueBase) block.get();
                ItemStack itemStackToDrop = new ItemStack(statue);
                if (entity instanceof MobEntity) {
                    if (((MobEntity) entity).isChild() == statue.isBaby()) {
                        DropLootStatues(entity, itemStackToDrop, source, event);
                    }
                } else {
                    DropLootStatues(entity, itemStackToDrop, source, event);
                }
            }
        }

        if(StatuesConfig.COMMON.playerDropsStatue.get() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            ItemStack playerStatueStack = new ItemStack(StatueRegistry.PLAYER_STATUE.get()).setDisplayName(player.getName());
            double random_drop = Math.random();
            double playerDropChance = StatuesConfig.COMMON.playerStatueDropChance.get();
            BlockPos entityPos = entity.getPosition();
            switch (StatuesConfig.COMMON.playerStatueKillSource.get())
            {
                default:
                    if (source instanceof PlayerEntity && !(source instanceof FakePlayer)) {
                        ServerPlayerEntity sourcePlayer = (ServerPlayerEntity) source;
                        List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
                        if (!luckyPlayers.isEmpty()) {
                            for (String luckyName : luckyPlayers) {
                                String user = sourcePlayer.getName().getUnformattedComponentText();

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
                        if (random_drop < playerDropChance) {
                            event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
                        }
//                    }
                    }
                    break;
                case PLAYER_FAKEPLAYER:
                    if (source instanceof PlayerEntity) {
                        if (random_drop < playerDropChance) {
                            event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
                        }
                    }
                    break;
                case ALL:
                    if (random_drop < playerDropChance) {
                        event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), playerStatueStack));
                    }
                    break;
            }
        }
    }

    public void DropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
        double random_drop = Math.random();
        double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();
        BlockPos entityPos = entity.getPosition();

        switch (StatuesConfig.COMMON.statueKillSource.get()) {
            default:
                if (source instanceof PlayerEntity && !(source instanceof FakePlayer)) {
                    ServerPlayerEntity player = (ServerPlayerEntity) source;
                    List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
                    if (!luckyPlayers.isEmpty()) {
                        for (String luckyName : luckyPlayers) {
                            String user = player.getName().getUnformattedComponentText();

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
                    if (random_drop < default_drop_chance) {
                        event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
                    }
//                    }
                }
                break;
            case PLAYER_FAKEPLAYER:
                if (source instanceof PlayerEntity) {
                    if (random_drop < default_drop_chance) {
                        event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
                    }
                }
                break;
            case ALL:
                if (random_drop < default_drop_chance) {
                    event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), itemStackToDrop));
                }
                break;
        }
    }

    public static void addSombrero(Entity entity, Entity source, LivingDropsEvent event)
    {
        double random_drop;
        double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();
        BlockPos entityPos = entity.getPosition();
        Biome biome = entity.world.getBiome(entity.getPosition());

        switch (StatuesConfig.COMMON.statueKillSource.get())
        {
            default:
                if(source instanceof PlayerEntity && !(source instanceof FakePlayer))
                {
                    ServerPlayerEntity player = (ServerPlayerEntity)source;
                    List<? extends String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
                    if (!luckyPlayers.isEmpty()) {
                        for (String luckyName : luckyPlayers) {
                            String user = player.getName().getUnformattedComponentText();

                            if (!luckyName.isEmpty() && user.equals(luckyName)) {
                                default_drop_chance = StatuesConfig.COMMON.statueDropChance.get() / 4;
                            }
                        }
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
                    if (random_drop < default_drop_chance)
                    {
                        if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
                        {
                            ItemStack sombreroStack = new ItemStack(StatueRegistry.SOMBRERO.get());
                            event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), sombreroStack));
                        }
                    }
//					}
                }
                break;
            case PLAYER_FAKEPLAYER:
                if(source instanceof PlayerEntity)
                {
                    random_drop = Math.random();
                    if (random_drop < default_drop_chance)
                    {
                        if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
                        {
                            ItemStack sombreroStack = new ItemStack(StatueRegistry.SOMBRERO.get());
                            event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), sombreroStack));
                        }
                    }
                }
                break;
            case ALL:
                random_drop = Math.random();
                if (random_drop < default_drop_chance)
                {
                    if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
                    {
                        ItemStack sombreroStack = new ItemStack(StatueRegistry.SOMBRERO.get());
                        event.getDrops().add(new ItemEntity(entity.world, entityPos.getX(), entityPos.getY(), entityPos.getZ(), sombreroStack));
                    }
                }
                break;
        }
    }
}