package com.svennieke.statues.handler;

import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootHandler {
	@SubscribeEvent
	public void onItemFished(ItemFishedEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.world;
		if(!world.isRemote)
		{
			if(!(event.getEntityPlayer() instanceof FakePlayer))
			{
				if(event.getDrops().size() >= 1)
				{
					for(int i = 0; i < event.getDrops().size(); i++)
					{
						if(event.getDrops().get(i) != null)
							event.getDrops().remove(i);
					}
				}
				
				ItemStack itemStackToDrop = new ItemStack(StatuesBlocks.pufferfish_statue, 1);
				DropLootStatues(player, itemStackToDrop, event);
			}
		}
	}
	
	public void DropLootStatues(Entity entity, ItemStack itemStackToDrop, ItemFishedEvent event) {
		double random_drop;
		
		random_drop = Math.random();
		if ( random_drop < StatuesConfigGen.general.OldDropChance )
        {
        	event.getDrops().add(itemStackToDrop);
        }
	}
}
