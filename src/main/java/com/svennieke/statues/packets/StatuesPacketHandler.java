package com.svennieke.statues.packets;

import com.svennieke.statues.Statues;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class StatuesPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("statues");
	
	public static void registerWailaUpdatePacket() {
		if(Statues.instance.isWailaInstalled)
		{
			INSTANCE.registerMessage(StatuesProgressMessage.PacketHandler.class, StatuesProgressMessage.class, 0, Side.CLIENT);
		}
	}
}
