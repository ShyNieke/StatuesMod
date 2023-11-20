package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlayerBEWLR extends BlockEntityWithoutLevelRenderer {
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;
	public static boolean isSlim = false;

	public PlayerBEWLR(BlockEntityRendererProvider.Context context) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		this.model = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), false);
		this.slimModel = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), false);
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		renderPlayerItem(stack, poseStack, bufferSource, combinedLight);
	}

	private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

	public void renderPlayerItem(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
		poseStack.pushPose();
		if (stack != null) {
			GameProfile gameprofile = null;

			if (stack.hasCustomHoverName()) {
				String stackName = stack.getHoverName().getString().toLowerCase(Locale.ROOT);
				boolean validFlag = !stackName.isEmpty() && !stackName.contains(" ");

				if (validFlag) {
					if (GAMEPROFILE_CACHE.containsKey(stackName)) gameprofile = GAMEPROFILE_CACHE.get(stackName);

					if (stack.hasTag() && gameprofile == null) {
						CompoundTag compoundtag = stack.getTag();
						if (compoundtag.contains("PlayerProfile", 10)) {
							GameProfile foundProfile = NbtUtils.readGameProfile(compoundtag.getCompound("PlayerProfile"));
							if (foundProfile != null) {
								GAMEPROFILE_CACHE.put(foundProfile.getName().toLowerCase(), foundProfile);
							}
							if (foundProfile.getName().equalsIgnoreCase(stackName)) {
								gameprofile = foundProfile;
							}
						} else if (compoundtag.contains("PlayerProfile", 8) && !StringUtils.isBlank(compoundtag.getString("PlayerProfile"))) {
							String playerProfile = compoundtag.getString("PlayerProfile");
							compoundtag.remove("PlayerProfile");

							PlayerBlockEntity.fetchGameProfile(playerProfile).thenAccept((profile) -> {
								if (profile.isPresent()) {
									GameProfile profile1 = profile.orElse(new GameProfile(Util.NIL_UUID, playerProfile));
									GAMEPROFILE_CACHE.put(profile1.getName().toLowerCase(), profile1);
								}
							});
							gameprofile = GAMEPROFILE_CACHE.get(stackName);
						}
					}

					if (gameprofile == null) {
						PlayerBlockEntity.fetchGameProfile(stackName).thenAccept((profile) -> {
							if (profile.isPresent()) {
								GameProfile profile1 = profile.orElse(new GameProfile(Util.NIL_UUID, stackName));
								GAMEPROFILE_CACHE.put(profile1.getName().toLowerCase(), profile1);
							}
						});
					}
				} else {
					if (GAMEPROFILE_CACHE.containsKey("steve")) gameprofile = GAMEPROFILE_CACHE.get("steve");

					if (gameprofile == null) {
						PlayerBlockEntity.fetchGameProfile("steve").thenAccept((profile) -> {
							if (profile.isPresent()) {
								GameProfile profile1 = profile.orElse(new GameProfile(Util.NIL_UUID, "steve"));
								GAMEPROFILE_CACHE.put(profile1.getName().toLowerCase(), profile1);
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

	public void renderItem(GameProfile gameprofile, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
		SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
		if (gameprofile != null && isSlim != skinmanager.getInsecureSkin(gameprofile).model().id().equals("slim"))
			isSlim = !isSlim;

		VertexConsumer vertexConsumer = bufferSource.getBuffer(PlayerBER.getRenderType(gameprofile));
		boolean isSupporter = false;
		if (gameprofile != null) {
			final String s = ChatFormatting.stripFormatting(gameprofile.getName());
			if ("Dinnerbone".equalsIgnoreCase(s) || "Grumm".equalsIgnoreCase(s)) {
				poseStack.translate(0.0D, 1.85D, 0.0D);
				poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
			}
			isSupporter = ClientHandler.SUPPORTER.contains(gameprofile.getId());
		}

		int light = isSupporter ? 15728880 : combinedLight;
		if (isSlim) {
			if (slimModel != null) {
				slimModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
		} else {
			if (model != null) {
				model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}
}