package com.shynieke.statues.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.client.model.state.PlayerStatueRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.HumanoidArm;

import java.util.List;

public class PlayerStatueModel extends HumanoidModel<PlayerStatueRenderState> {

	private final List<ModelPart> bodyParts;
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
		this.bodyParts = List.of(this.head, this.body, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);

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
		this.leftSleeve.copyFrom(this.leftArm);
		this.rightSleeve.copyFrom(this.rightArm);
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
