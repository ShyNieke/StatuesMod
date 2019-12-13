package com.shynieke.statues.tiles.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.Profile;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.tiles.PlayerTile;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class PlayerTileRenderer extends TileEntityRenderer<PlayerTile>{
    public static PlayerTileRenderer instance;

    public static final PlayerModel model = new PlayerModel(0.03125F, false);
    public static final PlayerModel slimModel = new PlayerModel(0.03125F, true);

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        rendererDispatcher = rendererDispatcherIn;
        instance = this;
    }

    @Override
    public void render(PlayerTile te, double x, double y, double z, float partialTicks,
                       int destroyStage) {
        Direction direction = Direction.UP;
        this.renderPlayer(te, x, y, z, te.getPlayerProfile(), destroyStage, direction);
    }

    private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

    public void renderPlayerItem(ItemStack stack)
    {
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
                    gameprofile = SkullTileEntity.updateGameProfile(gameprofile1);
                    nbttagcompound.remove("PlayerProfile");
                    nbttagcompound.put("PlayerProfile", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
                    GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
                }
            }
            if(gameprofile == null && !StringUtils.isBlank(stack.getDisplayName().getString()) && !stack.getDisplayName().getString().equals("Player Statue") && !stack.getDisplayName().getString().equals(" "))
            {
                GameProfile gameprofile1 = new GameProfile(null, stack.getDisplayName().getString());
                gameprofile = SkullTileEntity.updateGameProfile(gameprofile1);
                GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
            }

            ResourceLocation skinlocation = DefaultPlayerSkin.getDefaultSkinLegacy();

            PlayerModel theModel = PlayerTileRenderer.model;
            boolean slimModel = false;
            if (gameprofile != null)
            {
                Minecraft minecraft = Minecraft.getInstance();
                Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(gameprofile);

                if (map.containsKey(Type.SKIN))
                {
                    skinlocation = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
                }
                else
                {
                    UUID uuid = PlayerEntity.getUUID(gameprofile);
                    skinlocation = DefaultPlayerSkin.getDefaultSkin(uuid);
                }

                slimModel = gameprofile.getId() != null ? SkinUtil.isSlimSkin(gameprofile.getId()) : false;
            }

            Minecraft.getInstance().getTextureManager().bindTexture(skinlocation);

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();

            GlStateManager.translatef((float)0 + 0.5F, (float)0 + 1.5F, (float)0 + 0.5F);
            GlStateManager.scalef(1.0F, -1.0F, -1.0F);
            GlStateManager.translatef(0.0F, 1.0F, 0.0F);
//        float f = 0.9995F;
            GlStateManager.scalef(0.9995F, 0.9995F, 0.9995F);
            GlStateManager.translatef(0.0F, -1.0F, 0.0F);

            GlStateManager.translatef(0F, 0.75F, 0F);
            GlStateManager.rotatef(-180.0F, 0.0F, 180.0F, 0.0F);

            GlStateManager.enableRescaleNormal();
            GlStateManager.enableAlphaTest();
            GlStateManager.setProfile(Profile.PLAYER_SKIN);
            if(slimModel)
                theModel = this.slimModel;

            theModel.bipedBody.render(scale);
            theModel.bipedHead.render(scale);
            theModel.bipedLeftArm.render(scale);
            theModel.bipedRightArm.render(scale);
            theModel.bipedLeftLeg.render(scale);
            theModel.bipedRightLeg.render(scale);

            theModel.bipedBodyWear.render(scale);
            theModel.bipedHeadwear.render(scale);
            theModel.bipedLeftArmwear.render(scale);
            theModel.bipedRightArmwear.offsetZ = -0.3125F;
            theModel.bipedRightArmwear.render(scale);
            theModel.bipedLeftLegwear.render(scale);
            theModel.bipedRightLegwear.render(scale);
            GlStateManager.popMatrix();
        }
    }

    private void renderPlayer(PlayerTile te, double x, double y, double z, @Nullable GameProfile profile, int destroyStage, Direction enumfacing)
    {
//		ResourceLocation skinlocation = DefaultPlayerSkin.getDefaultSkinLegacy();

        PlayerModel theModel = PlayerTileRenderer.model;
        boolean slimModel = false;
        BlockState state;
//		boolean renderHeadWear = true;
//		boolean renderChestWear = true;
//		boolean renderLeftArmWear = true;
//		boolean renderRightArmWear = true;
//		boolean renderLeftLegWear = true;
//		boolean renderRightLegWear = true;

        if (te == null) {
            profile = null;
            enumfacing = Direction.UP;
        }
        else {
            profile = te.getPlayerProfile();
            if(te.getWorld().getBlockState(te.getPos()) != null) {
                state = te.getWorld().getBlockState(te.getPos());
                if(state.getBlock() instanceof HorizontalBlock)
                    enumfacing = (state.get(PlayerStatueBlock.HORIZONTAL_FACING));
                else
                    enumfacing = Direction.UP;
            } else {
                enumfacing = Direction.UP;
            }
        }

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(4.0F, 4.0F, 1.0F);
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else {
            this.bindTexture(this.bindPlayerSkin(profile));
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        GlStateManager.translatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        GlStateManager.translatef(0.0F, 1.0F, 0.0F);
//        float f = 0.9995F;
        GlStateManager.scalef(0.9995F, 0.9995F, 0.9995F);
        GlStateManager.translatef(0.0F, -1.0F, 0.0F);

        switch (enumfacing)
        {
            case DOWN:
            case UP:
            default:
                GlStateManager.translatef(0F, 0.75F, 0F);
                GlStateManager.rotatef(-180.0F, 0.0F, 180.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.translatef(0F, 0.75F, 0F);
                GlStateManager.rotatef(0.0F, 0.0F, 90.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.translatef(0F, 0.75F, 0F);
                GlStateManager.rotatef(-90.0F, 0.0F, -90.0F, 0.0F);
                break;
            case EAST:
                GlStateManager.translatef(0F, 0.75F, 0F);
                GlStateManager.rotatef(-90.0F, 0.0F, 90.0F, 0.0F);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlphaTest();
        GlStateManager.setProfile(Profile.PLAYER_SKIN);

        if(slimModel)
            theModel = this.slimModel;

        theModel.bipedBody.render(scale);
        theModel.bipedHead.render(scale);
        theModel.bipedLeftArm.render(scale);
        theModel.bipedRightArm.render(scale);
        theModel.bipedLeftLeg.render(scale);
        theModel.bipedRightLeg.render(scale);

        theModel.bipedBodyWear.render(scale);
        theModel.bipedHeadwear.render(scale);
        theModel.bipedLeftArmwear.render(scale);
        theModel.bipedRightArmwear.offsetZ = -0.3125F;
        theModel.bipedRightArmwear.render(scale);
        theModel.bipedLeftLegwear.render(scale);
        theModel.bipedRightLegwear.render(scale);
        GlStateManager.popMatrix();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    private ResourceLocation bindPlayerSkin(@Nullable GameProfile profile) {
        ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
        if (profile != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);
            if (map.containsKey(Type.SKIN)) {
                resourcelocation = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
            } else {
                resourcelocation = DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(profile));
            }
        }

        return resourcelocation;
    }
}
