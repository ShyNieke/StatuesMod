package com.shynieke.statues.handlers;

import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class FishHandler {
    @SubscribeEvent
    public void onItemFished(ItemFishedEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = player.world;
        if(!world.isRemote){
            ItemStack itemStackToDrop = new ItemStack(StatueRegistry.EAGLE_RAY.get());
            dropFishedStatue(itemStackToDrop, player, event);
        }
    }

    public void dropFishedStatue(ItemStack itemStackToDrop, Entity source, ItemFishedEvent event) {
        double random_drop = Math.random();
        double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();

        switch (StatuesConfig.COMMON.statueKillSource.get()) {
            default:
                if (source instanceof ServerPlayerEntity && !(source instanceof FakePlayer)) {
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

                    if (random_drop <= default_drop_chance) {
                        event.getDrops().add(itemStackToDrop);
                    }
                }
                break;
            case PLAYER_FAKEPLAYER:
                if (source instanceof ServerPlayerEntity) {
                    if (random_drop <= default_drop_chance) {
                        event.getDrops().add(itemStackToDrop);
                    }
                }
                break;
            case ALL:
                if (random_drop <= default_drop_chance) {
                    event.getDrops().add(itemStackToDrop);
                }
                break;
        }
    }
}
