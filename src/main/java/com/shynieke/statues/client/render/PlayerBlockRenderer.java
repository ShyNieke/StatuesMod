package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import com.shynieke.statues.client.state.PlayerStatueRenderState;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class PlayerBlockRenderer implements BlockEntityRenderer<PlayerBlockEntity, PlayerBlockRenderState> {
	private final PlayerSkinRenderCache playerSkinRenderCache;
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;

	public static final Identifier defaultTexture = DefaultPlayerSkin.getDefaultTexture();

	public PlayerBlockRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), false);
		this.slimModel = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), true);
		this.playerSkinRenderCache = context.playerSkinRenderCache();
	}

	@Override
	public PlayerBlockRenderState createRenderState() {
		return new PlayerBlockRenderState();
	}

	@Override
	public void extractRenderState(PlayerBlockEntity blockEntity, PlayerBlockRenderState renderState,
	                               float partialTick, Vec3 cameraPosition,
	                               ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
		renderState.profile = blockEntity.getPlayerProfile();

		if (renderState.profile != null) {
			SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
			Supplier<PlayerSkin> skinSupplier = skinmanager.createLookup(renderState.profile.partialProfile(), false);
			renderState.skin = playerSkinRenderCache.getOrDefault(renderState.profile).playerSkin();
			renderState.isSlim = skinSupplier.get().model().getSerializedName().equals("slim");
			renderState.renderType = this.getRenderType(renderState.profile);
		}
	}

	@Override
	public void submit(PlayerBlockRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		final boolean flag = renderState.blockState.getBlock() instanceof PlayerStatueBlock;
		final Direction direction = flag ? renderState.blockState.getValue(PlayerStatueBlock.FACING) : Direction.UP;
		final StatuePlayerTileModel playerModel = renderState.isSlim ? slimModel : model;
		final ResolvableProfile resolvableProfile = renderState.profile;
		final RenderType renderType = renderState.renderType;

		poseStack.pushPose();
		poseStack.scale(0.5625F, 0.5625F, 0.5625F);
		poseStack.translate(0.375F, 0.0F, 0.375F);
		submitPlayerStatue(nodeCollector, direction, resolvableProfile.partialProfile(), playerModel, poseStack, renderType, renderState.lightCoords, renderState.breakProgress);
		poseStack.popPose();
	}

	public static void submitPlayerStatue(SubmitNodeCollector nodeCollector, @Nullable Direction direction,
	                                      @Nullable GameProfile profile, StatuePlayerTileModel playerModel,
	                                      PoseStack poseStack, RenderType renderType, int combinedLight,
	                                      ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		poseStack.translate(0.5D, 0.25D, 0.5D);
		poseStack.pushPose();
		if (direction != null) {
			switch (direction) {
				case NORTH:
					break;
				case SOUTH:
					poseStack.mulPose(Axis.YP.rotationDegrees(180));
					break;
				case WEST:
					poseStack.mulPose(Axis.YP.rotationDegrees(90));
					break;
				default:
					poseStack.mulPose(Axis.YP.rotationDegrees(270));
			}
		}
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0D, -1.25D, 0.0D);

		boolean isSupporter = false;
		if (profile != null) {
			final String s = ChatFormatting.stripFormatting(profile.name());
			if ("Dinnerbone".equalsIgnoreCase(s) || "Grumm".equalsIgnoreCase(s)) {
				poseStack.translate(0.0D, (double) (1.85F), 0.0D);
				poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
			}
			isSupporter = ClientHandler.SUPPORTER.contains(profile.id());
		}

		int light = isSupporter ? 15728880 : combinedLight;
		PlayerStatueRenderState renderState = new PlayerStatueRenderState();
		playerModel.setupAnim(renderState);
		nodeCollector.submitModel(playerModel, renderState, poseStack, renderType,
				light, OverlayTexture.NO_OVERLAY, 0, crumblingOverlay);
		poseStack.popPose();
	}

	public RenderType getRenderType(@Nullable ResolvableProfile resolvableProfile) {
		if (resolvableProfile == null)
			return RenderTypes.entityTranslucent(defaultTexture);

		return playerSkinRenderCache.getOrDefault(resolvableProfile).renderType();
	}
}
