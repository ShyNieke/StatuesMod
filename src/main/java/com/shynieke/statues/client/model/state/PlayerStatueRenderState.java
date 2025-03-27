package com.shynieke.statues.client.model.state;

import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;

public class PlayerStatueRenderState extends ArmorStandRenderState {
	public PlayerSkin skin = DefaultPlayerSkin.getDefaultSkin();
	public int id;
	public String name = "Steve";
	public int clientLock;
	public float yOffset;
	public boolean supporter;
	public boolean upsideDown;
	public final ItemStackRenderState heldOnHead = new ItemStackRenderState();
}
