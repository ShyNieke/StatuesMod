package com.shynieke.statues.tiles.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class PlayerTileInventoryRenderer extends ItemStackTileEntityRenderer {
    protected TileEntityRendererDispatcher rendererDispatcher; //TODO: Apply this to the item of the player statue

    @Override
    public void renderByItem(ItemStack stack) {
        if (SkullTileEntityRenderer.instance != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            PlayerTileRenderer.instance.renderPlayerItem(stack);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }
}