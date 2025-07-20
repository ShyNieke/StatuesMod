package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class PlayerSpecialRenderer implements SpecialModelRenderer<ResolvableProfile> {
	private final StatuePlayerTileModel model;
	private final StatuePlayerTileModel slimModel;
	public boolean isSlim = false;

	public PlayerSpecialRenderer(StatuePlayerTileModel model, StatuePlayerTileModel slimModel) {
		this.model = model;
		this.slimModel = slimModel;
	}

	@Override
	public void render(@Nullable ResolvableProfile resolvableProfile, ItemDisplayContext displayContext,
	                   PoseStack poseStack, MultiBufferSource bufferSource,
	                   int packedLight, int packedOverlay, boolean hasFoilType) {
		SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
		if (resolvableProfile != null && isSlim != skinmanager.getInsecureSkin(resolvableProfile.gameProfile()).model().id().equals("slim"))
			isSlim = !isSlim;
		StatuePlayerTileModel playerModel = isSlim ? slimModel : model;

		PlayerBlockRenderer.renderPlayerStatue(null, resolvableProfile, playerModel, poseStack, bufferSource, packedLight, packedOverlay);
	}

	@Override
	public void getExtents(Set<Vector3f> p_428562_) {
		PoseStack posestack = new PoseStack();
		posestack.scale(0.375F, 0.375F, 0.375F);
		posestack.translate(1D, 0D, 0.75D);
		this.model.root().getExtentsForGui(posestack, p_428562_);
		this.slimModel.root().getExtentsForGui(posestack, p_428562_);
	}

	private static final Map<String, ResolvableProfile> GAMEPROFILE_CACHE = new HashMap<>();

	@Nullable
	public ResolvableProfile extractArgument(ItemStack stack) {
		ResolvableProfile gameprofile = null;

		if (stack.has(DataComponents.CUSTOM_NAME)) {
			String stackName = stack.getHoverName().getString().toLowerCase(Locale.ROOT);
			boolean validFlag = !stackName.isEmpty() && !stackName.contains(" ");

			if (validFlag) {
				if (GAMEPROFILE_CACHE.containsKey(stackName)) gameprofile = GAMEPROFILE_CACHE.get(stackName);

				if (!stack.has(DataComponents.PROFILE)) {
					stack.set(DataComponents.PROFILE, gameprofile);
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
					PlayerBlockEntity.fetchGameProfile("steve").thenAccept((profile) -> {
						if (profile.isPresent()) {
							GameProfile profile1 = profile.orElse(new GameProfile(Util.NIL_UUID, "steve"));
							GAMEPROFILE_CACHE.put(profile1.getName().toLowerCase(), new ResolvableProfile(profile1));
						}
					});
				}
			}
		}
		return gameprofile;
	}

	@OnlyIn(Dist.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final Unbaked INSTANCE = new Unbaked();
		public static MapCodec<Unbaked> CODEC = MapCodec.unit(INSTANCE).stable();

		@Override
		public MapCodec<PlayerSpecialRenderer.Unbaked> type() {
			return CODEC;
		}

		@Nullable
		@Override
		public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
			StatuePlayerTileModel model = new StatuePlayerTileModel(entityModelSet.bakeLayer(ClientHandler.PLAYER_STATUE), false);
			StatuePlayerTileModel slimModel = new StatuePlayerTileModel(entityModelSet.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), true);
			return new PlayerSpecialRenderer(model, slimModel);
		}
	}
}