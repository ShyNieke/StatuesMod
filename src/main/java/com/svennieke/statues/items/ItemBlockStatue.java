package com.svennieke.statues.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@InterfaceList({
	@Interface(iface="baubles.api.IBauble", modid="Baubles", striprefs=true),
	@Interface(iface="baubles.api.BaubleType", modid="Baubles", striprefs=true)})

public class ItemBlockStatue extends ItemBlock implements IBauble, IRenderBauble{

    public ItemBlockStatue(Block block) {
		super(block);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.HEAD;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
		if (type == RenderType.HEAD) {
	        {
	        	GlStateManager.pushMatrix();
	            Minecraft minecraft = Minecraft.getMinecraft();
	            //Helper.translateToHeadLevel(player);

	                GlStateManager.translate(0.0F, -0.25F, 0.0F);
	                GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
	                GlStateManager.scale(0.625F, -0.625F, -0.625F);

	                minecraft.getItemRenderer().renderItem(player, stack, ItemCameraTransforms.TransformType.HEAD);
	            GlStateManager.popMatrix();
	        }
		}
	}
}
