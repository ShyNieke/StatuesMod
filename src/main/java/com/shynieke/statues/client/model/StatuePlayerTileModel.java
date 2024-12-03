package com.shynieke.statues.client.model;

import com.shynieke.statues.client.model.state.PlayerStatueRenderState;
import net.minecraft.client.model.geom.ModelPart;

public class StatuePlayerTileModel extends PlayerStatueModel {
	public StatuePlayerTileModel(ModelPart part, boolean slim) {
		super(part, slim);
		this.hat.setPos(0.0F, -1.75F, 0.0F);
		this.rightSleeve.setPos(-5.0F, 2.0F, 0.0F);
	}

	@Override
	public void setupAnim(PlayerStatueRenderState renderState) {
		super.setupAnim(renderState);
		this.setAllVisible(true);
		this.hat.visible = true;
		this.jacket.visible = true;
		this.leftPants.visible = true;
		this.rightPants.visible = true;
		this.leftSleeve.visible = true;
		this.rightSleeve.visible = true;
	}
}
