package com.svennieke.statues.renderer;

import javax.annotation.Nullable;

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
	public void renderTileEntityAt(PlayerStatueTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) 
	{
		EnumFacing enumfacing = EnumFacing.UP;
		this.renderPlayer(te, x, y, z, te.getName(), destroyStage, enumfacing);
	}

	public void renderPlayer(PlayerStatueTileEntity te, double x, double y, double z, String playerName, int destroyStage, EnumFacing enumfacing) 
	{
		ResourceLocation skinlocation = this.getSkinResourceLocation(playerName);
		
		if (te.hasWorldObj())
        {
            IBlockState iblockstate = this.getWorld().getBlockState(te.getPos());

            if (iblockstate.getBlock() instanceof BlockPlayer_Statue)
            {
                enumfacing = (EnumFacing)iblockstate.getValue(BlockPlayer_Statue.FACING);
            }
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
        
        this.model.bipedBody.render(scale);
        this.model.bipedHead.render(scale);
        this.model.bipedLeftArm.render(scale);
        this.model.bipedRightArm.render(scale);
        this.model.bipedLeftLeg.render(scale);
        this.model.bipedRightLeg.render(scale);

        this.model.bipedBodyWear.render(scale);
        this.model.bipedHeadwear.render(scale);
        this.model.bipedLeftArmwear.render(scale);
        this.model.bipedRightArmwear.render(scale);
        this.model.bipedRightArmwear.offsetZ = -0.3125F;
        this.model.bipedLeftLegwear.render(scale);
        this.model.bipedRightLegwear.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        
        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        } 
	}
	
	@Nullable
    private ResourceLocation getSkinResourceLocation(String name)
    {			
		if(name.equals("") || name.contains(" "))
		{
			final ResourceLocation Steve = DefaultPlayerSkin.getDefaultSkinLegacy();
			return Steve;
		}
		else
		{
			return SkinUtil.getSkinTexture(name);
		}
    }
}
