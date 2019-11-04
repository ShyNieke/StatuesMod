package com.svennieke.statues.handlers;

import com.svennieke.statues.tiles.PlayerTile;
import com.svennieke.statues.tiles.render.PlayerTileRenderer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientHandler {
    public static void registerRenders(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(PlayerTile.class, new PlayerTileRenderer());
    }
}
