package com.shynieke.statues.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class PlayerTileInventoryRenderer extends ItemStackTileEntityRenderer {
    protected TileEntityRendererDispatcher rendererDispatcher; //TODO: Apply this to the item of the player statue

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        PlayerTileRenderer.renderPlayerItem(itemStackIn, matrixStackIn, bufferIn, combinedLightIn);
    }
}