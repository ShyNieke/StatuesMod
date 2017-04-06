package com.svennieke.statues.handler;

import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.init.StatuesConfig;
import com.svennieke.statues.init.StatuesItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropHandler {

	public static double random_drop;
    public static int dropped;
	
	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		if (event.getEntity() instanceof EntityStatueBat) {
            	ItemStack itemStackToDrop = new ItemStack(StatuesItems.core, 1);
            	event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, 
            			
            	event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
		}
	}
}
