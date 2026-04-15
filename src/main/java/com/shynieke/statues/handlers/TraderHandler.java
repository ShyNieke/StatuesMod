package com.shynieke.statues.handlers;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;
import java.util.Optional;

public class TraderHandler {
	public static final ResourceKey<VillagerTrade> INFO_STATUE = resourceKey("wandering_trader/info_statue");
	public static final ResourceKey<VillagerTrade> SOMBRERO = resourceKey("wandering_trader/sombrero");
	public static final ResourceKey<VillagerTrade> DETECTIVE_PLATYPUS = resourceKey("wandering_trader/detective_platypus");
	public static final ResourceKey<VillagerTrade> SLABFISH = resourceKey("wandering_trader/slabfish");
	public static final ResourceKey<VillagerTrade> TOTEM_OF_UNDYING_STATUE = resourceKey("wandering_trader/totem_of_undying_statue");

	public static ResourceKey<VillagerTrade> resourceKey(String path) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, Reference.modLoc(path));
	}

	public static void bootstrap(BootstrapContext<VillagerTrade> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);

		context.register(INFO_STATUE, new VillagerTrade(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(StatueRegistry.INFO_STATUE.asItem()), 32, 1, 0.05F, Optional.empty(), List.of()));
		context.register(SOMBRERO, new VillagerTrade(new TradeCost(Items.EMERALD, 10), new ItemStackTemplate(StatueRegistry.SOMBRERO.asItem()), 1, 1, 0.05F, Optional.empty(), List.of()));
		context.register(DETECTIVE_PLATYPUS, new VillagerTrade(new TradeCost(Items.EMERALD, 20), new ItemStackTemplate(StatueRegistry.DETECTIVE_PLATYPUS.asItem()), 1, 1, 0.05F, Optional.empty(), List.of()));
		context.register(SLABFISH, new VillagerTrade(new TradeCost(Items.EMERALD, 15), new ItemStackTemplate(StatueRegistry.SLABFISH.asItem()), 1, 1, 0.05F, Optional.empty(), List.of()));
		context.register(TOTEM_OF_UNDYING_STATUE, new VillagerTrade(new TradeCost(Items.EMERALD, 32), new ItemStackTemplate(StatueRegistry.TOTEM_OF_UNDYING_STATUE.asItem()), 1, 1, 0.05F, Optional.empty(), List.of()));
	}
}
