package com.svennieke.statues.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.svennieke.statues.init.StatuesBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@InterfaceList({
	@Interface(iface="baubles.api.IBauble", modid="baubles", striprefs=true),
	@Interface(iface="baubles.api.BaubleType", modid="baubles", striprefs=true),
	@Interface(iface="baubles.api.render.IRenderBauble", modid="baubles", striprefs=true)})

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
	        	
				Helper.translateToHeadLevel(player);
				Helper.translateToFace();
				GlStateManager.translate(0.0F, 2.975F, 1.275F);
				GlStateManager.scale(0.625F, 0.625F, 0.625F);

				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.HEAD);
				
				GlStateManager.popMatrix();
	        }
		}
	}
	
	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EntityEquipmentSlot.HEAD;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		if (target.world.isRemote)
        {
            return false;
        }
		if (target instanceof EntityMob && this.getBlock() == StatuesBlocks.sombrero)
        {
            EntityMob mob = (EntityMob)target;
            ItemStack singleCopy = stack.copy();
            singleCopy.setCount(1);
            
            if(mob.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
            {
            	mob.setItemStackToSlot(EntityEquipmentSlot.HEAD, singleCopy);
            	mob.setDropChance(EntityEquipmentSlot.HEAD, 1.0F);
                stack.shrink(1);
            }

            return true;
        }
        return false;
	}
}
