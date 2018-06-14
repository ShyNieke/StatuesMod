package com.svennieke.statues.packets;

import com.svennieke.statues.handler.FishHandler;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StatuesAFKPacket implements IMessage{
	
	public boolean isAfk;
	
	public StatuesAFKPacket() {}
	
	public StatuesAFKPacket(boolean afk) {
		this.isAfk = afk;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		isAfk = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isAfk);
	}
	
	public static class PacketHandler implements IMessageHandler<StatuesAFKPacket, IMessage>
	{
		@Override
		public IMessage onMessage(StatuesAFKPacket message, MessageContext ctx) {
			FishHandler.updateAfk(ctx.getServerHandler().player, message.isAfk);
		    return null;
		}
	}
}
