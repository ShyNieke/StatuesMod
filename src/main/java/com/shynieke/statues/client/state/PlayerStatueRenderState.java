package com.shynieke.statues.client.state;

import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.world.entity.player.PlayerSkin;

public class PlayerStatueRenderState extends ArmorStandRenderState {
	public PlayerSkin skin = DefaultPlayerSkin.getDefaultSkin();
	public int id;
	public String name = "Steve";
	public int clientLock;
	public float yOffset;
	public boolean supporter;
	public boolean upsideDown;
}
