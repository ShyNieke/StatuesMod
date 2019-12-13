package com.shynieke.statues.renderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class StatuesState extends StateMapperBase{
	private ResourceLocation resourceLocation;
	
	public StatuesState(ResourceLocation resource) {
		resourceLocation = resource;
	}
	
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(resourceLocation, this.getPropertyString(state.getProperties()));
	}
}
