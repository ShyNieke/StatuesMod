package com.shynieke.statues.client.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.List;

public class StatueTableRenderState extends BlockEntityRenderState {
	public List<ItemStackRenderState> items;
	public float timeAndPartial = 0F;

}
