package com.svennieke.statues.handler;

import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
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
			double default_drop_chance = StatuesConfigGen.general.OldDropChance;

			if(!(player instanceof FakePlayer))
			{
				EntityPlayerMP realPlayer = (EntityPlayerMP)event.getEntityPlayer();
				String[] LuckyPlayers = StatuesConfigGen.luckyplayers.lucky_players;
				
				if(LuckyPlayers.length != 0)
				{
					for (int i = 0; (i < LuckyPlayers.length) && (LuckyPlayers[i] != null); i++) {
						if(realPlayer.getName().equals(LuckyPlayers[i]));
						{
							default_drop_chance = StatuesConfigGen.general.OldDropChance / 4;
						}
					}
				}
				else
				{
					default_drop_chance = StatuesConfigGen.general.OldDropChance;
				}
				
				if ( Math.random() <= default_drop_chance )
		        {
					EntityFishHook hook = event.getHookEntity();
					if(hook != null)
					{
						EntityItem entityitem = new EntityItem(hook.world, hook.posX, hook.posY, hook.posZ, new ItemStack(StatuesBlocks.pufferfish_statue[0]));
	                    double d0 = hook.getAngler().posX - hook.posX;
	                    double d1 = hook.getAngler().posY - hook.posY;
	                    double d2 = hook.getAngler().posZ - hook.posZ;
	                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	                    double d4 = 0.1D;
	                    entityitem.motionX = d0 * 0.1D;
	                    entityitem.motionY = d1 * 0.1D + (double)MathHelper.sqrt(d3) * 0.08D;
	                    entityitem.motionZ = d2 * 0.1D;
	                    hook.world.spawnEntity(entityitem);
					}
					
					event.setCanceled(true);
		        }
			}
		}
	}
}
