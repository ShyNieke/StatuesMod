package com.svennieke.statues.renderer;

import com.svennieke.statues.entity.EntityStatueBat;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class StatueBatRenderer extends RenderBat
{
	public static final Factory FACTORY = new Factory();
	
	private static final ResourceLocation texture = new ResourceLocation("statues:textures/entity/statuebat.png");
	
	public StatueBatRenderer(RenderManager renderManagerIn) {
		super(renderManagerIn);
	  }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBat entity) {
	  return texture;
	}
	
	public static class Factory implements IRenderFactory<EntityStatueBat> {
	
	  @Override
	  public Render<? super EntityStatueBat> createRenderFor(RenderManager manager) {
	    return new StatueBatRenderer(manager);
	  }
	}

}
