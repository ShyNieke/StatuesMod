package com.shynieke.statues.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.Statues;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.PlayerStatueModel;
import com.shynieke.statues.client.model.state.PlayerStatueRenderState;
import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.Nullable;

public class PlayerStatueRenderer extends LivingEntityRenderer<PlayerStatue, PlayerStatueRenderState, PlayerStatueModel> {
	private final PlayerStatueModel playerModel;
	private final PlayerStatueModel slimPlayerModel;
	public static final PlayerSkin defaultSkin = DefaultPlayerSkin.getDefaultSkin();
	public boolean isSlim = false;

	public PlayerStatueRenderer(EntityRendererProvider.Context context) {
		this(context, false);
	}

	@Override
	public PlayerStatueRenderState createRenderState() {
		return new PlayerStatueRenderState();
	}

	@Override
	public void extractRenderState(PlayerStatue statue, PlayerStatueRenderState statueRenderState, float partialTick) {
		HumanoidMobRenderer.extractHumanoidRenderState(statue, statueRenderState, partialTick, this.itemModelResolver);
		statueRenderState.yRot = Mth.rotLerp(partialTick, statue.yRotO, statue.getYRot());
		statueRenderState.isSmall = statue.isSmall();
		statueRenderState.bodyPose = statue.getBodyRotation();
		statueRenderState.headPose = statue.getHeadRotation();
		statueRenderState.leftArmPose = statue.getLeftArmRotation();
		Statues.LOGGER.error("{} {} {}", statue.getLeftArmRotation().getWrappedX(), statue.getLeftArmRotation().getWrappedY(), statue.getLeftArmRotation().getWrappedZ());
		statueRenderState.rightArmPose = statue.getRightArmRotation();
		statueRenderState.leftLegPose = statue.getLeftLegRotation();
		statueRenderState.rightLegPose = statue.getRightLegRotation();
		statueRenderState.wiggle = (float)(statue.level().getGameTime() - statue.punchCooldown) + partialTick;

		statueRenderState.skin = getSkin(statue.getGameProfile().orElse(null));
		statueRenderState.id = statue.getId();
		statueRenderState.name = statue.getGameProfile().isPresent() ?
				statue.getGameProfile().get().gameProfile().getName() : "unknown";
		statueRenderState.clientLock = statue.clientLock;
		statueRenderState.yOffset = statue.getYOffsetData();
		statueRenderState.supporter = isSupporter(statue);
		statueRenderState.upsideDown = isPlayerUpsideDown(statue);
	}

	private PlayerSkin getSkin(@Nullable ResolvableProfile profile) {
		SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
		if (profile != null) {
			return skinmanager.getInsecureSkin(profile.gameProfile());
		} else {
			return defaultSkin;
		}
	}

	public PlayerStatueRenderer(EntityRendererProvider.Context context, boolean slim) {
		super(context, new PlayerStatueModel(context.bakeLayer(ModelLayers.PLAYER), slim), 0.0F);
		this.playerModel = new PlayerStatueModel(context.bakeLayer(ModelLayers.PLAYER), false);
		this.slimPlayerModel = new PlayerStatueModel(context.bakeLayer(ModelLayers.PLAYER_SLIM), true);
		this.addLayer(
				new HumanoidArmorLayer<>(
						this,
						new HumanoidArmorModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)),
						new HumanoidArmorModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)),
						context.getEquipmentRenderer()
				)
		);
		this.addLayer(new ItemInHandLayer<>(this));
		this.addLayer(new WingsLayer<>(this, context.getModelSet(), context.getEquipmentRenderer()));
		this.addLayer(new CustomHeadLayer<>(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(PlayerStatueRenderState statueRenderState) {
		return statueRenderState.skin.texture();
	}

	@Override
	public void render(PlayerStatueRenderState statueRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
		this.isSlim = statueRenderState.skin != null && statueRenderState.skin.model() == Model.SLIM;
		this.model = isSlim ? this.slimPlayerModel : playerModel;
		poseStack.translate(0, statueRenderState.yOffset, 0);
		if (statueRenderState.clientLock > 0) {
			statueRenderState.xRot = statueRenderState.bodyRot;
		}
		super.render(statueRenderState, poseStack, bufferSource, statueRenderState.supporter ? 15728880 : packedLightIn);
	}

	@Override
	protected boolean shouldShowName(PlayerStatue statue, double offset) {
		return statue.isCustomNameVisible();
	}

	@Override
	protected void setupRotations(PlayerStatueRenderState statueRenderState, PoseStack poseStack, float partialTicks, float scale) {
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - partialTicks));
		if (statueRenderState.wiggle < 5.0F) {
			poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(statueRenderState.wiggle / 1.5F * (float) Math.PI) * 3.0F));
		}

		if (statueRenderState.upsideDown) {
			poseStack.translate(0.0D, (double) (statueRenderState.boundingBoxHeight + 0.1F), 0.0D);
			poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
		}
	}

	@Override
	protected void scale(PlayerStatueRenderState statueRenderState, PoseStack poseStack) {
		float f = 0.9375F;
		poseStack.scale(f, f, f);
	}

	public static boolean isPlayerUpsideDown(PlayerStatue playerStatue) {
		if (playerStatue.getGameProfile().isPresent()) {
			ResolvableProfile profile = playerStatue.getGameProfile().get();
			String s = ChatFormatting.stripFormatting(profile.name().orElse("steve"));
			return "Dinnerbone".equals(s) || "Grumm".equals(s);
		}

		return false;
	}

	public static boolean isSupporter(PlayerStatue playerStatue) {
		if (playerStatue.getGameProfile().isPresent()) {
			ResolvableProfile profile = playerStatue.getGameProfile().get();
			return ClientHandler.SUPPORTER.contains(profile.id().orElse(Util.NIL_UUID));
		}

		return false;
	}
}
