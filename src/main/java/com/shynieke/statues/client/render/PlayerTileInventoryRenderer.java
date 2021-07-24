package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.model.StatuePlayerTileModel;
import com.shynieke.statues.tiles.PlayerBlockEntity;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTileInventoryRenderer extends BlockEntityWithoutLevelRenderer {
    private StatuePlayerTileModel model;
    private StatuePlayerTileModel slimModel;

    public PlayerTileInventoryRenderer() {
        super(null, null);
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft != null) {
            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            if (resourceManager instanceof ReloadableResourceManager) {
                ((ReloadableResourceManager) resourceManager).registerReloadListener(this);
            }
        }
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        modelSet.onResourceManagerReload(manager);
        if(modelSet != null) {
            this.model = new StatuePlayerTileModel(modelSet.bakeLayer(ClientHandler.PLAYER_STATUE), false);
            this.slimModel = new StatuePlayerTileModel(modelSet.bakeLayer(ClientHandler.PLAYER_STATUE_SLIM), false);
        }
    }

    @Override
    public void renderByItem(ItemStack stack, TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        renderPlayerItem(stack, poseStack, bufferSource, combinedLight);
    }

    private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

    public void renderPlayerItem(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        poseStack.pushPose();
        if(stack != null) {
            GameProfile gameprofile = null;

            if(stack.hasCustomHoverName()) {
                String stackName = stack.getHoverName().getContents().toLowerCase();
                boolean validFlag = !stackName.isEmpty() && !stackName.contains(" ");

                if (validFlag) {
                    if (GAMEPROFILE_CACHE.containsKey(stackName))
                        gameprofile = GAMEPROFILE_CACHE.get(stackName);

                    if (stack.hasTag() && gameprofile == null) {
                        CompoundTag compoundtag = stack.getTag();
                        if (compoundtag.contains("PlayerProfile", 10)) {
                            GameProfile foundProfile = NbtUtils.readGameProfile(compoundtag.getCompound("PlayerProfile"));
                            if(foundProfile != null) {
                                GAMEPROFILE_CACHE.put(foundProfile.getName().toLowerCase(), foundProfile);
                            }
                            if(foundProfile.getName().equalsIgnoreCase(stackName)) {
                                gameprofile = foundProfile;
                            }
                        } else if (compoundtag.contains("PlayerProfile", 8) && !StringUtils.isBlank(compoundtag.getString("PlayerProfile"))) {
                            GameProfile gameprofile1 = new GameProfile((UUID)null, compoundtag.getString("PlayerProfile"));
                            compoundtag.remove("PlayerProfile");
                            PlayerBlockEntity.updateGameprofile(gameprofile1, (profile) -> {
                                compoundtag.put("PlayerProfile", NbtUtils.writeGameProfile(new CompoundTag(), profile));
                                if(profile != null) {
                                    GAMEPROFILE_CACHE.put(profile.getName().toLowerCase(), profile);
                                }
                            });
                            gameprofile = GAMEPROFILE_CACHE.get(stackName);
                        }
                    }

                    if(gameprofile == null) {
                        GameProfile gameprofile1 = new GameProfile((UUID)null, stackName);
                        PlayerBlockEntity.updateGameprofile(gameprofile1, (profile) -> {
                            if(profile != null) {
                                GAMEPROFILE_CACHE.put(profile.getName().toLowerCase(), profile);
                            }
                        });
                    }
                } else {
                    if (GAMEPROFILE_CACHE.containsKey("steve"))
                        gameprofile = GAMEPROFILE_CACHE.get("steve");

                    if(gameprofile == null) {
                        GameProfile gameprofile1 = new GameProfile((UUID)null, "steve");
                        PlayerBlockEntity.updateGameprofile(gameprofile1, (profile) -> {
                            if(profile != null) {
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
        VertexConsumer vertexConsumer = bufferSource.getBuffer(PlayerTileRenderer.getRenderType(gameprofile));
        if(flag) {
            if(slimModel != null) {
                slimModel.renderToBuffer(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        } else {
            if(model != null) {
                model.renderToBuffer(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}