package com.shynieke.statues;

import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.compat.curios.CuriosCompat;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.handlers.DropHandler;
import com.shynieke.statues.handlers.FishHandler;
import com.shynieke.statues.handlers.SpecialHandler;
import com.shynieke.statues.handlers.TraderHandler;
import com.shynieke.statues.init.StatueEntities;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueSerializers;
import com.shynieke.statues.packets.PlayerStatueScreenMessage;
import com.shynieke.statues.packets.PlayerStatueSyncMessage;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class Statues {
	public static final Logger LOGGER = LogManager.getLogger();

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(Reference.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public Statues() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatuesConfig.commonSpec);
		eventBus.register(StatuesConfig.class);

		eventBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

		StatueRegistry.ENTITIES.register(eventBus);
		StatueRegistry.BLOCKS.register(eventBus);
		StatueRegistry.ITEMS.register(eventBus);

		eventBus.addListener(StatueEntities::registerEntityAttributes);

		if (ModList.get().isLoaded("curios")) {
			eventBus.addListener(CuriosCompat::sendImc);
		}

//		MinecraftForge.EVENT_BUS.register(new InventoryHandler());
		MinecraftForge.EVENT_BUS.register(new FishHandler());
		MinecraftForge.EVENT_BUS.register(new TraderHandler());
		MinecraftForge.EVENT_BUS.register(new DropHandler());
		MinecraftForge.EVENT_BUS.register(new SpecialHandler()); //Used for the Etho Statue

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::doClientStuff);
			eventBus.addListener(ClientHandler::registerBlockColors);
			eventBus.addListener(ClientHandler::registerItemColors);
		});

	}

	private void setup(final FMLCommonSetupEvent event) {
		StatueLootList.initializeStatueLoot();
		DataSerializers.registerSerializer(StatueSerializers.OPTIONAL_GAME_PROFILE);
		StatueEntities.setupEntities();

		CHANNEL.registerMessage(0, PlayerStatueSyncMessage.class, PlayerStatueSyncMessage::encode, PlayerStatueSyncMessage::decode, PlayerStatueSyncMessage::handle);
		CHANNEL.registerMessage(1, PlayerStatueScreenMessage.class, PlayerStatueScreenMessage::encode, PlayerStatueScreenMessage::decode, PlayerStatueScreenMessage::handle);
	}

	public void serverAboutToStart(final FMLServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		PlayerTile.setProfileCache(server.getPlayerProfileCache());
		PlayerTile.setSessionService(server.getMinecraftSessionService());
		PlayerProfileCache.setOnlineMode(server.isServerInOnlineMode());
	}
}
