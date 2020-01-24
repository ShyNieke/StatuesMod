package com.shynieke.statues.handlers;

import com.shynieke.statues.init.StatueBlocks;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.tiles.render.PlayerTileRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
}
