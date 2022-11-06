package com.shynieke.statues.registry;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class StatueSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<EntityDataSerializer<Optional<GameProfile>>> OPTIONAL_GAME_PROFILE = ENTITY_DATA_SERIALIZER.register("optional_game_profile", () ->
			EntityDataSerializer.optional(FriendlyByteBuf::writeGameProfile, FriendlyByteBuf::readGameProfile)
	);
}
