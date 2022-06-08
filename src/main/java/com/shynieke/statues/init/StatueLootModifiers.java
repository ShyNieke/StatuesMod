package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.lootmodifiers.StatuesLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StatueLootModifiers {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<StatuesLootModifier.Serializer> STATUES_LOOT = GLM.register("statues_loot", StatuesLootModifier.Serializer::new);
}
