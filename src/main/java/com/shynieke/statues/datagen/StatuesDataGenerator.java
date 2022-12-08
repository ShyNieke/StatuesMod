package com.shynieke.statues.datagen;

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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
//		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
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
			generator.addProvider(event.includeServer(), new StatueGLMProvider(generator));
//			generator.addProvider(event.includeServer(), new StatuePatchouliProvider(packOutput));

//			final HolderSet.Named<Biome> spawnBiomesTag = new HolderSet.Named<>(ops.registry(Registries.BIOME_REGISTRY).get(), StatueTags.CAN_SPAWN_STATUE_BAT);
//			final BiomeModifier addSpawn = AddSpawnsBiomeModifier.singleSpawn(
//					spawnBiomesTag,
//					new SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));
//
//			final HolderSet.Named<Biome> spawnLessBiomesTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), StatueTags.CAN_SPAWN_FEWER_STATUE_BAT);
//			final BiomeModifier addFewerSpawn = AddSpawnsBiomeModifier.singleSpawn(
//					spawnLessBiomesTag,
//					new SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));
//
//			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
//					generator, existingFileHelper, Reference.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
//					Map.of(new ResourceLocation(Reference.MOD_ID, "add_statue_bat_spawn"), addSpawn,
//							new ResourceLocation(Reference.MOD_ID, "add_fewer_statue_bat_spawn"), addFewerSpawn
//					)
//			));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new StatueLanguageProvider(generator));
			generator.addProvider(event.includeClient(), new StatueSoundProvider(generator, existingFileHelper));
			generator.addProvider(event.includeClient(), new StatueBlockstateProvider(generator, existingFileHelper));
			generator.addProvider(event.includeClient(), new StatueItemModelProvider(generator, existingFileHelper));
		}
	}
}
