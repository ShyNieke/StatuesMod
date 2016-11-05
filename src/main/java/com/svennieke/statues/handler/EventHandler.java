package com.svennieke.statues.handler;

import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesConfig;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	public static double random_drop;
    public static int dropped;
	
	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		if (event.getEntity() instanceof EntitySlime) {
			random_drop = Math.random();
            if ( random_drop < StatuesConfig.DropChance )
            {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.slime_statue, 1);
            	event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, 
            			
            		event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
            }
		}
		if (event.getEntity() instanceof EntityBlaze) {
			random_drop = Math.random();
            if ( random_drop < StatuesConfig.DropChance )
            {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.blaze_statue, 1);
            	event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, 
            			
            		event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
            }
		}
		if (event.getEntity() instanceof EntitySnowman) {
			random_drop = Math.random();
            if ( random_drop < StatuesConfig.DropChance )
            {
            	ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.snowgolem_statue, 1);
            	event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, 
            			
            		event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
            }
		}
	}
}
