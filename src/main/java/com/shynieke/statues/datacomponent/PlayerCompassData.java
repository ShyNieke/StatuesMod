package com.shynieke.statues.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record PlayerCompassData(GlobalPos globalPos, String name) {
	public static final Codec<PlayerCompassData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
					GlobalPos.CODEC.fieldOf("globalPos").forGetter(PlayerCompassData::globalPos),
					Codec.STRING.fieldOf("name").forGetter(PlayerCompassData::name))
			.apply(inst, PlayerCompassData::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, PlayerCompassData> STREAM_CODEC = StreamCodec.of(
			PlayerCompassData::toNetwork, PlayerCompassData::fromNetwork
	);

	private static PlayerCompassData fromNetwork(RegistryFriendlyByteBuf byteBuf) {
		GlobalPos pos = GlobalPos.STREAM_CODEC.decode(byteBuf);
		String name = byteBuf.readUtf(32767);
		return new PlayerCompassData(pos, name);
	}

	private static void toNetwork(RegistryFriendlyByteBuf byteBuf, PlayerCompassData playerCompassData) {
		GlobalPos.STREAM_CODEC.encode(byteBuf, playerCompassData.globalPos());
		byteBuf.writeUtf(playerCompassData.name());
	}
}
