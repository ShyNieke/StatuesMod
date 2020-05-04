package com.shynieke.statues.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;

public class StatuePlayerModel<T extends LivingEntity> extends PlayerModel<T> {
    public StatuePlayerModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
        this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F,0.0F);
        this.bipedHeadwear.cubeList.remove(0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize + 2.25F);
        this.bipedHeadwear.setRotationPoint(0.0F, -1.75F, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.setVisible(true);
        this.bipedHeadwear.showModel = true;
        this.bipedBodyWear.showModel = true;
        this.bipedLeftLegwear.showModel = true;
        this.bipedRightLegwear.showModel = true;
        this.bipedLeftArmwear.showModel = true;
        this.bipedRightArmwear.showModel = true;
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
