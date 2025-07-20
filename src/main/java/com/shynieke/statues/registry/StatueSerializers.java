package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.item.component.ResolvableProfile;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class StatueSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Reference.MOD_ID);

	public static final Supplier<EntityDataSerializer<Optional<ResolvableProfile>>> OPTIONAL_RESOLVABLE_PROFILE = ENTITY_DATA_SERIALIZER.register("optional_resolvable_profile", () -> EntityDataSerializer.forValueType(
			ResolvableProfile.STREAM_CODEC.apply(ByteBufCodecs::optional)
	));

	public static final Supplier<EntityDataSerializer<Optional<UUID>>> OPTIONAL_UUID = ENTITY_DATA_SERIALIZER.register("optional_uuid", () -> EntityDataSerializer.forValueType(
			UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs::optional)
	));
}
