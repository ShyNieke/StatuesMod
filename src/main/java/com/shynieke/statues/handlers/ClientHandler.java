package com.shynieke.statues.handlers;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.shynieke.statues.init.StatueBlocks;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.tiles.PlayerTile;
import com.shynieke.statues.tiles.render.PlayerTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.File;
import java.util.UUID;

public class ClientHandler {
//    public static void registerRenders(ModelRegistryEvent event) {
//    }

    public static void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(StatueTiles.PLAYER, PlayerTileRenderer::new);

        RenderTypeLookup.setRenderLayer(StatueBlocks.campfire_statue, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.drowned_statue, RenderType.cutout());;
        RenderTypeLookup.setRenderLayer(StatueBlocks.husk_statue, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.zombie_statue, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.enderman_statue, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.info_statue, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(StatueBlocks.wasteland_statue, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(StatueBlocks.endermite_statue, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(StatueBlocks.slime_statue, RenderType.translucent());
    }

    public static void onClientSetup(final FMLClientSetupEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(mc.getProxy(), UUID.randomUUID().toString());
        MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
        GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
        PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(mc.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
        PlayerTile.setProfileCache(playerprofilecache);
        PlayerTile.setSessionService(minecraftsessionservice);
    }
}
