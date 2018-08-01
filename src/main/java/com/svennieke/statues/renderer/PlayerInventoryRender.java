package com.svennieke.statues.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
	
@SideOnly(Side.CLIENT)
public class PlayerInventoryRender extends TileEntityItemStackRenderer{
	public static final ModelPlayer model = new ModelPlayer(0.03125F, false);
    protected TileEntityRendererDispatcher rendererDispatcher;
	private static final Map<String, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();

	public PlayerInventoryRender() {
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		super.renderByItem(itemStackIn);
	}
	
	@Override
	public void renderByItem(ItemStack stack, float partialTicks) {
		GameProfile gameprofile = null;
		
		if (GAMEPROFILE_CACHE.containsKey(stack.getDisplayName())) 
			gameprofile = GAMEPROFILE_CACHE.get(stack.getDisplayName());
		
		if (stack.hasTagCompound() && gameprofile == null)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound.hasKey("PlayerProfile", 10))
            {
                gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("PlayerProfile"));
            }
            else if (nbttagcompound.hasKey("PlayerProfile", 8) && !StringUtils.isBlank(nbttagcompound.getString("PlayerProfile")))
            {
                GameProfile gameprofile1 = new GameProfile((UUID)null, nbttagcompound.getString("PlayerProfile"));
                gameprofile = TileEntitySkull.updateGameprofile(gameprofile1);
                nbttagcompound.removeTag("PlayerProfile");
                nbttagcompound.setTag("PlayerProfile", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
                GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
            }
        }
		if(gameprofile == null && !StringUtils.isBlank(stack.getDisplayName()) && !stack.getDisplayName().equals("Player Statue") && !stack.getDisplayName().equals(" "))
		{
			GameProfile gameprofile1 = new GameProfile((UUID)null, stack.getDisplayName());
            gameprofile = TileEntitySkull.updateGameprofile(gameprofile1);
            GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
		}
		
        if (TileEntitySkullRenderer.instance != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            PlayerStatueRenderer.instance.renderPlayerItem(gameprofile);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
	}
}
