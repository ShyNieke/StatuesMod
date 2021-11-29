package com.shynieke.statues.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

public class PlayerTileInventoryRenderer extends ItemStackTileEntityRenderer {
    @Override
    public void renderByItem(ItemStack stack, TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        PlayerTileRenderer.renderPlayerItem(stack, matrixStack, buffer, combinedLight);
    }
}