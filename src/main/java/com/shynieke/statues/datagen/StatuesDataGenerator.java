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
import com.shynieke.statues.handlers.TraderHandler;
import com.shynieke.statues.registry.StatueJukeboxSongs;
import com.shynieke.statues.registry.StatuePatterns;
import com.shynieke.statues.registry.StatueTrims;
import com.shynieke.statues.registry.StatuesIntProviders;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createWorldRegistryObjects(BUILDER);
		event.createReloadableRegistryObjects(RELOADABLE_BUILDER);

		event.createProvider(StatueLanguageProvider::new);
		event.createProvider(StatueSoundProvider::new);
		event.createProvider(StatueModelProvider::new);

		event.createProvider(StatueBlockTagProvider::new);
		event.createProvider(StatueItemTagProvider::new);
		event.createProvider(StatueBiomeTagProvider::new);
		event.createProvider(StatueVillagerTradesTagProvider::new);
		event.createProvider(StatueGLMProvider::new);

//		event.createProvider(StatuePatchouliProvider::new);
		event.createProvider(StatueCurioProvider::new);
	}

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.TRIM_PATTERN, StatueTrims::bootstrap)
			.add(Registries.JUKEBOX_SONG, StatueJukeboxSongs::bootstrap)
			.add(Registries.VILLAGER_TRADE, TraderHandler::bootstrap)
			.add(Registries.DECORATED_POT_PATTERN, StatuePatterns::bootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, StatueBiomeModifiers::bootstrap);

	public static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
			.add(RecipeProvider.asBootstrap(StatueRecipeProvider::new))
			.add(Registries.ADVANCEMENT, StatueAdvancementProvider.create())
			.add(Registries.CONTEXT_INT_PROVIDER, StatuesIntProviders::bootstrap)
			.add(Registries.LOOT_TABLE, StatueLootProvider.create());
}
