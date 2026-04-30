package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class PlayerSpecialRenderer implements SpecialModelRenderer<PlayerSkinRenderCache.RenderInfo> {
	private final PlayerSkinRenderCache playerSkinRenderCache;
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;

	public PlayerSpecialRenderer(PlayerSkinRenderCache playerSkinRenderCache,
	                             StatuePlayerTileModel model, StatuePlayerTileModel slimModel) {
		this.playerSkinRenderCache = playerSkinRenderCache;
		this.model = model;
		this.slimModel = slimModel;
	}

	@Override
	public void submit(@Nullable PlayerSkinRenderCache.RenderInfo argument, PoseStack poseStack,
	                   SubmitNodeCollector nodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
		final boolean isSlim = argument == null || argument.playerSkin().model() == PlayerModelType.SLIM;
		final StatuePlayerTileModel playerModel = isSlim ? slimModel : model;

		poseStack.pushPose();
		transform(poseStack);
		RenderType rendertype = argument != null ? argument.renderType() : PlayerSkinRenderCache.DEFAULT_PLAYER_SKIN_RENDER_TYPE;
		GameProfile gameProfile = argument != null ? argument.gameProfile() : new GameProfile(Util.NIL_UUID, "Steve");
		PlayerBlockRenderer.submitPlayerStatue(nodeCollector, null, gameProfile, playerModel,
				poseStack, rendertype, lightCoords, null);
		poseStack.popPose();
	}

	@Override
	public void getExtents(Consumer<Vector3fc> output) {
		PoseStack posestack = new PoseStack();
		transform(posestack);
		this.model.root().getExtentsForGui(posestack, output);
		this.slimModel.root().getExtentsForGui(posestack, output);
	}

	private static void transform(PoseStack poseStack) {
		poseStack.scale(0.375F, 0.375F, 0.375F);
		poseStack.translate(1D, 0D, 0.75D);
	}

	public PlayerSkinRenderCache.RenderInfo extractArgument(ItemStack stack) {
		ResolvableProfile profile = stack.get(DataComponents.PROFILE);
		return profile == null ? null : this.playerSkinRenderCache.getOrDefault(profile);
	}

	public record Unbaked() implements SpecialModelRenderer.Unbaked<PlayerSkinRenderCache.RenderInfo> {
		public static final Unbaked INSTANCE = new Unbaked();
		public static final MapCodec<PlayerSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

		@NotNull
		@Override
		public MapCodec<PlayerSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@NotNull
		@Override
		public SpecialModelRenderer<PlayerSkinRenderCache.RenderInfo> bake(SpecialModelRenderer.BakingContext context) {
			final EntityModelSet entityModelSet = context.entityModelSet();
			StatuePlayerTileModel model = new StatuePlayerTileModel(entityModelSet.bakeLayer(ClientHandler.PLAYER_STATUE), false);
			StatuePlayerTileModel slimModel = new StatuePlayerTileModel(entityModelSet.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), true);
			return new PlayerSpecialRenderer(context.playerSkinRenderCache(), model, slimModel);
		}
	}
}