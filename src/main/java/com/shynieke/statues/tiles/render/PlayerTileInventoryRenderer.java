package com.shynieke.statues.tiles.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class PlayerTileInventoryRenderer extends ItemStackTileEntityRenderer {
    protected TileEntityRendererDispatcher rendererDispatcher; //TODO: Apply this to the item of the player statue

    @Override
    public void render(ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int p_228364_4_, int p_228364_5_) {
        PlayerTileRenderer.renderPlayerItem(stack, matrixStack, renderTypeBuffer, p_228364_4_, p_228364_5_);
    }
}