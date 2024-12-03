package com.shynieke.statues.client.model;

import com.shynieke.statues.client.model.state.StatueBatRenderState;
import net.minecraft.client.animation.definitions.BatAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;

/**
 * Because vanilla BatModel doesn't allow any other class than BatEntity
 */
public class StatueBatModel extends EntityModel<StatueBatRenderState> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightWingTip;
	private final ModelPart leftWingTip;
	private final ModelPart feet;

	public StatueBatModel(ModelPart root) {
		super(root, RenderType::entityCutout);
		this.root = root;
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.rightWing = this.body.getChild("right_wing");
		this.rightWingTip = this.rightWing.getChild("right_wing_tip");
		this.leftWing = this.body.getChild("left_wing");
		this.leftWingTip = this.leftWing.getChild("left_wing_tip");
		this.feet = this.body.getChild("feet");
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	@Override
	public void setupAnim(StatueBatRenderState statueBatRenderState) {
		super.setupAnim(statueBatRenderState);
		if (statueBatRenderState.isResting) {
			this.applyHeadRotation(statueBatRenderState.yRot);
		}

		this.animate(statueBatRenderState.flyAnimationState, BatAnimation.BAT_FLYING, statueBatRenderState.ageInTicks, 1.0F);
		this.animate(statueBatRenderState.restAnimationState, BatAnimation.BAT_RESTING, statueBatRenderState.ageInTicks, 1.0F);
	}

	private void applyHeadRotation(float headRotation) {
		this.head.yRot = headRotation * (float) (Math.PI / 180.0);
	}
}
