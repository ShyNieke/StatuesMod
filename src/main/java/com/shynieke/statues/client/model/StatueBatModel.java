package com.shynieke.statues.client.model;

import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

/**
 * Because vanilla BatModel doesn't allow any other class than BatEntity
 */
public class StatueBatModel extends HierarchicalModel<StatueBatEntity> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightWingTip;
    private final ModelPart leftWingTip;

    public StatueBatModel(ModelPart modelPart) {
        this.root = modelPart;
        this.head = modelPart.getChild("head");
        this.body = modelPart.getChild("body");
        this.rightWing = this.body.getChild("right_wing");
        this.rightWingTip = this.rightWing.getChild("right_wing_tip");
        this.leftWing = this.body.getChild("left_wing");
        this.leftWingTip = this.leftWing.getChild("left_wing_tip");
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(StatueBatEntity statueBat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (statueBat.isResting()) {
            this.head.xRot = headPitch * 0.017453292F;
            this.head.yRot = 3.1415927F - netHeadYaw * 0.017453292F;
            this.head.zRot = 3.1415927F;
            this.head.setPos(0.0F, -2.0F, 0.0F);
            this.rightWing.setPos(-3.0F, 0.0F, 3.0F);
            this.leftWing.setPos(3.0F, 0.0F, 3.0F);
            this.body.xRot = 3.1415927F;
            this.rightWing.xRot = -0.15707964F;
            this.rightWing.yRot = -1.2566371F;
            this.rightWingTip.yRot = -1.7278761F;
            this.leftWing.xRot = this.rightWing.xRot;
            this.leftWing.yRot = -this.rightWing.yRot;
            this.leftWingTip.yRot = -this.rightWingTip.yRot;
        } else {
            this.head.xRot = headPitch * 0.017453292F;
            this.head.yRot = netHeadYaw * 0.017453292F;
            this.head.zRot = 0.0F;
            this.head.setPos(0.0F, 0.0F, 0.0F);
            this.rightWing.setPos(0.0F, 0.0F, 0.0F);
            this.leftWing.setPos(0.0F, 0.0F, 0.0F);
            this.body.xRot = 0.7853982F + Mth.cos(ageInTicks * 0.1F) * 0.15F;
            this.body.yRot = 0.0F;
            this.rightWing.yRot = Mth.cos(ageInTicks * 74.48451F * 0.017453292F) * 3.1415927F * 0.25F;
            this.leftWing.yRot = -this.rightWing.yRot;
            this.rightWingTip.yRot = this.rightWing.yRot * 0.5F;
            this.leftWingTip.yRot = -this.rightWing.yRot * 0.5F;
        }
    }
}
