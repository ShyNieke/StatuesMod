package com.shynieke.statues.handler;

import com.shynieke.statues.Reference;
import com.shynieke.statues.config.StatuesConfigGen;
import com.shynieke.statues.init.StatuesBlocks;
import com.shynieke.statues.packets.StatuesAFKPacket;
import com.shynieke.statues.packets.StatuesPacketHandler;
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
import org.lwjgl.input.Mouse;

import java.util.List;

public class FishHandler {
	@SubscribeEvent
	public void onItemFished(ItemFishedEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.world;
		if (!world.isRemote) {
			if (StatuesConfigGen.general.AquaticFishing) { //Check if aquatic fishing is enabled otherwise do nothing
				double default_drop_chance = StatuesConfigGen.general.OldDropChance;
				if (!(player instanceof FakePlayer)) {
					EntityPlayerMP realPlayer = (EntityPlayerMP) event.getEntityPlayer();
					String[] LuckyPlayers = StatuesConfigGen.luckyplayers.lucky_players;
					if (StatuesConfigGen.othersettings.antiAfk) {
						if (!realPlayer.getEntityData().getBoolean(afkKey)) {
							if (LuckyPlayers.length != 0) {
								for (int i = 0; (i < LuckyPlayers.length) && (LuckyPlayers[i] != null); i++) {
									if (realPlayer.getName().equals(LuckyPlayers[i])) {
										default_drop_chance = StatuesConfigGen.general.OldDropChance / 4;
									}
								}
							} else {
								default_drop_chance = StatuesConfigGen.general.OldDropChance;
							}

							if (Math.random() < default_drop_chance) {
								EntityFishHook hook = event.getHookEntity();
								if (hook != null) {
									EntityItem entityitem = new EntityItem(hook.world, hook.posX, hook.posY, hook.posZ, new ItemStack(StatuesBlocks.pufferfish_statue[0]));
									double d0 = hook.getAngler().posX - hook.posX;
									double d1 = hook.getAngler().posY - hook.posY;
									double d2 = hook.getAngler().posZ - hook.posZ;
									double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
									double d4 = 0.1D;
									entityitem.motionX = d0 * 0.1D;
									entityitem.motionY = d1 * 0.1D + (double) MathHelper.sqrt(d3) * 0.08D;
									entityitem.motionZ = d2 * 0.1D;
									hook.world.spawnEntity(entityitem);
								}

								event.setCanceled(true);
							}
						}
					} else {
						if (LuckyPlayers.length != 0) {
							for (int i = 0; (i < LuckyPlayers.length) && (LuckyPlayers[i] != null); i++) {
								if (realPlayer.getName().equals(LuckyPlayers[i])) {
									default_drop_chance = StatuesConfigGen.general.OldDropChance / 4;
								}
							}
						} else {
							default_drop_chance = StatuesConfigGen.general.OldDropChance;
						}

						if (Math.random() < default_drop_chance) {
							EntityFishHook hook = event.getHookEntity();
							if (hook != null) {
								EntityItem entityitem = getFishedStatue(hook.world, hook.posX, hook.posY, hook.posZ);
								double d0 = hook.getAngler().posX - hook.posX;
								double d1 = hook.getAngler().posY - hook.posY;
								double d2 = hook.getAngler().posZ - hook.posZ;
								double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
								double d4 = 0.1D;
								entityitem.motionX = d0 * 0.1D;
								entityitem.motionY = d1 * 0.1D + (double) MathHelper.sqrt(d3) * 0.08D;
								entityitem.motionZ = d2 * 0.1D;
								hook.world.spawnEntity(entityitem);
							}

							event.setCanceled(true);
						}
					}
				}
			}
		}
	}

	public static EntityItem getFishedStatue(World world, double x, double y, double z) {
		ItemStack fishedStack;
		switch (world.rand.nextInt(8)) {
			default:
				fishedStack = new ItemStack(StatuesBlocks.pufferfish_statue[0]);
				break;
			case 1:
				fishedStack = new ItemStack(StatuesBlocks.pufferfish_medium_statue[0]);
				break;
			case 2:
				fishedStack = new ItemStack(StatuesBlocks.pufferfish_small_statue[0]);
				break;
			case 3:
				fishedStack = new ItemStack(StatuesBlocks.drowned_statue[0]);
				break;
			case 4:
				fishedStack = new ItemStack(StatuesBlocks.turtle_statue[0]);
				break;
			case 5:
				fishedStack = new ItemStack(StatuesBlocks.cod_statue[0]);
				break;
			case 6:
				fishedStack = new ItemStack(StatuesBlocks.salmon_statue[0]);
				break;
		}
		return new EntityItem(world, x, y, z, fishedStack);
	}

	public static final int maxTime = 2400;
	private int timeSinceKeyPressed;
	public static final String afkKey = Reference.MOD_PREFIX + "afk";

	public static void updateAfk(EntityPlayer player, boolean afk) {
		if (player.world.playerEntities.size() != 1) {
			player.getEntityData().setBoolean(afkKey, afk);
		}
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event) {
		World world = event.player.world;
		List<EntityPlayer> players = world.playerEntities;

		if (players.size() == 1) {
			EntityPlayer lastPlayer = players.get(0);
			lastPlayer.getEntityData().setBoolean(afkKey, false);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		if (event.phase == Phase.END) {
			timeSinceKeyPressed++;
			if (timeSinceKeyPressed >= maxTime)
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
		if (Mouse.getEventButtonState())
			onPress();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onMousePress(GuiScreenEvent.MouseInputEvent.Pre event) {
		if (Mouse.getEventButtonState() && event.getGui() != null)
			onPress();
	}

	private void onPress() {
		if (timeSinceKeyPressed >= maxTime)
			StatuesPacketHandler.INSTANCE.sendToServer(new StatuesAFKPacket(false));

		timeSinceKeyPressed = 0;
	}
}
