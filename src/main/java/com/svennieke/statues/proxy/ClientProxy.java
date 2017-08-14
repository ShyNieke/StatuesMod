package com.svennieke.statues.proxy;

import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.renderer.StatueBatRenderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy{

	@Override
	public void Preinit() {
		StatuesBlocks.registerRenders();
		StatuesItems.registerRenders();
		RenderingRegistry.registerEntityRenderingHandler(EntityStatueBat.class, StatueBatRenderer.FACTORY);
	}
	
	@Override
	public void Init() {

	}
}