package com.svennieke.statues.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
	
@OnlyIn(Dist.CLIENT)
public class PlayerInventoryRender extends TileEntityItemStackRenderer{
    protected TileEntityRendererDispatcher rendererDispatcher;
	
	@Override
	public void renderByItem(ItemStack stack) {		
        if (TileEntitySkullRenderer.instance != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            PlayerStatueRenderer.instance.renderPlayerItem(stack);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
	}
}
