package com.svennieke.statues.renderer;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
	
@SideOnly(Side.CLIENT)
public class PlayerInventoryRender extends TileEntityItemStackRenderer{
	public static final ModelPlayer model = new ModelPlayer(0.03125F, false);
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
