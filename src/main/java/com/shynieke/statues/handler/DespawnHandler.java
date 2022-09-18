package com.shynieke.statues.handler;

import com.shynieke.statues.entity.fakeentity.IFakeEntity;
import com.shynieke.statues.init.StatuesItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DespawnHandler {
	@SubscribeEvent
	public void worldTick(PlayerInteractEvent.EntityInteract event) {
		if (!event.getWorld().isRemote) {
			Entity entity = event.getEntity();
			if (entity instanceof EntityCreature && entity instanceof IFakeEntity) {
				EntityCreature entityC = (EntityCreature) entity;
				ItemStack stack = event.getItemStack();

				if (stack.getItem() == StatuesItems.core) {
					entityC.enablePersistence();
					stack.shrink(1);
				}
			}
		}
	}
}
