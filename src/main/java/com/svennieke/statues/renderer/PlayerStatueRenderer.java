package com.svennieke.statues.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.util.SkinUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelPlayer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class PlayerStatueRenderer extends TileEntityRenderer<PlayerStatueTileEntity>{	
    public static PlayerStatueRenderer instance;
	
	public static final ModelPlayer model = new ModelPlayer(0.03125F, false);
	
	@Override
	public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
		rendererDispatcher = rendererDispatcherIn;
		instance = this;
    }
	@Override
	public void render(PlayerStatueTileEntity te, double x, double y, double z, float partialTicks,
			int destroyStage) {
		EnumFacing enumfacing = EnumFacing.UP;
		
		this.renderPlayer(te, x, y, z, te.getPlayerProfile(), destroyStage, enumfacing);
	}

	private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();
	public void renderPlayerItem(ItemStack stack)
	{
		GameProfile gameprofile = null;
		
		if (GAMEPROFILE_CACHE.containsKey(stack.getDisplayName().getString())) 
			gameprofile = GAMEPROFILE_CACHE.get(stack.getDisplayName().getString());
		
		if (stack.hasTag() && gameprofile == null)
        {
            NBTTagCompound nbttagcompound = stack.getTag();
            if (nbttagcompound.contains("PlayerProfile", 10))
            {
                gameprofile = NBTUtil.readGameProfile(nbttagcompound.getCompound("PlayerProfile"));
            }
            else if (nbttagcompound.contains("PlayerProfile", 8) && !StringUtils.isBlank(nbttagcompound.getString("PlayerProfile")))
            {
                GameProfile gameprofile1 = new GameProfile((UUID)null, nbttagcompound.getString("PlayerProfile"));
                gameprofile = TileEntitySkull.updateGameProfile(gameprofile1);
                nbttagcompound.removeTag("PlayerProfile");
                nbttagcompound.setTag("PlayerProfile", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
                GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
            }
        }
		if(gameprofile == null && !StringUtils.isBlank(stack.getDisplayName().getString()) && !stack.getDisplayName().getString().equals("Player Statue") && !stack.getDisplayName().getString().equals(" "))
		{
			GameProfile gameprofile1 = new GameProfile((UUID)null, stack.getDisplayName().getString());
            gameprofile = TileEntitySkull.updateGameProfile(gameprofile1);
            GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
		}
		
		ResourceLocation skinlocation = DefaultPlayerSkin.getDefaultSkinLegacy();
		
		ModelPlayer theModel = PlayerStatueRenderer.model;
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
                UUID uuid = EntityPlayer.getUUID(gameprofile);
                skinlocation = DefaultPlayerSkin.getDefaultSkin(uuid);
            }
            
			slimModel = SkinUtil.isSlimSkin(gameprofile.getId());
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
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        float scale = 0.03125F;
        if(slimModel)
        	theModel = new ModelPlayer(scale, true);
        
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
	
	public void renderPlayer(PlayerStatueTileEntity te, double x, double y, double z, @Nullable GameProfile profile, int destroyStage, EnumFacing enumfacing) 
	{
//		ResourceLocation skinlocation = DefaultPlayerSkin.getDefaultSkinLegacy();
		
		ModelPlayer theModel = PlayerStatueRenderer.model;
		boolean slimModel = false;
		IBlockState state;
//		boolean renderHeadWear = true;
//		boolean renderChestWear = true;
//		boolean renderLeftArmWear = true;
//		boolean renderRightArmWear = true;
//		boolean renderLeftLegWear = true;
//		boolean renderRightLegWear = true;
		 
		if (te == null) {
            profile = null;
            enumfacing = EnumFacing.UP;
		}
		else {
            profile = te.getPlayerProfile();
            state = te.getWorld().getBlockState(te.getPos());
            if(state.getBlock() instanceof BlockHorizontal)
            	enumfacing = (state.get(BlockPlayer_Statue.HORIZONTAL_FACING));
            else
                enumfacing = EnumFacing.UP;
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
            case NORTH:
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
	            resourcelocation = DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getUUID(profile));
	         }
	      }

	      return resourcelocation;
	   }
}
