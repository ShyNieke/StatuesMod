package com.shynieke.statues.datagen;

import com.shynieke.statues.Reference;
import com.shynieke.statues.datagen.client.StatueLanguageProvider;
import com.shynieke.statues.datagen.client.StatueModelProvider;
import com.shynieke.statues.datagen.client.StatueSoundProvider;
import com.shynieke.statues.datagen.server.StatueAdvancementProvider;
import com.shynieke.statues.datagen.server.StatueBiomeTagProvider;
import com.shynieke.statues.datagen.server.StatueBlockTagProvider;
import com.shynieke.statues.datagen.server.StatueDatapackProvider;
import com.shynieke.statues.datagen.server.StatueGLMProvider;
import com.shynieke.statues.datagen.server.StatueItemTagProvider;
import com.shynieke.statues.datagen.server.StatueLootProvider;
import com.shynieke.statues.datagen.server.StatueRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
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
		generator.addProvider(true, new StatueItemTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueBiomeTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueGLMProvider(packOutput, lookupProvider));
//			generator.addProvider(true, new StatuePatchouliProvider(packOutput, lookupProvider));
		generator.addProvider(true, new StatueAdvancementProvider(packOutput, lookupProvider));

		generator.addProvider(true, new StatueDatapackProvider(
				packOutput,
				event.getLookupProvider(),
				Set.of(Reference.MOD_ID)
		));
	}
}
