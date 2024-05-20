package com.shynieke.statues.handlers;

import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

import java.util.List;

public class FishHandler {
	@SubscribeEvent
	public void onItemFished(ItemFishedEvent event) {
		Player player = event.getEntity();
		Level level = player.level();
		if (!level.isClientSide) {
			ItemStack itemStackToDrop = new ItemStack(StatueRegistry.EAGLE_RAY.get());
			dropFishedStatue(itemStackToDrop, player, event);
		}
	}

	public void dropFishedStatue(ItemStack itemStackToDrop, Entity source, ItemFishedEvent event) {
		double random_drop = Math.random();
		double default_drop_chance = StatuesConfig.COMMON.statueDropChance.get();

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

					if (random_drop <= default_drop_chance) {
						event.getDrops().add(itemStackToDrop);
					}
				}
				break;
			case PLAYER_FAKEPLAYER:
				if (source instanceof ServerPlayer) {
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
