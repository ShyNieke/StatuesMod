package com.svennieke.statues.renderer;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.util.SkinUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class PlayerStatueRenderer extends TileEntitySpecialRenderer<PlayerStatueTileEntity>{	
    public static PlayerStatueRenderer instance;
	
	public static final ModelPlayer model = new ModelPlayer(0.03125F, false);
	
	public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super.setRendererDispatcher(rendererDispatcherIn);
        instance = this;
    }
	
	@Override
	public void render(PlayerStatueTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
	{
		EnumFacing enumfacing = EnumFacing.UP;
		
		this.renderPlayer(te, x, y, z, te.getPlayerProfile(), destroyStage, enumfacing);
	}
	
	public void renderPlayer(PlayerStatueTileEntity te, double x, double y, double z, @Nullable GameProfile profile, int destroyStage, EnumFacing enumfacing) 
	{
		ResourceLocation skinlocation = DefaultPlayerSkin.getDefaultSkinLegacy();
		
		ModelPlayer theModel = this.model;
		boolean slimModel = false;
		IBlockState state;
		 
		if (te == null) {
            profile = null;
            enumfacing = EnumFacing.UP;
		}
		else {
            profile = te.getPlayerProfile();
            state = te.getWorld().getBlockState(te.getPos());
            enumfacing = (state.getValue(BlockPlayer_Statue.FACING));
		}
		
        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
        	if (skinlocation != null)
            {
        		if (profile != null)
                {
        			skinlocation = SkinUtil.getSkin(profile);
        			slimModel = SkinUtil.isSlimSkin(profile.getId());
                }
        		
            	this.bindTexture(skinlocation);
    		}
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        
        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
        float f = 0.9995F;
        GlStateManager.scale(0.9995F, 0.9995F, 0.9995F);
        GlStateManager.translate(0.0F, -1.0F, 0.0F);
        
        switch (enumfacing)
        {
            case DOWN:
            case UP:
            default:
            	GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(-180.0F, 0.0F, 180.0F, 0.0F);
                break;
            case NORTH:
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
        
        if (skinlocation != null)
        {
        	this.bindTexture(skinlocation);
		}
        
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        
        float scale = 0.03125F;

        if(slimModel)
        	theModel = new ModelPlayer(0.03125F, true);
        
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
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        
        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        } 
	}
}
