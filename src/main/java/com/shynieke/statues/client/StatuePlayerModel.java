package com.shynieke.statues.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;

public class StatuePlayerModel<T extends LivingEntity> extends PlayerModel<T> {
    public StatuePlayerModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
        this.rightSleeve.setPos(-5.0F, 2.0F,0.0F);
        this.hat.cubes.remove(0);
        this.hat.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize + 2.5F);
        this.hat.setPos(0.0F, -1.75F, 0.0F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.setAllVisible(true);
        this.hat.visible = true;
        this.jacket.visible = true;
        this.leftPants.visible = true;
        this.rightPants.visible = true;
        this.leftSleeve.visible = true;
        this.rightSleeve.visible = true;
        super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
