package com.shynieke.statues.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.shynieke.statues.Reference;
import com.shynieke.statues.datagen.client.StatueBlockstateProvider;
import com.shynieke.statues.datagen.client.StatueItemModelProvider;
import com.shynieke.statues.datagen.client.StatueLanguageProvider;
import com.shynieke.statues.datagen.client.StatueSoundProvider;
import com.shynieke.statues.datagen.server.StatueBiomeTagProvider;
import com.shynieke.statues.datagen.server.StatueBlockTagProvider;
import com.shynieke.statues.datagen.server.StatueGLMProvider;
import com.shynieke.statues.datagen.server.StatueItemTagProvider;
import com.shynieke.statues.datagen.server.StatueLootProvider;
import com.shynieke.statues.datagen.server.StatueRecipeProvider;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		HolderLookup.Provider provider = getProvider();
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, provider);

		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new StatueLootProvider(packOutput));
			generator.addProvider(event.includeServer(), new StatueRecipeProvider(packOutput));
			StatueBlockTagProvider blockTags = new StatueBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
			generator.addProvider(event.includeServer(), blockTags);
			generator.addProvider(event.includeServer(), new StatueItemTagProvider(packOutput, lookupProvider, blockTags, existingFileHelper));
			generator.addProvider(event.includeServer(), new StatueBiomeTagProvider(packOutput, lookupProvider, existingFileHelper));
			generator.addProvider(event.includeServer(), new StatueGLMProvider(packOutput));
//			generator.addProvider(event.includeServer(), new StatuePatchouliProvider(packOutput));

			final HolderLookup.RegistryLookup<Biome> biomeReg = provider.lookupOrThrow(Registries.BIOME);
			final BiomeModifier addSpawn = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
					HolderSet.emptyNamed(biomeReg, StatueTags.CAN_SPAWN_STATUE_BAT),
					new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));

			final BiomeModifier addFewerSpawn = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
					HolderSet.emptyNamed(biomeReg, StatueTags.CAN_SPAWN_FEWER_STATUE_BAT),
					new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					packOutput, existingFileHelper, Reference.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
					Map.of(new ResourceLocation(Reference.MOD_ID, "add_statue_bat_spawn"), addSpawn,
							new ResourceLocation(Reference.MOD_ID, "add_fewer_statue_bat_spawn"), addFewerSpawn
					)
			));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new StatueLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new StatueSoundProvider(packOutput, existingFileHelper));
			generator.addProvider(event.includeClient(), new StatueBlockstateProvider(packOutput, existingFileHelper));
			generator.addProvider(event.includeClient(), new StatueItemModelProvider(packOutput, existingFileHelper));
		}
	}

	private static HolderLookup.Provider getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, $ -> {
		});
		registryBuilder.add(Registries.PLACED_FEATURE, $ -> {
		});
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
	}
}
