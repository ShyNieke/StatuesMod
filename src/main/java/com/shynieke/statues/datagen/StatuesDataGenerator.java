package com.shynieke.statues.datagen;

import com.shynieke.statues.Reference;
import com.shynieke.statues.datagen.client.StatueBlockstateProvider;
import com.shynieke.statues.datagen.client.StatueItemModelProvider;
import com.shynieke.statues.datagen.client.StatueLanguageProvider;
import com.shynieke.statues.datagen.client.StatueSoundProvider;
import com.shynieke.statues.datagen.server.StatueAdvancementProvider;
import com.shynieke.statues.datagen.server.StatueBiomeModifiers;
import com.shynieke.statues.datagen.server.StatueBiomeTagProvider;
import com.shynieke.statues.datagen.server.StatueBlockTagProvider;
import com.shynieke.statues.datagen.server.StatueGLMProvider;
import com.shynieke.statues.datagen.server.StatueItemTagProvider;
import com.shynieke.statues.datagen.server.StatueLootProvider;
import com.shynieke.statues.datagen.server.StatueRecipeProvider;
import com.shynieke.statues.datagen.server.patchouli.StatuePatchouliProvider;
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
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new StatueLootProvider(packOutput));
			generator.addProvider(event.includeServer(), new StatueRecipeProvider(packOutput));
			StatueBlockTagProvider blockTags = new StatueBlockTagProvider(packOutput, lookupProvider, helper);
			generator.addProvider(event.includeServer(), blockTags);
			generator.addProvider(event.includeServer(), new StatueItemTagProvider(packOutput, lookupProvider, blockTags, helper));
			generator.addProvider(event.includeServer(), new StatueBiomeTagProvider(packOutput, lookupProvider, helper));
			generator.addProvider(event.includeServer(), new StatueGLMProvider(packOutput));
			generator.addProvider(event.includeServer(), new StatuePatchouliProvider(packOutput));
			generator.addProvider(event.includeServer(), new StatueAdvancementProvider(packOutput, lookupProvider, helper));

			generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
					packOutput, CompletableFuture.supplyAsync(StatuesDataGenerator::getProvider), Set.of(Reference.MOD_ID)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new StatueLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new StatueSoundProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new StatueBlockstateProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new StatueItemModelProvider(packOutput, helper));
		}
	}

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, $ -> {
		});
		registryBuilder.add(Registries.PLACED_FEATURE, $ -> {
		});
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
