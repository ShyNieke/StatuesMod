package com.shynieke.statues.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.shynieke.statues.Reference;
import com.shynieke.statues.client.model.StatueBatModel;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class StatueBatRenderer extends MobRenderer<StatueBatEntity, StatueBatModel> {
    private static final ResourceLocation BAT_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/entity/statuebat.png");

    public StatueBatRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new StatueBatModel(), 0.25F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(StatueBatEntity entity) {
        return BAT_TEXTURES;
    }

    protected void preRenderCallback(StatueBatEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.35F, 0.35F, 0.35F);
    }

    protected void applyRotations(StatueBatEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving.getIsBatHanging()) {
            matrixStackIn.translate(0.0D, (double) -0.1F, 0.0D);
        } else {
            matrixStackIn.translate(0.0D, (double) (MathHelper.cos(ageInTicks * 0.3F) * 0.1F), 0.0D);
        }

        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }
}
