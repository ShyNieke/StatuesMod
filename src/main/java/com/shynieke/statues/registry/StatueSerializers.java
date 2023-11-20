package com.shynieke.statues.registry;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;

public class StatueSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Reference.MOD_ID);

	public static final Supplier<EntityDataSerializer<Optional<GameProfile>>> OPTIONAL_GAME_PROFILE = ENTITY_DATA_SERIALIZER.register("optional_game_profile", () -> new EntityDataSerializer<Optional<GameProfile>>() {
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
	});
}
