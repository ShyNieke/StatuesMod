package com.shynieke.statues.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.Reference;
import com.shynieke.statues.client.model.StatueBatModel;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class StatueBatRenderer extends MobRenderer<StatueBatEntity, StatueBatModel> {
    private static final ResourceLocation BAT_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/entity/statuebat.png");

    public StatueBatRenderer(Context context) {
        super(context, new StatueBatModel(context.bakeLayer(ModelLayers.BAT)), 0.25F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(StatueBatEntity entity) {
        return BAT_TEXTURES;
    }

    protected void scale(StatueBatEntity entitylivingbaseIn, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.35F, 0.35F, 0.35F);
    }

    protected void setupRotations(StatueBatEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving.isResting()) {
            poseStack.translate(0.0D, (double) -0.1F, 0.0D);
        } else {
            poseStack.translate(0.0D, (double) (Mth.cos(ageInTicks * 0.3F) * 0.1F), 0.0D);
        }

        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);
    }
}
