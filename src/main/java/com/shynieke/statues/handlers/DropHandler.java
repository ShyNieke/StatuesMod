package com.shynieke.statues.handlers;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.blocks.statues.SheepStatueBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class DropHandler {

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        Entity source = event.getSource().getTrueSource();

        addSombrero(entity, source, event);

        if (entity instanceof VillagerEntity) {
            switch(entity.world.rand.nextInt(4)) {
                default:
                    DropLootStatues(entity, new ItemStack(StatueBlocks.villager_br_statue), source, event);
                    break;
                case 1:
                    DropLootStatues(entity, new ItemStack(StatueBlocks.villager_wh_statue), source, event);
                    break;
                case 2:
                    DropLootStatues(entity, new ItemStack(StatueBlocks.villager_pu_statue), source, event);
                    break;
                case 3:
                    DropLootStatues(entity, new ItemStack(StatueBlocks.villager_gr_statue), source, event);
                    break;
            }
        } else if(entity instanceof EvokerEntity || entity instanceof VindicatorEntity || entity instanceof VexEntity)
        {
            if(entity instanceof EvokerEntity)
            {
                ItemStack itemStackToDrop = new ItemStack(StatueBlocks.evoker_statue);
                DropLootStatues(entity, itemStackToDrop, source, event);
            }

            ItemStack itemStackToDrop2 = new ItemStack(StatueBlocks.totem_of_undying_statue);
            DropLootStatues(entity, itemStackToDrop2, source, event);
        } else if(entity instanceof SheepEntity) {
            SheepEntity sheep = (SheepEntity) entity;
            ItemStack itemStackToDrop = new ItemStack(StatueBlocks.sheep_shaven_statue);
            if(sheep.getSheared()) {
                DropLootStatues(entity, itemStackToDrop, source, event);
            } else {
                itemStackToDrop = new ItemStack(SheepStatueBlock.getStatue(sheep.getFleeceColor()));
                DropLootStatues(entity, itemStackToDrop, source, event);
            }
        } else {
            for (Block block : StatueBlocks.BLOCKS) {
                if (block instanceof AbstractStatueBase) {
                    AbstractStatueBase statue = (AbstractStatueBase) block;
                    if (statue.getEntity().equals(entity.getType()) && !statue.isHiddenStatue()) {
                        ItemStack itemStackToDrop = new ItemStack(block);
                        if (entity instanceof MobEntity) {
                            if (((MobEntity) entity).isChild() == statue.isBaby()) {
                                DropLootStatues(entity, itemStackToDrop, source, event);
                            }
                        } else {
                            DropLootStatues(entity, itemStackToDrop, source, event);
                        }
                    }
                }
            }
        }

        if(StatuesConfig.COMMON.playerDropsStatue.get() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            ItemStack playerStatueStack = new ItemStack(StatueBlocks.player_statue).setDisplayName(player.getName());
            double random_drop = Math.random();
            double playerDropChance = StatuesConfig.COMMON.playerStatueDropChance.get();
            switch (StatuesConfig.COMMON.playerStatueKillSource.get())
            {
                default:
                    if (source instanceof PlayerEntity && !(source instanceof FakePlayer)) {
                        ServerPlayerEntity sourcePlayer = (ServerPlayerEntity) source;
                        List<String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
                        if (!luckyPlayers.isEmpty()) {
                            for (int i = 0; i < luckyPlayers.size(); i++) {
                                String luckyName = luckyPlayers.get(i);
                                String user = sourcePlayer.getName().getFormattedText();

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
                            event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, playerStatueStack));
                        }
//                    }
                    }
                    break;
                case PLAYER_FAKEPLAYER:
                    if (source instanceof PlayerEntity) {
                        if (random_drop < playerDropChance) {
                            event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, playerStatueStack));
                        }
                    }
                    break;
                case ALL:
                    if (random_drop < playerDropChance) {
                        event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, playerStatueStack));
                    }
                    break;
            }
        }
    }

    public void DropLootStatues(Entity entity, ItemStack itemStackToDrop, Entity source, LivingDropsEvent event) {
        double random_drop = Math.random();
        double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();

        switch (StatuesConfig.COMMON.statueKillSource.get()) {
            default:
                if (source instanceof PlayerEntity && !(source instanceof FakePlayer)) {
                    ServerPlayerEntity player = (ServerPlayerEntity) source;
                    List<String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
                    if (!luckyPlayers.isEmpty()) {
                        for (int i = 0; i < luckyPlayers.size(); i++) {
                            String luckyName = luckyPlayers.get(i);
                            String user = player.getName().getFormattedText();

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
                        event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
                    }
//                    }
                }
                break;
            case PLAYER_FAKEPLAYER:
                if (source instanceof PlayerEntity) {
                    if (random_drop < default_drop_chance) {
                        event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
                    }
                }
                break;
            case ALL:
                if (random_drop < default_drop_chance) {
                    event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, itemStackToDrop));
                }
                break;
        }
    }

    public static void addSombrero(Entity entity, Entity source, LivingDropsEvent event)
    {
        double random_drop;
        double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();

        switch (StatuesConfig.COMMON.statueKillSource.get())
        {
            default:
                if(source instanceof PlayerEntity && !(source instanceof FakePlayer))
                {
                    ServerPlayerEntity player = (ServerPlayerEntity)source;
                    List<String> luckyPlayers = StatuesConfig.COMMON.lucky_players.get();
                    if (!luckyPlayers.isEmpty()) {
                        for (int i = 0; i < luckyPlayers.size(); i++) {
                            String luckyName = luckyPlayers.get(i);
                            String user = player.getName().getFormattedText();

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
                        Biome biome = entity.world.getBiomeBody(entity.getPosition());
                        if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
                        {
                            ItemStack sombreroStack = new ItemStack(StatueBlocks.sombrero);
                            event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
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
                        Biome biome = entity.world.getBiomeBody(entity.getPosition());

                        if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
                        {
                            ItemStack sombreroStack = new ItemStack(StatueBlocks.sombrero);
                            event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
                        }
                    }
                }
                break;
            case ALL:
                random_drop = Math.random();
                if (random_drop < default_drop_chance)
                {
                    Biome biome = entity.world.getBiomeBody(entity.getPosition());
                    if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
                    {
                        ItemStack sombreroStack = new ItemStack(StatueBlocks.sombrero);
                        event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, sombreroStack));
                    }
                }
                break;
        }
    }
}