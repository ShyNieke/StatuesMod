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
import com.shynieke.statues.datagen.server.patchouli.StatuePatchouliProvider;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new StatueLootProvider(generator));
			generator.addProvider(event.includeServer(), new StatueRecipeProvider(generator));
			StatueBlockTagProvider blockTags = new StatueBlockTagProvider(generator, event.getExistingFileHelper());
			generator.addProvider(event.includeServer(), blockTags);
			generator.addProvider(event.includeServer(), new StatueItemTagProvider(generator, blockTags, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new StatueBiomeTagProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new StatueGLMProvider(generator));
			generator.addProvider(event.includeServer(), new StatuePatchouliProvider(generator));

			final HolderSet.Named<Biome> spawnBiomesTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), StatueTags.CAN_SPAWN_STATUE_BAT);
			final BiomeModifier addSpawn = AddSpawnsBiomeModifier.singleSpawn(
					spawnBiomesTag,
					new SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));

			final HolderSet.Named<Biome> spawnLessBiomesTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), StatueTags.CAN_SPAWN_FEWER_STATUE_BAT);
			final BiomeModifier addFewerSpawn = AddSpawnsBiomeModifier.singleSpawn(
					spawnLessBiomesTag,
					new SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, Reference.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
					Map.of(new ResourceLocation(Reference.MOD_ID, "add_statue_bat_spawn"), addSpawn,
							new ResourceLocation(Reference.MOD_ID, "add_fewer_statue_bat_spawn"), addFewerSpawn
					)
			));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new StatueLanguageProvider(generator));
			generator.addProvider(event.includeClient(), new StatueSoundProvider(generator, helper));
			generator.addProvider(event.includeClient(), new StatueBlockstateProvider(generator, helper));
			generator.addProvider(event.includeClient(), new StatueItemModelProvider(generator, helper));
		}
	}
}
