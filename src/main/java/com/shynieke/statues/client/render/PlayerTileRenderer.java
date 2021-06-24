package com.shynieke.statues.client.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.client.StatuePlayerModel;
import com.shynieke.statues.tiles.PlayerTile;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class PlayerTileRenderer extends TileEntityRenderer<PlayerTile>{
    public static final StatuePlayerModel model = new StatuePlayerModel(0.03125F, false);
    public static final StatuePlayerModel slimModel = new StatuePlayerModel(0.03125F, true);

    public static final ResourceLocation defaultTexture = DefaultPlayerSkin.getDefaultSkinLegacy();

    public PlayerTileRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(PlayerTile te, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = te.getBlockState();
        boolean flag = blockstate.getBlock() instanceof PlayerStatueBlock;
        Direction direction = flag ? blockstate.get(PlayerStatueBlock.HORIZONTAL_FACING) : Direction.UP;
        GameProfile profile = te.getPlayerProfile();

        render(direction, profile, matrixStackIn, bufferIn, combinedLightIn);
    }

    public static void render(@Nullable Direction direction, @Nullable GameProfile profile, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int combinedLight) {
        StatuePlayerModel playerModel = model;

        matrix.translate(0.5D, 0.25D, 0.5D);
        matrix.push();
        if (direction != null) {
           switch(direction) {
                case NORTH:
                    break;
                case SOUTH:
                    matrix.rotate(Vector3f.YP.rotationDegrees(180));
                    break;
                case WEST:
                    matrix.rotate(Vector3f.YP.rotationDegrees(90));
                    break;
                default:
                    matrix.rotate(Vector3f.YP.rotationDegrees(270));
            }
        }
        matrix.scale(-1.0F, -1.0F, 1.0F);
        matrix.translate(0.0D, -1.25D, 0.0D);
        boolean flag = profile != null && profile.isComplete() && SkinUtil.isSlimSkin(profile.getId());
        if(flag && playerModel != slimModel) {
            playerModel = slimModel;
        }

        IVertexBuilder ivertexbuilder = typeBuffer.getBuffer(getRenderType(profile));
        playerModel.render(matrix, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrix.pop();
    }

    private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

    public static void renderPlayerItem(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int combinedLight) {
        matrix.push();

        if(stack != null) {
            GameProfile gameprofile = null;

            if(stack.hasDisplayName()) {
                String stackName = stack.getDisplayName().getUnformattedComponentText().toLowerCase();
                boolean spaceFlag = stackName.contains(" ");
                boolean emptyFlag = stackName.isEmpty();

                if (!spaceFlag && !emptyFlag) {
                    if (GAMEPROFILE_CACHE.containsKey(stackName))
                        gameprofile = GAMEPROFILE_CACHE.get(stackName);

                    if (stack.hasTag() && gameprofile == null) {
                        CompoundNBT tag = stack.getTag();
                        if (tag.contains("PlayerProfile", 10)) {
                            GameProfile foundProfile = NBTUtil.readGameProfile(tag.getCompound("PlayerProfile"));
                            if(foundProfile != null) {
                                GAMEPROFILE_CACHE.put(foundProfile.getName().toLowerCase(), foundProfile);
                            }
                            if(foundProfile.getName().equalsIgnoreCase(stackName)) {
                                gameprofile = foundProfile;
                            }
                        } else if (tag.contains("PlayerProfile", 8) && !StringUtils.isBlank(tag.getString("PlayerProfile"))) {
                            GameProfile gameprofile1 = new GameProfile((UUID) null, tag.getString("PlayerProfile"));
                            GameProfile foundProfile = PlayerTile.updateGameProfile(gameprofile1);
                            tag.remove("PlayerProfile");
                            tag.put("PlayerProfile", NBTUtil.writeGameProfile(new CompoundNBT(), foundProfile));
                            if(foundProfile != null) {
                                GAMEPROFILE_CACHE.put(foundProfile.getName().toLowerCase(), foundProfile);
                            }
                            if(foundProfile.getName().equalsIgnoreCase(stackName)) {
                                gameprofile = foundProfile;
                            }
                        }
                    }

                    if(gameprofile == null) {
                        GameProfile gameprofile1 = new GameProfile((UUID)null, stackName);
                        gameprofile1 = PlayerTile.updateGameProfile(gameprofile1);
                        if(gameprofile1 != null) {
                            gameprofile = gameprofile1;
                            GAMEPROFILE_CACHE.put(gameprofile1.getName().toLowerCase(), gameprofile1);
                        }
                    }
                } else {
                    if (GAMEPROFILE_CACHE.containsKey("steve"))
                        gameprofile = GAMEPROFILE_CACHE.get("steve");

                    if(gameprofile == null) {
                        GameProfile gameprofile1 = new GameProfile((UUID)null, "steve");
                        gameprofile = PlayerTile.updateGameProfile(gameprofile1);
                        if(gameprofile != null) {
                            GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
                        }
                    }
                }
            }

            matrix.translate(0.5D, 1.4D, 0.5D);
            matrix.scale(-1.0F, -1.0F, 1.0F);

            StatuePlayerModel playerModel = model;
            boolean flag = gameprofile != null && gameprofile.isComplete() && SkinUtil.isSlimSkin(gameprofile.getId());
            if(flag && playerModel != slimModel) {
                playerModel = slimModel;
            }

            IVertexBuilder ivertexbuilder = typeBuffer.getBuffer(getRenderType(gameprofile));
            playerModel.render(matrix, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrix.pop();
    }

    private static RenderType getRenderType(@Nullable GameProfile gameProfileIn) {
        if (gameProfileIn == null || !gameProfileIn.isComplete()) {
            return RenderType.getEntityCutoutNoCull(defaultTexture);
        } else {
            final Minecraft minecraft = Minecraft.getInstance();
            final Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(gameProfileIn);
            if (map.containsKey(Type.SKIN)) {
                return RenderType.getEntityTranslucent(minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN));
            } else {
                return RenderType.getEntityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(gameProfileIn)));
            }
        }
    }
}
