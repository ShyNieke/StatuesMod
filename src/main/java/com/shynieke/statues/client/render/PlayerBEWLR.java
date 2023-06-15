package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class PlayerBEWLR extends BlockEntityWithoutLevelRenderer {
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;

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
					if (GAMEPROFILE_CACHE.containsKey(stackName))
						gameprofile = GAMEPROFILE_CACHE.get(stackName);

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
							GameProfile gameprofile1 = new GameProfile((UUID) null, compoundtag.getString("PlayerProfile"));
							compoundtag.remove("PlayerProfile");
							PlayerBlockEntity.updateGameprofile(gameprofile1, (profile) -> {
								compoundtag.put("PlayerProfile", NbtUtils.writeGameProfile(new CompoundTag(), profile));
								if (profile != null) {
									GAMEPROFILE_CACHE.put(profile.getName().toLowerCase(), profile);
								}
							});
							gameprofile = GAMEPROFILE_CACHE.get(stackName);
						}
					}

					if (gameprofile == null) {
						GameProfile gameprofile1 = new GameProfile((UUID) null, stackName);
						PlayerBlockEntity.updateGameprofile(gameprofile1, (profile) -> {
							if (profile != null) {
								GAMEPROFILE_CACHE.put(profile.getName().toLowerCase(), profile);
							}
						});
					}
				} else {
					if (GAMEPROFILE_CACHE.containsKey("steve"))
						gameprofile = GAMEPROFILE_CACHE.get("steve");

					if (gameprofile == null) {
						GameProfile gameprofile1 = new GameProfile((UUID) null, "steve");
						PlayerBlockEntity.updateGameprofile(gameprofile1, (profile) -> {
							if (profile != null) {
								GAMEPROFILE_CACHE.put(profile.getName(), profile);
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
		boolean flag = gameprofile != null && gameprofile.isComplete() && SkinUtil.isSlimSkin(gameprofile.getId());
		VertexConsumer vertexConsumer = bufferSource.getBuffer(PlayerBER.getRenderType(gameprofile));
		boolean isSupporter = false;
		if (gameprofile != null) {
			final String s = ChatFormatting.stripFormatting(gameprofile.getName());
			if ("Dinnerbone".equalsIgnoreCase(s) || "Grumm".equalsIgnoreCase(s)) {
				poseStack.translate(0.0D, (double) (1.85F), 0.0D);
				poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
			}
			isSupporter = ClientHandler.SUPPORTER.contains(gameprofile.getId());
		}

		int light = isSupporter ? 15728880 : combinedLight;
		if (flag) {
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