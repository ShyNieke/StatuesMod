package com.svennieke.statues.renderer;

import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class PlayerStatueRenderer extends TileEntitySpecialRenderer<PlayerStatueTileEntity>{
	public static final ResourceLocation Steve = new ResourceLocation("textures/entity/steve.png");
	public static final ResourceLocation Bysco = new ResourceLocation("statues:textures/entity/mrbysco.png");
	
	public static final ModelPlayer model = new ModelPlayer(0.03125F, false);

	
	public PlayerStatueRenderer() {
		super();
	}
	
	public void setSkinResourceLocation() {

	}
	
	@Override
	public void render(PlayerStatueTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
	{
		EnumFacing enumfacing = EnumFacing.UP;
		
		if (te.hasWorld())
        {
            IBlockState iblockstate = this.getWorld().getBlockState(te.getPos());

            if (iblockstate.getBlock() instanceof BlockPlayer_Statue)
            {
                enumfacing = (EnumFacing)iblockstate.getValue(BlockPlayer_Statue.FACING);
            }
        }
		
		GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        
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
            this.bindTexture(Steve);
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
		
        if (destroyStage < 0)
        {
            //GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        }
        
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
                //GlStateManager.rotate(180.0F, 0.0F, 0.0F, 0.0F);
                break;
            case SOUTH:
            	GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(0.0F, 0.0F, 90.0F, 0.0F);
                break;
            case WEST:
            	GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(-90.0F, 0.0F, -90.0F, 0.0F);
                //GlStateManager.rotate(-90.0F, 90.0F, 0.0F, 1.0F);
                break;
            case EAST:
            	GlStateManager.translate(0F, 0.75F, 0F);
                GlStateManager.rotate(-90.0F, 0.0F, 90.0F, 0.0F);
                //GlStateManager.rotate(90.0F, 0.0F, 0.0F, 0.0F);
        }
        this.bindTexture(Bysco);
        
        this.model.bipedBody.render(0.03125F);
        this.model.bipedHead.render(0.03125F);
        this.model.bipedLeftArm.render(0.03125F);
        this.model.bipedRightArm.render(0.03125F);
        this.model.bipedLeftLeg.render(0.03125F);
        this.model.bipedRightLeg.render(0.03125F);

        GlStateManager.enableBlend();
        this.model.bipedBodyWear.render(0.03125F);
        this.model.bipedHeadwear.render(0.03125F);
        this.model.bipedLeftArmwear.render(0.03125F);
        this.model.bipedRightArmwear.render(0.03125F);
        this.model.bipedLeftLegwear.render(0.03125F);
        this.model.bipedRightLegwear.render(0.03125F);
        GlStateManager.disableBlend();
        
        /*
        this.modelPlayer.e1.render(0.0625F);
        this.modelPlayer.e2.render(0.0625F);
        this.modelPlayer.e3.render(0.0625F);
        this.modelPlayer.e4.render(0.0625F);
        this.modelPlayer.e5.render(0.0625F);
        this.modelPlayer.e6.render(0.0625F);
        this.modelPlayer.e7.render(0.0625F);
        this.modelPlayer.e8.render(0.0625F);
        this.modelPlayer.e9.render(0.0625F);
        this.modelPlayer.e10.render(0.0625F);
        this.modelPlayer.e11.render(0.0625F);
        this.modelPlayer.e12.render(0.0625F);
        */
        
        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        
        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }    
	}
}
