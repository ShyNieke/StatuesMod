package com.shynieke.statues.tiles.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.tiles.PlayerTile;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.PlayerModel;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PlayerTileRenderer extends TileEntityRenderer<PlayerTile>{
    public static final PlayerModel model = new PlayerModel(0.03125F, false);
    public static final PlayerModel slimModel = new PlayerModel(0.03125F, true);

    public static final ResourceLocation defaultTexture = DefaultPlayerSkin.getDefaultSkinLegacy();

    public PlayerTileRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(PlayerTile te, float partialTicks, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int p_225616_5_, int p_225616_6_) {
        BlockState blockstate = te.getBlockState();
        boolean flag = blockstate.getBlock() instanceof PlayerStatueBlock;
        Direction direction = flag ? blockstate.get(PlayerStatueBlock.HORIZONTAL_FACING) : Direction.UP;

        this.render(direction, te.getPlayerProfile(), matrix, typeBuffer, p_225616_6_);
    }

    public static void render(@Nullable Direction direction, @Nullable GameProfile profile, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int p_228879_7_) {
        PlayerModel playerModel = model;

        matrix.push();
        if (direction == null) {
            matrix.translate(0.5D, 0.0D, 0.5D);
        } else {
            switch(direction) {
                case NORTH:
                    matrix.translate(0.5D, 0.25D, (double)0.74F);
                    break;
                case SOUTH:
                    matrix.translate(0.5D, 0.25D, (double)0.26F);
                    break;
                case WEST:
                    matrix.translate((double)0.74F, 0.25D, 0.5D);
                    break;
                case EAST:
                default:
                    matrix.translate((double)0.26F, 0.25D, 0.5D);
            }
        }
        matrix.scale(-1.0F, -1.0F, 1.0F);
        matrix.translate(0.0D, -1.0D, 0.0D);
        IVertexBuilder ivertexbuilder = profile != null ? typeBuffer.getBuffer(getSkinFromProfile(profile)) : typeBuffer.getBuffer(RenderType.entityTranslucent(defaultTexture));
        if(profile != null && SkinUtil.isSlimSkin(profile.getId()) && playerModel != slimModel) {
            playerModel = slimModel;
        }

        playerModel.render(matrix, ivertexbuilder, p_228879_7_, OverlayTexture.DEFAULT_LIGHT, 1.0F, 1.0F, 1.0F, 1.0F);
        matrix.pop();
    }

    private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

    public static void renderPlayerItem(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int p_228364_4_, int p_228364_5_) {
        matrix.push();

        if(stack != null) {
            GameProfile gameprofile = null;

            if (GAMEPROFILE_CACHE.containsKey(stack.getDisplayName().getString()))
                gameprofile = GAMEPROFILE_CACHE.get(stack.getDisplayName().getString());

            if (stack.hasTag() && gameprofile == null)
            {
                CompoundNBT nbttagcompound = stack.getTag();
                if (nbttagcompound.contains("PlayerProfile", 10))
                {
                    gameprofile = NBTUtil.readGameProfile(nbttagcompound.getCompound("PlayerProfile"));
                }
                else if (nbttagcompound.contains("PlayerProfile", 8) && !StringUtils.isBlank(nbttagcompound.getString("PlayerProfile")))
                {
                    GameProfile gameprofile1 = new GameProfile(null, nbttagcompound.getString("PlayerProfile"));
                    gameprofile = PlayerTile.updateGameProfile(gameprofile1);
                    nbttagcompound.remove("PlayerProfile");
                    nbttagcompound.put("PlayerProfile", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
                    GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
                }
            }
            if(gameprofile == null && !StringUtils.isBlank(stack.getDisplayName().getString()) && !stack.getDisplayName().getString().equals("Player Statue") && !stack.getDisplayName().getString().equals(" "))
            {
                GameProfile gameprofile1 = new GameProfile(null, stack.getDisplayName().getString());
                gameprofile = PlayerTile.updateGameProfile(gameprofile1);
                GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
            }

            PlayerModel playerModel = model;
            if(gameprofile != null && SkinUtil.isSlimSkin(gameprofile.getId())) {
                playerModel = slimModel;
            }

            matrix.translate(0.5D, 1.25D, 0.5D);
            matrix.scale(-1.0F, -1.0F, 1.0F);

            IVertexBuilder ivertexbuilder;
            if(gameprofile != null) {
                ivertexbuilder = typeBuffer.getBuffer(getSkinFromProfile(gameprofile));
            } else {
                ivertexbuilder = typeBuffer.getBuffer(RenderType.entityTranslucent(defaultTexture));
            }

            if(gameprofile != null && SkinUtil.isSlimSkin(gameprofile.getId())) {
                playerModel = slimModel;
            }

            playerModel.render(matrix, ivertexbuilder, p_228364_5_, 0, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrix.pop();
    }

    private static RenderType getSkinFromProfile(@Nullable GameProfile p_228878_1_) {
        Minecraft minecraft = Minecraft.getInstance();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(p_228878_1_);
        return map.containsKey(Type.SKIN) ? RenderType.entityTranslucent(minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN)) : RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(p_228878_1_)));
    }
}
