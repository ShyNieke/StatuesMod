package com.shynieke.statues.client.model;

import com.google.common.collect.ImmutableList;
import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PlayerStatueModel extends PlayerModel<PlayerStatue> {

    public PlayerStatueModel(ModelPart modelPart, boolean slim) {
        super(modelPart, slim);

        this.hat.setRotation(0.0F, -1.75F, 0.0F);
        this.rightSleeve.setRotation(-5.0F, 2.0F, 0.0F);
    }

    public static MeshDefinition createStatueMesh(CubeDeformation cubeDeformation, boolean slim) {
        MeshDefinition meshdefinition = PlayerModel.createMesh(cubeDeformation, slim);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(2.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        if (slim) {
            partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-5.0F, 2.5F, 0.0F));
        } else {
            partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        }
        return meshdefinition;
    }

    @Override
    public void setupAnim(PlayerStatue entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getX();
        this.head.yRot = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getY();
        this.head.zRot = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getZ();
        this.head.setPos(0.0F, 1.0F, 0.0F);
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
        this.leftLeg.setPos(1.9F, 11.0F, 0.0F);
        this.rightLeg.xRot = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getX();
        this.rightLeg.yRot = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getY();
        this.rightLeg.zRot = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getZ();
        this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head, this.hat);
    }
}
