package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class PlayerBEWLR extends BlockEntityWithoutLevelRenderer {
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;

	public PlayerBEWLR(BlockEntityRendererProvider.Context context) {
		super(context.getBlockEntityRenderDispatcher(), context.getModelSet());
		this.model = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), false);
		this.slimModel = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), false);
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		renderPlayerItem(stack, poseStack, bufferSource, combinedLight);
	}

	public void renderPlayerItem(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
		poseStack.pushPose();
		if (stack != null) {
			GameProfile gameprofile = null;
			if (stack.hasTag() && gameprofile == null) {
				CompoundTag tag = stack.getTag();
				assert tag != null;
				if (tag.contains("PlayerProfile", 10)) {
					CompoundTag profileTag = tag.getCompound("PlayerProfile");
					gameprofile = NbtUtils.readGameProfile(profileTag);
				} else if (tag.contains("PlayerProfile", 8) && !Util.isBlank(tag.getString("PlayerProfile"))) {
					gameprofile = new GameProfile((UUID) null, tag.getString("PlayerProfile"));
					tag.remove("PlayerProfile");
					SkinUtil.updateGameProfile(gameprofile, (profile) ->
							tag.put("PlayerProfile", NbtUtils.writeGameProfile(new CompoundTag(), profile))
					);
				}
			}

			poseStack.translate(0.5D, 1.4D, 0.5D);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			renderItem(gameprofile, poseStack, bufferSource, combinedLight);
		}
		poseStack.popPose();
	}

	public void renderItem(GameProfile gameprofile, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
		boolean flag = gameprofile != null && SkinUtil.isSlimSkin(gameprofile.getId());
		VertexConsumer vertexConsumer = bufferSource.getBuffer(PlayerBER.getRenderType(gameprofile));
		boolean isSupporter = false;
		if (gameprofile != null && gameprofile.getName() != null) {
			final String s = ChatFormatting.stripFormatting(gameprofile.getName());
			if ("Dinnerbone".equalsIgnoreCase(s) || "Grumm".equalsIgnoreCase(s)) {
				poseStack.translate(0.0D, (double) (1.85F), 0.0D);
				poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
			}
			isSupporter = ClientHandler.SUPPORTER.contains(gameprofile.getId());
		}

		int light = isSupporter ? 15728880 : combinedLight;
		StatuePlayerTileModel renderedModel = flag ? slimModel : model;
		if (renderedModel != null) {
			renderedModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}