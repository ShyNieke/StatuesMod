package com.shynieke.statues.client;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.blocks.statues.fish.FishStatueBlock;
import com.shynieke.statues.client.ber.StatueTableBER;
import com.shynieke.statues.client.model.PlayerStatueModel;
import com.shynieke.statues.client.property.StatueCompassAngle;
import com.shynieke.statues.client.render.PlayerBlockRenderer;
import com.shynieke.statues.client.render.PlayerSpecialRenderer;
import com.shynieke.statues.client.render.PlayerStatueRenderer;
import com.shynieke.statues.client.render.StatueBatRenderer;
import com.shynieke.statues.client.screen.ShulkerStatueScreen;
import com.shynieke.statues.client.screen.StatueTableScreen;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.Services;
import net.minecraft.server.players.GameProfileCache;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientHandler {
	public static final ModelLayerLocation PLAYER_STATUE = new ModelLayerLocation(Reference.modLoc("player_statue"), "player_statue");
	public static final ModelLayerLocation PLAYER_STATUE_SLIM = new ModelLayerLocation(Reference.modLoc("player_statue_slim"), "player_statue_slim");
	public static final List<UUID> SUPPORTER = new ArrayList<>();
	public static final List<UUID> TRANSLATORS = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void doClientStuff(final FMLClientSetupEvent event) {
		setPlayerCache(Minecraft.getInstance());

		new Thread(() -> {
			Statues.LOGGER.info("Loading Statues supporter data...");
			try {
				URL url = new URL("https://raw.githubusercontent.com/ShyNieke/StatuesMod/1.19.x/Supporters.txt");
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
					String s;
					while ((s = reader.readLine()) != null) {
						String[] split = s.split(" ", 2);
						if (split.length != 2) {
							Statues.LOGGER.error("Invalid entry {} will be ignored.", s);
							continue;
						}
						SupporterType type = SupporterType.valueOf(split[1]);
						switch (type) {
							case TRANSLATOR -> TRANSLATORS.add(UUID.fromString(split[0]));
							default -> SUPPORTER.add(UUID.fromString(split[0]));
						}
					}
					reader.close();
				} catch (IOException ex) {
					Statues.LOGGER.error("Exception loading supporter data!");
					Statues.LOGGER.trace("Trace", ex);
				}
			} catch (Exception k) {
				//not possible
			}
			Statues.LOGGER.info("Loaded {} supporters.", SUPPORTER.size());
			Statues.LOGGER.info("Loaded {} translators.", TRANSLATORS.size());
		}, "Statues Perks Data Loader").start();

//		if (ModList.get().isLoaded("curios")) {
//			com.shynieke.statues.compat.curios.client.StatueCurioRenderer.setupRenderer();
//		}
	}

	public static void registerRangeSelectProperties(final RegisterRangeSelectItemModelPropertyEvent event) {
		event.register(Reference.modLoc("statue_compass_angle"), StatueCompassAngle.MAP_CODEC);
	}

	public static void onRegisterMenu(final RegisterMenuScreensEvent event) {
		event.register(StatueRegistry.STATUE_TABLE_MENU.get(), StatueTableScreen::new);
		event.register(StatueRegistry.SHULKER_STATUE_MENU.get(), ShulkerStatueScreen::new);
	}

	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(StatueBlockEntities.PLAYER.get(), PlayerBlockRenderer::new);

		event.registerEntityRenderer(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatueRenderer::new);
		event.registerEntityRenderer(StatueRegistry.STATUE_BAT.get(), StatueBatRenderer::new);

		event.registerBlockEntityRenderer(StatueBlockEntities.STATUE_TABLE.get(), StatueTableBER::new);
	}

	public static void registerSpecialModelRenderers(RegisterSpecialModelRendererEvent event) {
		event.register(Reference.modLoc("player"), PlayerSpecialRenderer.Unbaked.MAP_CODEC);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(PLAYER_STATUE, () -> LayerDefinition.create(PlayerStatueModel.createStatueMesh(CubeDeformation.NONE, false), 64, 64));
		event.registerLayerDefinition(PLAYER_STATUE_SLIM, () -> LayerDefinition.create(PlayerStatueModel.createStatueMesh(CubeDeformation.NONE, true), 64, 64));
	}

	public static void registerBlockColors(final RegisterColorHandlersEvent.Block event) {
		event.register(FishStatueBlock::getColor, StatueRegistry.TROPICAL_FISH_B.get(), StatueRegistry.TROPICAL_FISH_BB.get(), StatueRegistry.TROPICAL_FISH_BE.get(),
				StatueRegistry.TROPICAL_FISH_BM.get(), StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(),
				StatueRegistry.TROPICAL_FISH_E.get(), StatueRegistry.TROPICAL_FISH_ES.get(), StatueRegistry.TROPICAL_FISH_HB.get(),
				StatueRegistry.TROPICAL_FISH_SB.get(), StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get());
	}

	public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
		Minecraft mc = Minecraft.getInstance();
		if (!mc.isLocalServer()) {
			setPlayerCache(mc);
		}
	}

	public static void onRespawn(ClientPlayerNetworkEvent.Clone event) {
		Minecraft mc = Minecraft.getInstance();
		if (!mc.isLocalServer()) {
			setPlayerCache(mc);
		}
	}

	private static void setPlayerCache(Minecraft mc) {
		YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(mc.getProxy());
		Services services = Services.create(authenticationService, mc.gameDirectory);
		services.profileCache().setExecutor(mc);
		PlayerBlockEntity.setup(services, mc);
		GameProfileCache.setUsesAuthentication(false);
	}
}
