package com.shynieke.statues.init;

import com.mojang.serialization.Codec;
import com.shynieke.statues.Reference;
import com.shynieke.statues.biome_modifier.StatueBatBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

public class StatueBiomeModifierSerializers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Reference.MOD_ID);
	public static final DeferredRegister<BiomeModifier> BIOME_MODIFIERS = DeferredRegister.create(Keys.BIOME_MODIFIERS, Reference.MOD_ID);

	public static final RegistryObject<Codec<StatueBatBiomeModifier>> STATUE_BAT_SERIALIZER = BIOME_MODIFIER_SERIALIZERS.register("statue_bat", StatueBatBiomeModifier::makeCodec);
	public static final RegistryObject<StatueBatBiomeModifier> STATUE_BAT = BIOME_MODIFIERS.register("statue_bat", () -> new StatueBatBiomeModifier());

}
