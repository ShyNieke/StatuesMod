package com.shynieke.statues.client;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.client.render.PlayerTileRenderer;
import com.shynieke.statues.init.StatueBlocks;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.File;
import java.util.UUID;

public class ClientHandler {

    public static void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(StatueTiles.PLAYER, PlayerTileRenderer::new);

        RenderTypeLookup.setRenderLayer(StatueBlocks.campfire_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.drowned_statue, RenderType.getCutout());;
        RenderTypeLookup.setRenderLayer(StatueBlocks.husk_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.zombie_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.enderman_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.info_statue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.wasteland_statue, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(StatueBlocks.endermite_statue, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(StatueBlocks.slime_statue, RenderType.getTranslucent());

        Minecraft mc = Minecraft.getInstance();
        YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(mc.getProxy(), UUID.randomUUID().toString());
        MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
        GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
        PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(mc.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
        PlayerTile.setProfileCache(playerprofilecache);
        PlayerTile.setSessionService(minecraftsessionservice);
    }
}
