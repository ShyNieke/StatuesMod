package com.shynieke.statues.client.render;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.component.ResolvableProfile;

public class PlayerBlockRenderState extends BlockEntityRenderState {
	public ResolvableProfile profile;
	public PlayerSkin skin;
	public boolean isSlim = false;
	public RenderType renderType;
}
