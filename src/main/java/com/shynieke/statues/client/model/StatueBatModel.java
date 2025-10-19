package com.shynieke.statues.client.model;

import com.shynieke.statues.client.state.StatueBatRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
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
	private final KeyframeAnimation flyingAnimation;
	private final KeyframeAnimation restingAnimation;

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
		this.flyingAnimation = BatAnimation.BAT_FLYING.bake(root);
		this.restingAnimation = BatAnimation.BAT_RESTING.bake(root);
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

		this.flyingAnimation.apply(statueBatRenderState.flyAnimationState, statueBatRenderState.ageInTicks);
		this.restingAnimation.apply(statueBatRenderState.restAnimationState, statueBatRenderState.ageInTicks);
	}

	private void applyHeadRotation(float headRotation) {
		this.head.yRot = headRotation * (float) (Math.PI / 180.0);
	}
}
