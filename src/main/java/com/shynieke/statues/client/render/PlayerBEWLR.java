package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlayerBEWLR extends BlockEntityWithoutLevelRenderer {
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;
	public boolean isSlim = false;

	public PlayerBEWLR(BlockEntityRendererProvider.Context context) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		this.model = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), false);
		this.slimModel = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), false);
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		renderPlayerItem(stack, poseStack, bufferSource, combinedLight);
	}

	private static final Map<String, ResolvableProfile> GAMEPROFILE_CACHE = new HashMap<>();

	public void renderPlayerItem(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
		poseStack.pushPose();
		if (stack != null) {
			ResolvableProfile gameprofile = null;

			if (stack.has(DataComponents.CUSTOM_NAME)) {
				String stackName = stack.getHoverName().getString().toLowerCase(Locale.ROOT);
				boolean validFlag = !stackName.isEmpty() && !stackName.contains(" ");

				if (validFlag) {
					if (!stack.has(DataComponents.PROFILE)) {
						if (GAMEPROFILE_CACHE.containsKey(stackName)) gameprofile = GAMEPROFILE_CACHE.get(stackName);
						stack.set(DataComponents.PROFILE, gameprofile);
					} else {
						gameprofile = stack.get(DataComponents.PROFILE);
					}
					if (stack.has(DataComponents.PROFILE) && gameprofile == null) {
						ResolvableProfile resolvableProfile = stack.get(DataComponents.PROFILE);
						if (resolvableProfile != null && !resolvableProfile.isResolved()) {
							stack.remove(DataComponents.PROFILE);
							PlayerBlockEntity.resolve(resolvableProfile).thenAcceptAsync(profile ->
									stack.set(DataComponents.PROFILE, profile), Minecraft.getInstance());
						}
					}

					if (gameprofile == null) {
						PlayerBlockEntity.fetchGameProfile(stackName).thenAccept((profile) -> {
							if (profile.isPresent()) {
								GameProfile profile1 = profile.orElse(new GameProfile(Util.NIL_UUID, stackName));
								ResolvableProfile resolvableProfile = new ResolvableProfile(profile1);
								stack.set(DataComponents.PROFILE, resolvableProfile);
								GAMEPROFILE_CACHE.put(profile1.getName().toLowerCase(), resolvableProfile);
							}
						});
					}
				} else {
					if (GAMEPROFILE_CACHE.containsKey("steve")) gameprofile = GAMEPROFILE_CACHE.get("steve");

					if (gameprofile == null) {
						Statues.LOGGER.error("Could not find profile for {}, defaulting to steve!", stackName);
						PlayerBlockEntity.fetchGameProfile("steve").thenAccept((profile) -> {
							if (profile.isPresent()) {
								GameProfile profile1 = profile.orElse(new GameProfile(Util.NIL_UUID, "steve"));
								GAMEPROFILE_CACHE.put(profile1.getName().toLowerCase(), new ResolvableProfile(profile1));
							}
						});
					}
				}
			}

			poseStack.translate(0.5D, 1.4D, 0.5D);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			renderItem(gameprofile, poseStack, bufferSource, combinedLight);
		}
		poseStack.popPose();
	}

	public void renderItem(ResolvableProfile resolvableProfile, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
		SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
		if (resolvableProfile != null && isSlim != skinmanager.getInsecureSkin(resolvableProfile.gameProfile()).model().id().equals("slim"))
			isSlim = !isSlim;

		VertexConsumer vertexConsumer = bufferSource.getBuffer(PlayerBER.getRenderType(resolvableProfile));
		boolean isSupporter = false;
		if (resolvableProfile != null) {
			final String s = ChatFormatting.stripFormatting(resolvableProfile.name().orElse("Steve"));
			if ("Dinnerbone".equalsIgnoreCase(s) || "Grumm".equalsIgnoreCase(s)) {
				poseStack.translate(0.0D, 1.85D, 0.0D);
				poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
			}
			isSupporter = ClientHandler.SUPPORTER.contains(resolvableProfile.id().orElse(Util.NIL_UUID));
		}

		int light = isSupporter ? 15728880 : combinedLight;
		if (isSlim) {
			if (slimModel != null) {
				slimModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
			}
		} else {
			if (model != null) {
				model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
			}
		}
	}
}