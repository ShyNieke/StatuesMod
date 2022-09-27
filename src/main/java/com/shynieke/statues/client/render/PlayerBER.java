package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PlayerBER implements BlockEntityRenderer<PlayerBlockEntity> {
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;

	public static final ResourceLocation defaultTexture = DefaultPlayerSkin.getDefaultSkin();

	public PlayerBER(BlockEntityRendererProvider.Context context) {
		this.model = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), false);
		this.slimModel = new StatuePlayerTileModel(context.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), true);
	}

	@Override
	public void render(PlayerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
		BlockState blockstate = blockEntity.getBlockState();
		boolean flag = blockstate.getBlock() instanceof PlayerStatueBlock;
		Direction direction = flag ? blockstate.getValue(PlayerStatueBlock.FACING) : Direction.UP;
		GameProfile profile = blockEntity.getPlayerProfile();

		render(direction, profile, blockEntity.isSlim(), poseStack, bufferSource, combinedLightIn, partialTicks);
	}

	public void render(@Nullable Direction direction, @Nullable GameProfile profile, boolean isSlim, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, float partialTicks) {
		poseStack.translate(0.5D, 0.25D, 0.5D);
		poseStack.pushPose();
		if (direction != null) {
			switch (direction) {
				case NORTH:
					break;
				case SOUTH:
					poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
					break;
				case WEST:
					poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
					break;
				default:
					poseStack.mulPose(Vector3f.YP.rotationDegrees(270));
			}
		}
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0D, -1.25D, 0.0D);

		boolean isSupporter = false;
//		boolean isTranslator = false;
		if (profile != null) {
			final String s = ChatFormatting.stripFormatting(profile.getName());
			if ("Dinnerbone".equalsIgnoreCase(s) || "Grumm".equalsIgnoreCase(s)) {
				poseStack.translate(0.0D, (double) (1.85F), 0.0D);
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
			}
			isSupporter = ClientHandler.SUPPORTER.contains(profile.getId());
//			isTranslator = ClientHandler.TRANSLATORS.contains(profile.getId());
		}

		int light = isSupporter ? 15728880 : combinedLight;
		VertexConsumer vertexConsumer = bufferSource.getBuffer(getRenderType(profile));
		StatuePlayerTileModel playerModel = isSlim ? slimModel : model;

		//TODO: Implement Translator effect

		playerModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		poseStack.popPose();
	}

	public static RenderType getRenderType(@Nullable GameProfile gameProfileIn) {
		if (gameProfileIn == null || !gameProfileIn.isComplete()) {
			return RenderType.entityCutoutNoCull(defaultTexture);
		} else {
			final Minecraft minecraft = Minecraft.getInstance();
			final Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfileIn);
			if (map.containsKey(Type.SKIN)) {
				return RenderType.entityTranslucent(minecraft.getSkinManager().registerTexture((MinecraftProfileTexture) map.get(Type.SKIN), Type.SKIN));
			} else {
				return RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(gameProfileIn)));
			}
		}
	}
}
