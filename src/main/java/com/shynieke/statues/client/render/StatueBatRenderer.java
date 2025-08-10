package com.shynieke.statues.client.render;

import com.shynieke.statues.Reference;
import com.shynieke.statues.client.model.StatueBatModel;
import com.shynieke.statues.client.model.state.StatueBatRenderState;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StatueBatRenderer extends MobRenderer<StatueBatEntity, StatueBatRenderState, StatueBatModel> {
	private static final ResourceLocation BAT_TEXTURES = Reference.modLoc("textures/entity/statue_bat.png");

	public StatueBatRenderer(Context context) {
		super(context, new StatueBatModel(context.bakeLayer(ModelLayers.BAT)), 0.25F);
	}

	@Override
	public StatueBatRenderState createRenderState() {
		return new StatueBatRenderState();
	}

	public void extractRenderState(StatueBatEntity statueBatEntity, StatueBatRenderState renderState, float partialTicks) {
		super.extractRenderState(statueBatEntity, renderState, partialTicks);
		renderState.isResting = statueBatEntity.isResting();
		renderState.flyAnimationState.copyFrom(statueBatEntity.flyAnimationState);
		renderState.restAnimationState.copyFrom(statueBatEntity.restAnimationState);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(StatueBatRenderState renderState) {
		return BAT_TEXTURES;
	}
}
