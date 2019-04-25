package com.svennieke.statues.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.util.SkinUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class PlayerStatueRenderer extends TileEntitySpecialRenderer<PlayerStatueTileEntity>{
    public static PlayerStatueRenderer instance;

    public static final ModelPlayer model = new ModelPlayer(0.03125F, false);

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        rendererDispatcher = rendererDispatcherIn;
        instance = this;
    }

    @Override
    public void render(PlayerStatueTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        EnumFacing facing = te == null ? EnumFacing.UP : te.getWorld().getBlockState(te.getPos()).getValue(BlockPlayer_Statue.FACING);
        this.renderPlayer(x, y, z, te.getPlayerProfile(), destroyStage, facing);
    }

    public void renderPlayer(double x, double y, double z, @Nullable GameProfile profile, int destroyStage, EnumFacing enumfacing)
    {
        ResourceLocation skinlocation = DefaultPlayerSkin.getDefaultSkinLegacy();

        ModelPlayer theModel = this.model;

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            if (profile != null)
            {
                Minecraft minecraft = Minecraft.getMinecraft();
                Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

                if (map.containsKey(Type.SKIN))
                {
                    skinlocation = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
                }
                else
                {
                    UUID uuid = EntityPlayer.getUUID(profile);
                    skinlocation = DefaultPlayerSkin.getDefaultSkin(uuid);
                }

                if (SkinUtil.isSlimSkin(profile.getId())) {
                    theModel = new ModelPlayer(0.03125F, true);
                }
            }

            this.bindTexture(skinlocation);
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);

        switch (enumfacing)
        {
            case DOWN:
            case UP:
            default:
                GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(-180.0F, 0.0F, 180.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(0.0F, 0.0F, 90.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(-90.0F, 0.0F, -90.0F, 0.0F);
                break;
            case EAST:
                GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(-90.0F, 0.0F, 90.0F, 0.0F);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);

        theModel.bipedBody.render(0.03125F);
        theModel.bipedHead.render(0.03125F);
        theModel.bipedLeftArm.render(0.03125F);
        theModel.bipedRightArm.render(0.03125F);
        theModel.bipedLeftLeg.render(0.03125F);
        theModel.bipedRightLeg.render(0.03125F);

        theModel.bipedBodyWear.render(0.03125F);
        theModel.bipedHeadwear.render(0.03125F);
        theModel.bipedLeftArmwear.render(0.03125F);
        theModel.bipedRightArmwear.offsetZ = -0.3125F;
        theModel.bipedRightArmwear.render(0.03125F);
        theModel.bipedLeftLegwear.render(0.03125F);
        theModel.bipedRightLegwear.render(0.03125F);
        GlStateManager.popMatrix();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}