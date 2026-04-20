package com.shynieke.statues.datagen;

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
import com.shynieke.statues.datagen.server.StatueVillagerTradesTagProvider;
import com.shynieke.statues.datagen.server.curios.StatueCurioProvider;
import com.shynieke.statues.datagen.server.patchouli.StatuePatchouliProvider;
import com.shynieke.statues.handlers.TraderHandler;
import com.shynieke.statues.registry.StatueJukeboxSongs;
import com.shynieke.statues.registry.StatueTrims;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		event.createDatapackRegistryObjects(BUILDER);
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new StatueLanguageProvider(packOutput));
		generator.addProvider(true, new StatueSoundProvider(packOutput));
		generator.addProvider(true, new StatueModelProvider(packOutput));

		generator.addProvider(true, new StatueLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueRecipeProvider.Runner(packOutput, lookupProvider));
		StatueBlockTagProvider blockTags = new StatueBlockTagProvider(packOutput, lookupProvider);
		generator.addProvider(true, blockTags);
		generator.addProvider(true, new StatueItemTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueBiomeTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueVillagerTradesTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueGLMProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueAdvancementProvider(packOutput, lookupProvider));

		generator.addProvider(true, new StatuePatchouliProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueCurioProvider(packOutput, lookupProvider));

	}

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, $ -> {
			})
			.add(Registries.PLACED_FEATURE, $ -> {
			})
			.add(Registries.TRIM_PATTERN, StatueTrims::bootstrap)
			.add(Registries.JUKEBOX_SONG, StatueJukeboxSongs::bootstrap)
			.add(Registries.VILLAGER_TRADE, TraderHandler::bootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, StatueBiomeModifiers::bootstrap);
}
