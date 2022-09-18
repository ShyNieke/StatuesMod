package com.shynieke.statues;

import com.mojang.logging.LogUtils;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.handlers.DropHandler;
import com.shynieke.statues.handlers.FishHandler;
import com.shynieke.statues.handlers.SpecialHandler;
import com.shynieke.statues.handlers.TraderHandler;
import com.shynieke.statues.init.StatueBiomeModifierSerializers;
import com.shynieke.statues.init.StatueBlockEntities;
import com.shynieke.statues.init.StatueEntities;
import com.shynieke.statues.init.StatueLootModifiers;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueSerializers;
import com.shynieke.statues.init.StatueSounds;
import com.shynieke.statues.packets.StatuesNetworking;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class Statues {
	public static final Logger LOGGER = LogUtils.getLogger();

	public Statues() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatuesConfig.commonSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, StatuesConfig.clientSpec);
		eventBus.register(StatuesConfig.class);

		eventBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

		StatueSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);
		StatueRegistry.ENTITIES.register(eventBus);
		StatueRegistry.BLOCKS.register(eventBus);
		StatueRegistry.ITEMS.register(eventBus);
		StatueBlockEntities.BLOCK_ENTITIES.register(eventBus);
		StatueSounds.SOUND_EVENTS.register(eventBus);
		StatuesRecipes.RECIPE_SERIALIZERS.register(eventBus);
		StatueBiomeModifierSerializers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
		StatueBiomeModifierSerializers.BIOME_MODIFIERS.register(eventBus);
		StatueLootModifiers.GLM.register(eventBus);

		eventBus.addListener(StatueEntities::registerEntityAttributes);

		if (ModList.get().isLoaded("curios")) {
			eventBus.addListener(com.shynieke.statues.compat.curios.CuriosCompat::sendImc);
		}

//		MinecraftForge.EVENT_BUS.register(new InventoryHandler());
		MinecraftForge.EVENT_BUS.register(new FishHandler());
		MinecraftForge.EVENT_BUS.register(new TraderHandler());
		MinecraftForge.EVENT_BUS.register(new DropHandler());
		MinecraftForge.EVENT_BUS.register(new SpecialHandler()); //Used for the Etho Statue

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::doClientStuff);
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			eventBus.addListener(ClientHandler::registerBlockColors);
			eventBus.addListener(ClientHandler::preStitchEvent);
			MinecraftForge.EVENT_BUS.addListener(ClientHandler::onLogin);
			MinecraftForge.EVENT_BUS.addListener(ClientHandler::onRespawn);
		});

	}

	private void setup(final FMLCommonSetupEvent event) {
		StatueLootList.initializeStatueLoot();
		StatueEntities.setupEntities();

		StatuesNetworking.init();
	}

	public void serverAboutToStart(final ServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		PlayerBlockEntity.setup(server.getProfileCache(), server.getSessionService(), server);
		GameProfileCache.setUsesAuthentication(server.usesAuthentication());
	}
}
