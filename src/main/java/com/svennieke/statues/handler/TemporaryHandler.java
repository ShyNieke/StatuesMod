package com.svennieke.statues.handler;

import com.svennieke.statues.Reference;
import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class TemporaryHandler {
	 public static final String RECEIVED_ANNIVERARY_GIFT = Reference.MOD_PREFIX + "RECEIVED_ANNIVERARY_GIFT";
	 
	 @SubscribeEvent
	 public void worldLoad(PlayerLoggedInEvent event)
	 {
		 EntityPlayer player = event.player;
		 System.out.println("hey");
		 System.out.println(event.player.getName());
		 if(!player.world.isRemote && player.getName().equals("etho"))
		 {
			 if(!player.getEntityData().hasKey(RECEIVED_ANNIVERARY_GIFT) || !player.getEntityData().getBoolean(RECEIVED_ANNIVERARY_GIFT))
			 {
				 player.getEntityData().setBoolean(RECEIVED_ANNIVERARY_GIFT, true);
				 ItemStack gift = new ItemStack(StatuesBlocks.campfire_statue[0]);
				 if(player.inventory.addItemStackToInventory(gift))
				 {
					 player.entityDropItem(gift, 0.5F);
					 player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your gift is laying on the floor about to despawn."));
				 }
			 }
		 }
	 }
	 
	 @SubscribeEvent
	 public void onPlayerRespawn(PlayerRespawnEvent event) 
	 {
		 EntityPlayer player = event.player;
		 if(player.getName().equals("etho"))
		 {
			 player.getEntityData().setBoolean(RECEIVED_ANNIVERARY_GIFT, true);
		 }
	 }
}
