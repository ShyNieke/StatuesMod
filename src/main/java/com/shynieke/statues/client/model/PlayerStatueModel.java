package com.shynieke.statues.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.client.model.state.PlayerStatueRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.HumanoidArm;

public class PlayerStatueModel extends HumanoidModel<PlayerStatueRenderState> {

	public final ModelPart leftSleeve;
	public final ModelPart rightSleeve;
	public final ModelPart leftPants;
	public final ModelPart rightPants;
	public final ModelPart jacket;
	private final boolean slim;

	public PlayerStatueModel(ModelPart root, boolean slim) {
		super(root, RenderType::entityTranslucent);
		this.slim = slim;
		this.leftSleeve = this.leftArm.getChild("left_sleeve");
		this.rightSleeve = this.rightArm.getChild("right_sleeve");
		this.leftPants = this.leftLeg.getChild("left_pants");
		this.rightPants = this.rightLeg.getChild("right_pants");
		this.jacket = this.body.getChild("jacket");

		this.hat.setRotation(0.0F, -1.75F, 0.0F);
		this.rightSleeve.setRotation(-5.0F, 2.0F, 0.0F);
	}

	public static MeshDefinition createStatueMesh(CubeDeformation cubeDeformation, boolean slim) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(cubeDeformation, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
		float f = 0.25F;
		if (slim) {
			PartDefinition left_arm = partdefinition.addOrReplaceChild(
					"left_arm",
					CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation),
					PartPose.offset(5.0F, 2.0F, 0.0F)
			);
			PartDefinition right_arm = partdefinition.addOrReplaceChild(
					"right_arm",
					CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation),
					PartPose.offset(-5.0F, 2.0F, 0.0F)
			);
			left_arm.addOrReplaceChild(
					"left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
			);
			right_arm.addOrReplaceChild(
					"right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
			);
		} else {
			PartDefinition left_arm = partdefinition.addOrReplaceChild(
					"left_arm",
					CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
					PartPose.offset(5.0F, 2.0F, 0.0F)
			);
			PartDefinition right_arm = partdefinition.getChild("right_arm");
			left_arm.addOrReplaceChild(
					"left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
			);
			right_arm.addOrReplaceChild(
					"right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
			);
		}

		PartDefinition left_leg = partdefinition.addOrReplaceChild(
				"left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(1.9F, 12.0F, 0.0F)
		);
		PartDefinition right_leg = partdefinition.getChild("right_leg");
		left_leg.addOrReplaceChild(
				"left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
		);
		right_leg.addOrReplaceChild(
				"right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
		);
		PartDefinition body = partdefinition.getChild("body");
		body.addOrReplaceChild(
				"jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.ZERO
		);
		return meshdefinition;
	}

	@Override
	public void setupAnim(PlayerStatueRenderState renderState) {
		this.body.visible = true;
		this.rightArm.visible = true;
		this.leftArm.visible = true;
		this.rightLeg.visible = true;
		this.leftLeg.visible = true;
		super.setupAnim(renderState);
		this.head.xRot = (float) (Math.PI / 180.0) * renderState.headPose.getX();
		this.head.yRot = (float) (Math.PI / 180.0) * renderState.headPose.getY();
		this.head.zRot = (float) (Math.PI / 180.0) * renderState.headPose.getZ();
		this.body.xRot = (float) (Math.PI / 180.0) * renderState.bodyPose.getX();
		this.body.yRot = (float) (Math.PI / 180.0) * renderState.bodyPose.getY();
		this.body.zRot = (float) (Math.PI / 180.0) * renderState.bodyPose.getZ();
		this.leftArm.xRot = (float) (Math.PI / 180.0) * renderState.leftArmPose.getX();
		this.leftArm.yRot = (float) (Math.PI / 180.0) * renderState.leftArmPose.getY();
		this.leftArm.zRot = (float) (Math.PI / 180.0) * renderState.leftArmPose.getZ();
		this.rightArm.xRot = (float) (Math.PI / 180.0) * renderState.rightArmPose.getX();
		this.rightArm.yRot = (float) (Math.PI / 180.0) * renderState.rightArmPose.getY();
		this.rightArm.zRot = (float) (Math.PI / 180.0) * renderState.rightArmPose.getZ();
		this.leftLeg.xRot = (float) (Math.PI / 180.0) * renderState.leftLegPose.getX();
		this.leftLeg.yRot = (float) (Math.PI / 180.0) * renderState.leftLegPose.getY();
		this.leftLeg.zRot = (float) (Math.PI / 180.0) * renderState.leftLegPose.getZ();
		this.rightLeg.xRot = (float) (Math.PI / 180.0) * renderState.rightLegPose.getX();
		this.rightLeg.yRot = (float) (Math.PI / 180.0) * renderState.rightLegPose.getY();
		this.rightLeg.zRot = (float) (Math.PI / 180.0) * renderState.rightLegPose.getZ();
		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.leftPants.copyFrom(this.leftLeg);
		this.rightPants.copyFrom(this.rightLeg);
	}

	@Override
	public void setAllVisible(boolean visible) {
		super.setAllVisible(visible);
		this.leftSleeve.visible = visible;
		this.rightSleeve.visible = visible;
		this.leftPants.visible = visible;
		this.rightPants.visible = visible;
		this.jacket.visible = visible;
	}

	@Override
	public void translateToHand(HumanoidArm side, PoseStack poseStack) {
		this.root().translateAndRotate(poseStack);
		ModelPart modelpart = this.getArm(side);
		if (this.slim) {
			float f = 0.5F * (float) (side == HumanoidArm.RIGHT ? 1 : -1);
			modelpart.x += f;
			modelpart.translateAndRotate(poseStack);
			modelpart.x -= f;
		} else {
			modelpart.translateAndRotate(poseStack);
		}
	}
}
