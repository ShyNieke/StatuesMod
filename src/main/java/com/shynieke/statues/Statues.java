package com.shynieke.statues;

import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.compat.curios.CuriosCompat;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.handlers.DropHandler;
import com.shynieke.statues.handlers.SpecialHandler;
import com.shynieke.statues.handlers.TraderHandler;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class Statues {
	public static final Logger LOGGER = LogManager.getLogger();

	public Statues() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatuesConfig.commonSpec);
		eventBus.register(StatuesConfig.class);

		eventBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

		StatueRegistry.BLOCKS.register(eventBus);
		StatueRegistry.ITEMS.register(eventBus);

		if (ModList.get().isLoaded("curios")) {
			eventBus.addListener(CuriosCompat::sendImc);
		}

//		MinecraftForge.EVENT_BUS.register(new InventoryHandler());
		MinecraftForge.EVENT_BUS.register(new TraderHandler());
		MinecraftForge.EVENT_BUS.register(new DropHandler());
		MinecraftForge.EVENT_BUS.register(new SpecialHandler()); //Used for the Etho Statue

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::doClientStuff);
			eventBus.addListener(ClientHandler::registerBlockColors);
		});

	}

	private void setup(final FMLCommonSetupEvent event) {
		StatueLootList.initializeStatueLoot();
	}

	public void serverAboutToStart(final FMLServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		PlayerTile.setProfileCache(server.getPlayerProfileCache());
		PlayerTile.setSessionService(server.getMinecraftSessionService());
	}
}
