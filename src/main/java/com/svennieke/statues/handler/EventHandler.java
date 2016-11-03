package com.svennieke.statues.handler;

import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		if(event.getEntity() instanceof EntitySlime) {
			ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.slime_statue, 1);
	        event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, 

	              event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
		}
	}
}
