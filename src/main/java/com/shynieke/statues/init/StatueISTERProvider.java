package com.shynieke.statues.init;

import com.shynieke.statues.client.render.PlayerTileInventoryRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

import java.util.concurrent.Callable;

public class StatueISTERProvider {
    public static Callable<ItemStackTileEntityRenderer> playerStatue() {
        return PlayerTileInventoryRenderer::new;
    }
}
