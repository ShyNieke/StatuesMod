package com.svennieke.statues.handler;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.svennieke.statues.Reference;
import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.packets.StatuesAFKPacket;
import com.svennieke.statues.packets.StatuesPacketHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FishHandler {
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
				System.out.println(realPlayer.getEntityData().getBoolean(afkKey));
				if(!realPlayer.getEntityData().getBoolean(afkKey))
				{
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
	
	public static int tick;
	private int timeSinceKeyPressed;
	public static String afkKey = Reference.MOD_PREFIX + "afk";
	
	public static void updateAfk(EntityPlayer player, boolean afk) {
		if(player.world.playerEntities.size() != 1) {
			if(afk)
				player.getEntityData().setBoolean(afkKey, true);
			else 
				player.getEntityData().setBoolean(afkKey, false);
		}
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event) {
		World world = event.player.world;
		List<EntityPlayer> players = world.playerEntities;
		
        if(players.size() == 1) {
            EntityPlayer lastPlayer = players.get(0);
            lastPlayer.getEntityData().setBoolean(afkKey, false);
        }
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			timeSinceKeyPressed++;
			if(timeSinceKeyPressed >= 2400)
				StatuesPacketHandler.INSTANCE.sendToServer(new StatuesAFKPacket(true));
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeystroke(KeyInputEvent event) {
		onPress();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeystroke(GuiScreenEvent.KeyboardInputEvent.Pre event) {
		onPress();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onMousePress(MouseInputEvent event) {
		if(Mouse.getEventButtonState())
			onPress();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onMousePress(GuiScreenEvent.MouseInputEvent.Pre event) {
		if(Mouse.getEventButtonState() && event.getGui() != null)
			onPress();
	}

	private void onPress() {
		if(timeSinceKeyPressed >= 2400)
			StatuesPacketHandler.INSTANCE.sendToServer(new StatuesAFKPacket(false));
		
		timeSinceKeyPressed = 0;
	}
}
