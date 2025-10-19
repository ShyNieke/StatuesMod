package com.shynieke.statues.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.blockentities.StatueTableBlockEntity;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.client.state.StatueTableRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class StatueTableBER implements BlockEntityRenderer<StatueTableBlockEntity, StatueTableRenderState> {
	private final ItemModelResolver itemModelResolver;

	public StatueTableBER(BlockEntityRendererProvider.Context context) {
		this.itemModelResolver = context.itemModelResolver();
	}

	@Override
	public StatueTableRenderState createRenderState() {
		return new StatueTableRenderState();
	}

	@Override
	public void extractRenderState(StatueTableBlockEntity blockEntity, StatueTableRenderState renderState,
	                               float partialTick, Vec3 cameraPosition,
	                               @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
		renderState.timeAndPartial = blockEntity.time + partialTick;

		renderState.items = new ArrayList<>();

		ItemStackRenderState statueRenderState = new ItemStackRenderState();
		this.itemModelResolver
				.updateForTopItem(statueRenderState, blockEntity.getHandler().getResource(0).toStack(), ItemDisplayContext.GROUND, blockEntity.getLevel(), null, 0);
		renderState.items.add(statueRenderState);

		ItemStackRenderState coreRenderState = new ItemStackRenderState();
		this.itemModelResolver
				.updateForTopItem(coreRenderState, blockEntity.getHandler().getResource(1).toStack(), ItemDisplayContext.GROUND, blockEntity.getLevel(), null, 0);
		renderState.items.add(coreRenderState);

		for (int i = 2; i < 6; i++) {
			ItemStackRenderState catalystRenderState = new ItemStackRenderState();
			this.itemModelResolver
					.updateForTopItem(catalystRenderState, blockEntity.getHandler().getResource(i).toStack(), ItemDisplayContext.GUI, blockEntity.getLevel(), null, 0);
			renderState.items.add(catalystRenderState);
		}

	}

	@Override
	public void submit(StatueTableRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		final Direction direction = renderState.blockState.getValue(AbstractBaseBlock.FACING);

		poseStack.pushPose();
		switch (direction) {
			case EAST -> {
				poseStack.translate(1, 0, 0);
				poseStack.mulPose(Axis.YP.rotationDegrees(-90F));
			}
			case SOUTH -> {
				poseStack.translate(1, 0, 1);
				poseStack.mulPose(Axis.YP.rotationDegrees(180F));
			}
			case WEST -> {
				poseStack.translate(0, 0, 1);
				poseStack.mulPose(Axis.YP.rotationDegrees(90F));
			}
			default -> {
				//Nothing
			}
		}
		Minecraft mc = Minecraft.getInstance();
		Level level = mc.level;
		if (renderState.items.isEmpty()) {
			return;
		}
		if (!renderState.items.getFirst().isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5, (1.0 / 16) * 9, 0.5);
			poseStack.translate(0.0D, (double) (0.1F + Mth.sin(renderState.timeAndPartial * 0.2F) * 0.01F), 0.0D);
			renderState.items.getFirst().submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
			poseStack.popPose();
		}

		if (!renderState.items.get(1).isEmpty()) {
			poseStack.pushPose();
			poseStack.translate((1.0 / 16) * 8.75, (1.0 / 16) * 2.5, (1.0 / 16) * 13);
			poseStack.scale(0.3125F, 0.3125F, 0.3125F);
			renderState.items.get(1).submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
			poseStack.popPose();
		}

		for (int i = 2; i < 6; i++) {
			ItemStackRenderState catalystState = renderState.items.get(i);
			float xOffset = 0;
			float yOffset = 0;
			float zOffset = 0;
			switch (i) {
				default -> {
					xOffset = 0.0625f * 2;
					yOffset = 0;
					zOffset = 0.0625f * 2;
				}
				case 3 -> {
					xOffset = -0.0625f * 2;
					yOffset = 0;
					zOffset = 0.0625f * 2;
				}
				case 4 -> {
					xOffset = 0.0625f * 2;
					yOffset = 0;
					zOffset = -0.0625f * 2;
				}
				case 5 -> {
					xOffset = -0.0625f * 2;
					yOffset = 0;
					zOffset = -0.0625f * 2;
				}
			}
			if (!catalystState.isEmpty()) {
				poseStack.pushPose();
				poseStack.translate(0.5 + xOffset, 0.0625 * 9 + yOffset, 0.5 + zOffset);
				poseStack.scale(0.0625f * 2, 0.0625f * 2, 0.0625f * 2);
				catalystState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
				poseStack.popPose();
			}
		}
		poseStack.popPose();

	}
}
