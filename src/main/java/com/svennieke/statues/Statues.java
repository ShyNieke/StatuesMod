package com.svennieke.statues;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.handler.DropHandler;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesConfigGen;
import com.svennieke.statues.init.StatuesEntity;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
		
	public static CreativeTabs tabStatues = new CreativeTabs("tabStatues") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(StatuesItems.core);
		}
	};
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		isBaublesEnabled = Loader.isModLoaded("Baubles");
		if(isBaublesEnabled)logger.info("Loading With Baubles Compat");
		else{logger.info("Loading Without Baubles Compat");}
		
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		StatuesConfigGen.configOptions(cfg);
		
		StatuesEntity.register();
		StatuesBlocks.init();
		StatuesBlocks.register();
		StatuesItems.init();
		StatuesItems.register();

		//StatuesCrafting.register();
		
		proxy.Preinit();
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event)
	{
		for (Biome biome : Biome.REGISTRY) {
		    biome.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityStatueBat.class, 4, 1, 2));
		}
		System.out.println("Registered Statues Bat Spawn");
		
		proxy.Init();
		MinecraftForge.EVENT_BUS.register(new DropHandler());
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		
    }
}
