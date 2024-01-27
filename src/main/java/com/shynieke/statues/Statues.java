package com.shynieke.statues;

import com.mojang.logging.LogUtils;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.handlers.DropHandler;
import com.shynieke.statues.handlers.FishHandler;
import com.shynieke.statues.handlers.SpecialHandler;
import com.shynieke.statues.handlers.StatueHandler;
import com.shynieke.statues.handlers.TraderHandler;
import com.shynieke.statues.network.StatuesNetworking;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueEntities;
import com.shynieke.statues.registry.StatueLootModifiers;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueSerializers;
import com.shynieke.statues.registry.StatueSounds;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class Statues {
	public static final Logger LOGGER = LogUtils.getLogger();

	public Statues(IEventBus eventBus) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatuesConfig.commonSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, StatuesConfig.clientSpec);
		eventBus.register(StatuesConfig.class);

		NeoForge.EVENT_BUS.addListener(this::serverAboutToStart);

		StatueSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);
		StatueRegistry.ENTITIES.register(eventBus);
		StatueRegistry.BLOCKS.register(eventBus);
		StatueRegistry.ITEMS.register(eventBus);
		StatueRegistry.CREATIVE_MODE_TABS.register(eventBus);
		StatueRegistry.MENU_TYPES.register(eventBus);
		StatueBlockEntities.BLOCK_ENTITIES.register(eventBus);
		StatueSounds.SOUND_EVENTS.register(eventBus);
		StatuesRecipes.RECIPE_TYPES.register(eventBus);
		StatuesRecipes.RECIPE_SERIALIZERS.register(eventBus);
		StatueLootModifiers.GLM.register(eventBus);

		eventBus.addListener(StatueEntities::registerEntityAttributes);
		eventBus.addListener(StatueEntities::registerSpawnPlacements);
		eventBus.addListener(StatueBlockEntities::registerCapabilities);
		eventBus.addListener(StatuesNetworking::setupPackets);

		NeoForge.EVENT_BUS.register(new StatueHandler());
		NeoForge.EVENT_BUS.register(new FishHandler());
		NeoForge.EVENT_BUS.register(new TraderHandler());
		NeoForge.EVENT_BUS.register(new DropHandler());
		NeoForge.EVENT_BUS.register(new SpecialHandler()); //Used for the Etho Statue

		if (FMLEnvironment.dist == Dist.CLIENT) {
			eventBus.addListener(ClientHandler::doClientStuff);
			eventBus.addListener(ClientHandler::onRegisterMenu);
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			eventBus.addListener(ClientHandler::registerBlockColors);
			NeoForge.EVENT_BUS.addListener(ClientHandler::onLogin);
			NeoForge.EVENT_BUS.addListener(ClientHandler::onRespawn);
		}

	}

	public void serverAboutToStart(final ServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		PlayerBlockEntity.setup(server.getProfileCache(), server.getSessionService(), server);
		GameProfileCache.setUsesAuthentication(server.usesAuthentication());
	}
}
