package com.shynieke.statues;

import com.mojang.logging.LogUtils;
import com.shynieke.statues.commands.StatuesCommands;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.handlers.DropHandler;
import com.shynieke.statues.handlers.FishHandler;
import com.shynieke.statues.handlers.SpecialHandler;
import com.shynieke.statues.handlers.StatueHandler;
import com.shynieke.statues.network.StatuesNetworking;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueDataComponents;
import com.shynieke.statues.registry.StatueLootModifiers;
import com.shynieke.statues.registry.StatuePatterns;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueSerializers;
import com.shynieke.statues.registry.StatueSounds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class Statues {
	public static final Logger LOGGER = LogUtils.getLogger();

	public Statues(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, StatuesConfig.commonSpec);
		eventBus.register(StatuesConfig.class);

		eventBus.addListener(this::commonSetup);
		NeoForge.EVENT_BUS.addListener(this::onCommandRegister);

		StatueSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);
		StatueRegistry.ENTITIES.register(eventBus);
		StatueRegistry.BLOCKS.register(eventBus);
		StatueDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
		StatueRegistry.ITEMS.register(eventBus);
		StatueRegistry.CREATIVE_MODE_TABS.register(eventBus);
		StatueRegistry.MENU_TYPES.register(eventBus);
		StatueBlockEntities.BLOCK_ENTITIES.register(eventBus);
		StatueSounds.SOUND_EVENTS.register(eventBus);
		StatuesRecipes.RECIPE_TYPES.register(eventBus);
		StatuesRecipes.RECIPE_SERIALIZERS.register(eventBus);
		StatueLootModifiers.GLM.register(eventBus);
		StatuePatterns.POT_PATTERNS.register(eventBus);

		eventBus.addListener(StatueBlockEntities::registerCapabilities);
		eventBus.addListener(StatuesNetworking::setupPackets);

		NeoForge.EVENT_BUS.register(new StatueHandler());
		NeoForge.EVENT_BUS.register(new FishHandler());
		NeoForge.EVENT_BUS.register(new DropHandler());
		NeoForge.EVENT_BUS.register(new SpecialHandler()); //Used for the Etho Statue

		NeoForge.EVENT_BUS.addListener(this::onDatapackSync);

		if (dist.isClient()) {
			container.registerConfig(ModConfig.Type.CLIENT, StatuesConfig.clientSpec);
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}

	}

	public void onDatapackSync(OnDatapackSyncEvent event) {
		event.sendRecipes(StatuesRecipes.UPGRADE_RECIPE.get());
		event.sendRecipes(StatuesRecipes.LOOT_RECIPE.get());
	}

	public void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StatuePatterns.expandVanillaDefinitions();
		});
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		StatuesCommands.initializeCommands(event.getDispatcher());
	}
}
