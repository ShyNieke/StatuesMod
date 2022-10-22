package com.shynieke.statues.init;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;

import java.util.Optional;

public class StatueSerializers {

	public static final EntityDataSerializer<Optional<GameProfile>> OPTIONAL_GAME_PROFILE = new EntityDataSerializer<Optional<GameProfile>>() {
		public void write(FriendlyByteBuf friendlyByteBuf, Optional<GameProfile> optionalGameProfile) {
			friendlyByteBuf.writeBoolean(optionalGameProfile.isPresent());
			if (optionalGameProfile.isPresent()) {
				friendlyByteBuf.writeNbt(NbtUtils.writeGameProfile(new CompoundTag(), optionalGameProfile.get()));
			}

		}

		public Optional<GameProfile> read(FriendlyByteBuf friendlyByteBuf) {
			return !friendlyByteBuf.readBoolean() ? Optional.empty() : Optional.of(NbtUtils.readGameProfile(friendlyByteBuf.readNbt()));
		}

		public Optional<GameProfile> copy(Optional<GameProfile> optionalGameProfile) {
			return optionalGameProfile;
		}
	};


	public static void init() {
		EntityDataSerializers.registerSerializer(OPTIONAL_GAME_PROFILE);
	}
}
