package com.svennieke.statues;

import com.svennieke.statues.handler.DropHandler;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesConfigGen;
import com.svennieke.statues.init.StatuesCrafting;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class Statues {

	@Instance(Reference.MOD_ID)
	public static Statues instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabStatues = new CreativeTabs("tabStatues") {
		@Override
		public Item getTabIconItem() {
			return StatuesItems.core;
		}
	};
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		StatuesConfigGen.configOptions(cfg);
		
		StatuesBlocks.init();
		StatuesBlocks.register();
		StatuesItems.init();
		StatuesItems.register();
		StatuesCrafting.register();
		
		proxy.Preinit();
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.Init();
		MinecraftForge.EVENT_BUS.register(new DropHandler());
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		
    }
}
