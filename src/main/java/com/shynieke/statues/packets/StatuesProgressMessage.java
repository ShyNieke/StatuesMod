package com.shynieke.statues.packets;

import com.shynieke.statues.compat.waila.StatueProgressInfo;
import com.shynieke.statues.compat.waila.StatueTimerProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class StatuesProgressMessage implements IMessage {
	private int cooldown;
	private int maxCooldown;
	private boolean able;
	private BlockPos pos;

	public StatuesProgressMessage() {
	}

	public StatuesProgressMessage(int cooldown, int maxCooldown, boolean able, BlockPos pos) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.able = able;
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.cooldown = buf.readInt();
		this.maxCooldown = buf.readInt();
		this.able = buf.readBoolean();
		this.pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.cooldown);
		buf.writeInt(this.maxCooldown);
		buf.writeBoolean(this.able);
		buf.writeLong(this.pos.toLong());
	}

	public static class PacketHandler implements IMessageHandler<StatuesProgressMessage, IMessage> {
		@Override
		public IMessage onMessage(StatuesProgressMessage message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT && message != null) {
				addData(message.cooldown, message.maxCooldown, message.able, message.pos);
			}

			return null;
		}
	}

	public static void addData(int c, int cm, boolean a, BlockPos p) {
		StatueProgressInfo progress = new StatueProgressInfo(c, cm, a, p);

		if (StatueTimerProvider.info != progress)
			StatueTimerProvider.info = progress;
	}
}