package com.shynieke.statues.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.blockentities.StatueTableBlockEntity;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;

public class StatueTableBER implements BlockEntityRenderer<StatueTableBlockEntity> {
	public StatueTableBER(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(StatueTableBlockEntity tableBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource,
					   int combinedLightIn, int combinedOverlayIn) {
		if (tableBlockEntity.getHandler() != null) {
			IItemHandler handler = tableBlockEntity.getHandler();
			final BlockState state = tableBlockEntity.getBlockState();
			final Direction direction = state.getValue(AbstractBaseBlock.FACING);

			poseStack.pushPose();
			switch (direction) {
				default -> {
					//Nothing
				}
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
			}
			Minecraft mc = Minecraft.getInstance();
			Level level = mc.level;
			ItemStack statueStack = handler.getStackInSlot(0);
			if (!statueStack.isEmpty()) {
				float f = (float) tableBlockEntity.time + partialTicks;

				poseStack.pushPose();
				poseStack.translate(0.5, (1.0 / 16) * 9, 0.5);
				poseStack.translate(0.0D, (double) (0.1F + Mth.sin(f * 0.2F) * 0.01F), 0.0D);
				Minecraft.getInstance().getItemRenderer().renderStatic(statueStack, ItemDisplayContext.GROUND,
						combinedLightIn, combinedOverlayIn, poseStack, bufferSource, level, 0);
				poseStack.popPose();
			}

			ItemStack coreStack = handler.getStackInSlot(1);
			if (!statueStack.isEmpty()) {
				poseStack.pushPose();
				poseStack.translate((1.0 / 16) * 8.75, (1.0 / 16) * 2.5, (1.0 / 16) * 13);
				poseStack.scale(0.3125F, 0.3125F, 0.3125F);
				Minecraft.getInstance().getItemRenderer().renderStatic(coreStack, ItemDisplayContext.GROUND,
						combinedLightIn, combinedOverlayIn, poseStack, bufferSource, level, 0);
				poseStack.popPose();
			}

			for (int i = 2; i < 6; i++) {
				ItemStack catalystSTack = handler.getStackInSlot(i);
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
				if (!statueStack.isEmpty()) {
					poseStack.pushPose();
					poseStack.translate(0.5 + xOffset, 0.0625 * 9 + yOffset, 0.5 + zOffset);
					poseStack.scale(0.0625f * 2, 0.0625f * 2, 0.0625f * 2);
					Minecraft.getInstance().getItemRenderer().renderStatic(catalystSTack, ItemDisplayContext.GUI,
							combinedLightIn, combinedOverlayIn, poseStack, bufferSource, level, 0);
					poseStack.popPose();
				}
			}
			poseStack.popPose();
		}
	}
}
