package com.shynieke.statues.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.PlayerStatueModel;
import com.shynieke.statues.client.state.PlayerStatueRenderState;
import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jspecify.annotations.Nullable;

public class PlayerStatueRenderer extends LivingEntityRenderer<PlayerStatue, PlayerStatueRenderState, PlayerStatueModel> {
	private PlayerSkinRenderCache playerSkinRenderCache;
	private final PlayerStatueModel playerModel;
	private final PlayerStatueModel slimPlayerModel;
	public static final PlayerSkin DEFAULT_SKIN = DefaultPlayerSkin.get(Mannequin.DEFAULT_PROFILE.partialProfile());
	public boolean isSlim = false;


	public PlayerStatueRenderer(EntityRendererProvider.Context context, boolean slim) {
		super(context, new PlayerStatueModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), slim), 0.0F);
		this.playerModel = new PlayerStatueModel(context.bakeLayer(ClientHandler.PLAYER_STATUE), false);
		this.slimPlayerModel = new PlayerStatueModel(context.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), true);
		this.addLayer(
				new HumanoidArmorLayer<>(
						this,
						ArmorModelSet.bake(
								slim ? ModelLayers.PLAYER_SLIM_ARMOR : ModelLayers.PLAYER_ARMOR,
								context.getModelSet(),
								model -> new PlayerStatueModel(model, slim)
						),
						context.getEquipmentRenderer()
				)
		);
		this.addLayer(new ItemInHandLayer<>(this));
		this.addLayer(new WingsLayer<>(this, context.getModelSet(), context.getEquipmentRenderer()));
		this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getPlayerSkinRenderCache()));
	}

	public PlayerStatueRenderer(EntityRendererProvider.Context context) {
		this(context, false);
		this.playerSkinRenderCache = context.getPlayerSkinRenderCache();
	}

	@Override
	public PlayerStatueRenderState createRenderState() {
		return new PlayerStatueRenderState();
	}

	@Override
	public void extractRenderState(PlayerStatue statue, PlayerStatueRenderState statueRenderState, float partialTick) {
		super.extractRenderState(statue, statueRenderState, partialTick);
		HumanoidMobRenderer.extractHumanoidRenderState(statue, statueRenderState, partialTick, this.itemModelResolver);
		statueRenderState.yRot = Mth.rotLerp(partialTick, statue.yRotO, statue.getYRot());
		statueRenderState.isSmall = statue.isSmall();
		statueRenderState.bodyPose = statue.getBodyPose();
		statueRenderState.headPose = statue.getHeadPose();
		statueRenderState.leftArmPose = statue.getLeftArmPose();
		statueRenderState.rightArmPose = statue.getRightArmPose();
		statueRenderState.leftLegPose = statue.getLeftLegPose();
		statueRenderState.rightLegPose = statue.getRightLegPose();
		statueRenderState.wiggle = (float) (statue.level().getGameTime() - statue.punchCooldown) + partialTick;

		statueRenderState.skin = getSkin(statue.getResolvableProfile().orElse(null));
		statueRenderState.id = statue.getId();
		statueRenderState.name = statue.getResolvableProfile().flatMap(ResolvableProfile::name).orElse("unknown");
		statueRenderState.clientLock = statue.clientLock;
		statueRenderState.yOffset = statue.getYOffsetData();
		statueRenderState.supporter = isSupporter(statue);
		statueRenderState.upsideDown = isPlayerUpsideDown(statue);
	}

	private PlayerSkin getSkin(@Nullable ResolvableProfile profile) {
		if (profile == null) {
			return DEFAULT_SKIN;
		} else {
			return playerSkinRenderCache.getOrDefault(profile).playerSkin();
		}
	}

	@Override
	public Identifier getTextureLocation(PlayerStatueRenderState statueRenderState) {
		return statueRenderState.skin.body().texturePath();
	}

	@Override
	public void submit(PlayerStatueRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
		this.isSlim = state.skin != null && state.skin.model() == PlayerModelType.SLIM;
		this.model = isSlim ? this.slimPlayerModel : playerModel;
		poseStack.translate(0, state.yOffset, 0);
		if (state.clientLock > 0) {
			state.xRot = state.bodyRot;
		}
		super.submit(state, poseStack, submitNodeCollector, camera);
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
		if (playerStatue.getResolvableProfile().isPresent()) {
			ResolvableProfile profile = playerStatue.getResolvableProfile().get();
			String s = ChatFormatting.stripFormatting(profile.name().orElse("steve"));
			return "Dinnerbone".equals(s) || "Grumm".equals(s);
		}

		return false;
	}

	public static boolean isSupporter(PlayerStatue playerStatue) {
		if (playerStatue.getResolvableProfile().isPresent()) {
			ResolvableProfile resolvableProfile = playerStatue.getResolvableProfile().get();
			return ClientHandler.SUPPORTER.contains(resolvableProfile.partialProfile().id());
		}

		return false;
	}
}
