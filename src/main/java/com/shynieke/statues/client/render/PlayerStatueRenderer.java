package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.shynieke.statues.client.model.PlayerStatueModel;
import com.shynieke.statues.entity.PlayerStatueEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Map;

public class PlayerStatueRenderer extends LivingRenderer<PlayerStatueEntity, PlayerStatueModel> {
    public static final PlayerStatueModel model = new PlayerStatueModel(0.03125F, false);
    public static final PlayerStatueModel slimModel = new PlayerStatueModel(0.03125F, true);

    public PlayerStatueRenderer(EntityRendererManager renderManager) {
        this(renderManager, false);
    }

    public PlayerStatueRenderer(EntityRendererManager manager, boolean useSmallArms) {
        super(manager, new PlayerStatueModel(0.0F, useSmallArms), 0.0F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(PlayerStatueEntity playerStatue) {
        return playerStatue.getGameProfile()
                .map(this::getSkin)
                .orElseGet(() -> new ResourceLocation("minecraft:textures/entity/steve.png"));
    }

    private ResourceLocation getSkin(GameProfile gameProfile) {
        if (!gameProfile.isComplete()) {
            return new ResourceLocation("minecraft:textures/entity/steve.png");
        }
        else {
            final Minecraft minecraft = Minecraft.getInstance();
            SkinManager skinManager = minecraft.getSkinManager();
            final Map<Type, MinecraftProfileTexture> loadSkinFromCache = skinManager.loadSkinFromCache(gameProfile); // returned map may or may not be typed
            if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                return skinManager.loadSkin(loadSkinFromCache.get(Type.SKIN), Type.SKIN);
            }
            else {
                return DefaultPlayerSkin.getDefaultSkin(gameProfile.getId());
            }
        }
    }

    @Override
    public void render(PlayerStatueEntity playerStatue, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.entityModel = model;
        if(playerStatue.isSlim() && this.entityModel != slimModel) {
            this.entityModel = slimModel;
        }
        super.render(playerStatue, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected boolean canRenderName(PlayerStatueEntity playerStatue) {
        return playerStatue.isCustomNameVisible();
    }

    protected void applyRotations(PlayerStatueEntity playerStatue, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        float f = (float)(playerStatue.world.getGameTime() - playerStatue.punchCooldown) + partialTicks;
        if (f < 5.0F) {
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.sin(f / 1.5F * (float)Math.PI) * 3.0F));
        }
    }

    protected void preRenderCallback(PlayerStatueEntity playerStatue, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 0.9375F;
        matrixStackIn.scale(f, f, f);
    }
}
