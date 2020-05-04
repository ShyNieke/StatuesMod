package com.shynieke.statues;

import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.handlers.DropHandler;
import com.shynieke.statues.handlers.SpecialHandler;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
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
		eventBus.addListener(this::enqueueIMC);
		eventBus.addListener(this::processIMC);

//		MinecraftForge.EVENT_BUS.register(new InventoryHandler());
		MinecraftForge.EVENT_BUS.register(new DropHandler());
		MinecraftForge.EVENT_BUS.register(new SpecialHandler()); //Used for the Etho Statue

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::doClientStuff);
		});

	}

	private void setup(final FMLCommonSetupEvent event)
	{
		StatueLootList.initializeStatueLoot();
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{

	}

	private void processIMC(final InterModProcessEvent event)
	{

	}
}
