package com.shynieke.statues.registry;

import com.mojang.serialization.Codec;
import com.shynieke.statues.Reference;
import com.shynieke.statues.lootmodifiers.StatuesLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

public class StatueLootModifiers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> STATUES_CITY_LOOT = GLM.register("statues_city_loot", StatuesLootModifier.CODEC);
}
