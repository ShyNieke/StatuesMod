package com.shynieke.statues.datagen.server;

import com.shynieke.statues.handlers.TraderHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrades;

import java.util.concurrent.CompletableFuture;

public class StatueVillagerTradesTagProvider extends VillagerTradesTagsProvider {
	public StatueVillagerTradesTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.tag(VillagerTradeTags.WANDERING_TRADER_UNCOMMON)
				.add(
						TraderHandler.INFO_STATUE,
						TraderHandler.SOMBRERO,
						TraderHandler.DETECTIVE_PLATYPUS,
						TraderHandler.SLABFISH,
						TraderHandler.TOTEM_OF_UNDYING_STATUE
				);
	}
}
