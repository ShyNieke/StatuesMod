package com.svennieke.statues.renderer;

import org.lwjgl.opengl.GL11;

import com.svennieke.statues.renderer.model.ModelPlayerStatue;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.ArmorStandSilent;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class PlayerStatueRenderer extends TileEntitySpecialRenderer<PlayerStatueTileEntity>{
	public static final ResourceLocation Steve = new ResourceLocation("textures/entity/steve.png");
	
	public static final ModelPlayerStatue modelPlayer = new ModelPlayerStatue();

	
	public PlayerStatueRenderer() {
		super();
	}
	
	public void setSkinResourceLocation() {

	}
	
	@Override
	public void render(PlayerStatueTileEntity te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		GlStateManager.pushMatrix();
		this.bindTexture(Steve);
		//this.modelPlayer.render(entity, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        GlStateManager.popMatrix();
	    
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
