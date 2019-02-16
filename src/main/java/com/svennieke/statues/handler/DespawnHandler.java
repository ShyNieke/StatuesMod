package com.svennieke.statues.handler;

import com.svennieke.statues.Reference;
import com.svennieke.statues.entity.fakeentity.IFakeEntity;
import com.svennieke.statues.init.StatuesItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DespawnHandler {
	@SubscribeEvent
	public static void worldTick(PlayerInteractEvent.EntityInteract event)
	{
		if(!event.getWorld().isRemote)
		{
			Entity entity = event.getEntity();
			if(entity instanceof EntityCreature && entity instanceof IFakeEntity)
			{
				EntityCreature entityC = (EntityCreature)entity;
				ItemStack stack = event.getItemStack();
				
				if(stack.getItem() == StatuesItems.core)
				{
					entityC.enablePersistence();
					stack.shrink(1);
				}
			}
		}
	}
}
