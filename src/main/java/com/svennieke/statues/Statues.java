package com.svennieke.statues;

import com.svennieke.statues.RecipeConditions.AlternatePlayerRecipe;
import com.svennieke.statues.RecipeConditions.InteractionCondition;
import com.svennieke.statues.RecipeConditions.NewSystemCondition;
import com.svennieke.statues.RecipeConditions.PlayerCraftingRecipe;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.config.StatuesConfig;
import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.entity.fakeentity.FakeBlaze;
import com.svennieke.statues.entity.fakeentity.FakeCreeper;
import com.svennieke.statues.entity.fakeentity.FakeEnderman;
import com.svennieke.statues.entity.fakeentity.FakeGhast;
import com.svennieke.statues.entity.fakeentity.FakeGuardian;
import com.svennieke.statues.entity.fakeentity.FakeHusk;
import com.svennieke.statues.entity.fakeentity.FakeMagmaCube;
import com.svennieke.statues.entity.fakeentity.FakeShulker;
import com.svennieke.statues.entity.fakeentity.FakeSkeleton;
import com.svennieke.statues.entity.fakeentity.FakeSlime;
import com.svennieke.statues.entity.fakeentity.FakeSpider;
import com.svennieke.statues.entity.fakeentity.FakeStray;
import com.svennieke.statues.entity.fakeentity.FakeWitch;
import com.svennieke.statues.entity.fakeentity.FakeWitherSkeleton;
import com.svennieke.statues.entity.fakeentity.FakeZombie;
import com.svennieke.statues.entity.fakeentity.FakeZombiePigman;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeFireball;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeLargeFireball;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeShulkerBullet;
import com.svennieke.statues.init.StatuesEntity;
import com.svennieke.statues.init.StatuesGuiHandler;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.renderer.PlayerStatueRenderer;
import com.svennieke.statues.renderer.StatueBatRenderer;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderHusk;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderPigZombie;
import net.minecraft.client.renderer.entity.RenderShulker;
import net.minecraft.client.renderer.entity.RenderShulkerBullet;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderStray;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.entity.RenderWitherSkeleton;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class Statues {
		
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static final Marker STATUESMOD = MarkerManager.getMarker("STATUESMOD");
    
//	public static boolean isBaublesEnabled = false;
//	public static boolean isVeinminerInstalled = false;
//	public static boolean isWailaInstalled = false;
	
	public static final ItemGroup tabStatues = new ItemGroup(Reference.MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(StatuesItems.core);
		}
    };
    
	public Statues() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatuesConfig.commonSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(StatuesConfig.class);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        
        //FMLJavaModLoadingContext.get().getModEventBus().register(new StatuesGuiHandler());
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> StatuesGuiHandler::openShulkerGui);

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.addListener(ClientHandler::registerRenders);
		});
	}

	private void setup(final FMLCommonSetupEvent event)
    {
//		isVeinminerInstalled = Loader.isModLoaded("veinminer");
//		isWailaInstalled = Loader.isModLoaded("waila");
		
//		isBaublesEnabled = Loader.isModLoaded("baubles");
//		if(isBaublesEnabled)logger.info("Loading With Baubles Compat");
//		else{logger.info("Loading Without Baubles Compat");}
		
//		logger.info("Registering Statues Entities");
//		StatuesEntity.register();
		
//		Register Update Packets for waila if installed
//		StatuesPacketHandler.registerWailaUpdatePacket();
		
//		logger.info("Registering Statues Packets");
//		StatuesPacketHandler.registerPackets();
		CraftingHelper.register(new ResourceLocation(Reference.MOD_ID, "newsystem"), new NewSystemCondition());
		CraftingHelper.register(new ResourceLocation(Reference.MOD_ID, "playerrecipe"), new AlternatePlayerRecipe());
		CraftingHelper.register(new ResourceLocation(Reference.MOD_ID, "originalplayerrecipe"), new PlayerCraftingRecipe());
		CraftingHelper.register(new ResourceLocation(Reference.MOD_ID, "interaction"), new InteractionCondition());
    }

//    private void enqueueIMC(final InterModEnqueueEvent event)
//    {
//    	
//    }

    private void processIMC(final InterModProcessEvent event)
    {
		for (Biome biome : Biome.BIOMES) {
		    biome.getSpawns(EnumCreatureType.AMBIENT).add(new SpawnListEntry(StatuesEntity.STATUE_BAT, 4, 1, 2));
		}
		LOGGER.info("Registered Statues Bat Spawn");
		
		//logger.info("Registering Statues Gui Handler");
		//NetworkRegistry.INSTANCE.registerGuiHandler(this, new StatuesGuiHandler());
		
//		if(StatuesConfigGen.events.halloweenSpawning)
//		{
//			StatuesHoliday.registerSpawning();
//		}
		
//		TOPCompat.register();
		
		LOGGER.info("Initialize Statues Loot");
		StatueLootList.initializeStatueLoot();

//		logger.info("Registering Statues Event Handlers");
//		MinecraftForge.EVENT_BUS.register(new DropHandler());
//		MinecraftForge.EVENT_BUS.register(new FishHandler());
//		MinecraftForge.EVENT_BUS.register(new DespawnHandler());
//		MinecraftForge.EVENT_BUS.register(new MagicHandler());
    }
    
    static class ClientHandler {
    	static void registerRenders(ModelRegistryEvent event) {
			ClientRegistry.bindTileEntitySpecialRenderer(PlayerStatueTileEntity.class, new PlayerStatueRenderer());
			
    		RenderingRegistry.registerEntityRenderingHandler(EntityStatueBat.class, StatueBatRenderer.FACTORY);
    		RenderingRegistry.registerEntityRenderingHandler(FakeZombie.class,
    				renderManager -> new RenderZombie(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeHusk.class,
    				renderManager -> new RenderHusk(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeSkeleton.class,
    				renderManager -> new RenderSkeleton(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeBlaze.class,
    				renderManager -> new RenderBlaze(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeFireball.class,
    				renderManager -> new RenderFireball(renderManager, 0.5F));
    		RenderingRegistry.registerEntityRenderingHandler(FakeCreeper.class,
    				renderManager -> new RenderCreeper(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeEnderman.class,
    				renderManager -> new RenderEnderman(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeGhast.class,
    				renderManager -> new RenderGhast(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeLargeFireball.class,
    				renderManager -> new RenderFireball(renderManager, 2.0F));
    		RenderingRegistry.registerEntityRenderingHandler(FakeGuardian.class,
    				renderManager -> new RenderGuardian(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeSlime.class,
    				renderManager -> new RenderSlime(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeMagmaCube.class,
    				renderManager -> new RenderMagmaCube(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeShulker.class,
    				renderManager -> new RenderShulker(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeShulkerBullet.class,
    				renderManager -> new RenderShulkerBullet(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeWitch.class,
    				renderManager -> new RenderWitch(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeSpider.class,
    				renderManager -> new RenderSpider<EntitySpider>(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeStray.class,
    				renderManager -> new RenderStray(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeZombiePigman.class,
    				renderManager -> new RenderPigZombie(renderManager));
    		RenderingRegistry.registerEntityRenderingHandler(FakeWitherSkeleton.class,
    				renderManager -> new RenderWitherSkeleton(renderManager));
    	}
    }
}
