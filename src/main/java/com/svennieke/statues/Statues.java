package com.svennieke.statues;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.handler.DespawnHandler;
import com.svennieke.statues.handler.DropHandler;
import com.svennieke.statues.handler.FishHandler;
import com.svennieke.statues.handler.TemporaryHandler;
import com.svennieke.statues.init.StatuesEntity;
import com.svennieke.statues.init.StatuesGuiHandler;
import com.svennieke.statues.init.StatuesHoliday;
import com.svennieke.statues.init.StatuesSounds;
import com.svennieke.statues.packets.StatuesPacketHandler;
import com.svennieke.statues.proxy.CommonProxy;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
	public static boolean isBaublesEnabled = false;
	public static boolean isVeinminerInstalled = false;
	public static boolean isWailaInstalled = false;
		
	public static StatuesTab tabStatues = new StatuesTab();
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		isVeinminerInstalled = Loader.isModLoaded("veinminer");
		isWailaInstalled = Loader.isModLoaded("waila");
		
		isBaublesEnabled = Loader.isModLoaded("baubles");
		if(isBaublesEnabled)logger.info("Loading With Baubles Compat");
		else{logger.info("Loading Without Baubles Compat");}
		
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
		
		//StatuesCrafting.register();
		
		proxy.Preinit();
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event)
	{
		for (Biome biome : Biome.REGISTRY) {
		    biome.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityStatueBat.class, 4, 1, 2));
		}
		logger.info("Registered Statues Bat Spawn");
		
		logger.info("Registering Statues Gui Handler");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new StatuesGuiHandler());
		
		StatuesHoliday.registerSpawning();
		
		//Initialize loot
		logger.info("Initialize Statues Loot");
		StatueLootList.initializeStatueLoot();
		
		logger.info("Registering Statues Event Handlers");
		MinecraftForge.EVENT_BUS.register(new DropHandler());
		MinecraftForge.EVENT_BUS.register(new FishHandler());
		MinecraftForge.EVENT_BUS.register(new DespawnHandler());
		MinecraftForge.EVENT_BUS.register(new TemporaryHandler());
		
		proxy.Init();
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		
    }
}
