package com.shynieke.statues.handlers;

import com.shynieke.statues.tiles.PlayerTile;
import com.shynieke.statues.tiles.render.PlayerTileRenderer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientHandler {
    public static void registerRenders(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(PlayerTile.class, new PlayerTileRenderer());
    }
}
