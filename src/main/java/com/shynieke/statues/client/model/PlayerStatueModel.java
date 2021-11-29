package com.shynieke.statues.client.model;

import com.google.common.collect.ImmutableList;
import com.shynieke.statues.entity.PlayerStatueEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class PlayerStatueModel extends PlayerModel<PlayerStatueEntity> {

    public PlayerStatueModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }

    public void setupAnim(PlayerStatueEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getX();
        this.head.yRot = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getY();
        this.head.zRot = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getZ();
        this.body.xRot = ((float)Math.PI / 180F) * entityIn.getBodyRotation().getX();
        this.body.yRot = ((float)Math.PI / 180F) * entityIn.getBodyRotation().getY();
        this.body.zRot = ((float)Math.PI / 180F) * entityIn.getBodyRotation().getZ();
        this.leftArm.xRot = ((float)Math.PI / 180F) * entityIn.getLeftArmRotation().getX();
        this.leftArm.yRot = ((float)Math.PI / 180F) * entityIn.getLeftArmRotation().getY();
        this.leftArm.zRot = ((float)Math.PI / 180F) * entityIn.getLeftArmRotation().getZ();
        this.rightArm.xRot = ((float)Math.PI / 180F) * entityIn.getRightArmRotation().getX();
        this.rightArm.yRot = ((float)Math.PI / 180F) * entityIn.getRightArmRotation().getY();
        this.rightArm.zRot = ((float)Math.PI / 180F) * entityIn.getRightArmRotation().getZ();
        this.leftLeg.xRot = ((float)Math.PI / 180F) * entityIn.getLeftLegRotation().getX();
        this.leftLeg.yRot = ((float)Math.PI / 180F) * entityIn.getLeftLegRotation().getY();
        this.leftLeg.zRot = ((float)Math.PI / 180F) * entityIn.getLeftLegRotation().getZ();
        this.rightLeg.xRot = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getX();
        this.rightLeg.yRot = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getY();
        this.rightLeg.zRot = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getZ();
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head, this.hat);
    }
}
