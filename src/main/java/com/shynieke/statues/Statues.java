package com.shynieke.statues;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.compat.list.StatueLootList;
import com.shynieke.statues.compat.top.TOPCompat;
import com.shynieke.statues.config.StatuesConfigGen;
import com.shynieke.statues.entity.EntityStatueBat;
import com.shynieke.statues.handler.DespawnHandler;
import com.shynieke.statues.handler.DropHandler;
import com.shynieke.statues.handler.FishHandler;
import com.shynieke.statues.handler.MagicHandler;
import com.shynieke.statues.init.StatuesEntity;
import com.shynieke.statues.init.StatuesGuiHandler;
import com.shynieke.statues.init.StatuesHoliday;
import com.shynieke.statues.init.StatuesSounds;
import com.shynieke.statues.packets.StatuesPacketHandler;
import com.shynieke.statues.proxy.CommonProxy;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
		dependencies = Reference.DEPENDENCIES)

public class Statues {

	@Instance(Reference.MOD_ID)
	public static Statues instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

	public static StatuesTab tabStatues = new StatuesTab();

	public static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		logger.info("Registering Statues Config");
		MinecraftForge.EVENT_BUS.register(new StatuesConfigGen());

		logger.info("Registering Statues Sounds");
		StatuesSounds.registerSounds();

		logger.info("Registering Statues Entities");
		StatuesEntity.register();

		//Register Update Packets for waila if installed
		StatuesPacketHandler.registerWailaUpdatePacket();

		logger.info("Registering Statues Packets");
		StatuesPacketHandler.registerPackets();

		proxy.Preinit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (StatuesConfigGen.general.StatueBatSpawning) {
			for (Biome biome : Biome.REGISTRY) {
				biome.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityStatueBat.class, 4, 1, 2));
			}
			logger.info("Registered Statues Bat Spawn");
		}

		logger.info("Registering Statues Gui Handler");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new StatuesGuiHandler());

		if (StatuesConfigGen.events.halloweenSpawning) {
			StatuesHoliday.registerSpawning();
		}

		TOPCompat.register();

		//Initialize loot
		logger.info("Initialize Statues Loot");
		StatueLootList.initializeStatueLoot();

		logger.info("Registering Statues Event Handlers");
		MinecraftForge.EVENT_BUS.register(new DropHandler());
		MinecraftForge.EVENT_BUS.register(new FishHandler());
		MinecraftForge.EVENT_BUS.register(new DespawnHandler());
		MinecraftForge.EVENT_BUS.register(new MagicHandler());

		proxy.Init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.PostInit();
	}
}
