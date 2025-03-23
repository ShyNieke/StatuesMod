package com.shynieke.statues.datagen;

import com.shynieke.statues.Reference;
import com.shynieke.statues.datagen.client.StatueLanguageProvider;
import com.shynieke.statues.datagen.client.StatueModelProvider;
import com.shynieke.statues.datagen.client.StatueSoundProvider;
import com.shynieke.statues.datagen.server.StatueAdvancementProvider;
import com.shynieke.statues.datagen.server.StatueBiomeModifiers;
import com.shynieke.statues.datagen.server.StatueBiomeTagProvider;
import com.shynieke.statues.datagen.server.StatueBlockTagProvider;
import com.shynieke.statues.datagen.server.StatueGLMProvider;
import com.shynieke.statues.datagen.server.StatueItemTagProvider;
import com.shynieke.statues.datagen.server.StatueLootProvider;
import com.shynieke.statues.datagen.server.StatueRecipeProvider;
import com.shynieke.statues.registry.StatueJukeboxSongs;
import com.shynieke.statues.registry.StatueTrims;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new StatueLanguageProvider(packOutput));
		generator.addProvider(true, new StatueSoundProvider(packOutput));
		generator.addProvider(true, new StatueModelProvider(packOutput));

		generator.addProvider(true, new StatueLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueRecipeProvider.Runner(packOutput, lookupProvider));
		StatueBlockTagProvider blockTags = new StatueBlockTagProvider(packOutput, lookupProvider);
		generator.addProvider(true, blockTags);
		generator.addProvider(true, new StatueItemTagProvider(packOutput, lookupProvider, blockTags));
		generator.addProvider(true, new StatueBiomeTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueGLMProvider(packOutput, lookupProvider));
//			generator.addProvider(true, new StatuePatchouliProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueAdvancementProvider(packOutput, lookupProvider));

		generator.addProvider(true, new DatapackBuiltinEntriesProvider(
				packOutput, CompletableFuture.supplyAsync(StatuesDataGenerator::getProvider), Set.of(Reference.MOD_ID)));
	}

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, $ -> {
		});
		registryBuilder.add(Registries.PLACED_FEATURE, $ -> {
		});
		registryBuilder.add(Registries.TRIM_PATTERN, StatueTrims::bootstrap);
		registryBuilder.add(Registries.JUKEBOX_SONG, StatueJukeboxSongs::bootstrap);
		registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, StatueBiomeModifiers::bootstrap);
		// We need the BIOME registry to be present, so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		Cloner.Factory cloner$factory = new Cloner.Factory();
		net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(cloner$factory::addCodec));
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
	}
}
