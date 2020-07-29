package com.shynieke.statues.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class PlayerTileInventoryRenderer extends ItemStackTileEntityRenderer {
    protected TileEntityRendererDispatcher rendererDispatcher; //TODO: Apply this to the item of the player statue

    @Override
    public void func_239207_a_(ItemStack stack, TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        PlayerTileRenderer.renderPlayerItem(stack, matrixStack, buffer, combinedLight);
    }
}