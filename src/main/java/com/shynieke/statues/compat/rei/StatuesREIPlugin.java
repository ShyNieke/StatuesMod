package com.shynieke.statues.compat.rei;

import com.shynieke.statues.Reference;
import com.shynieke.statues.compat.rei.display.LootDisplay;
import com.shynieke.statues.compat.rei.display.UpgradeDisplay;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.forge.REIPluginCommon;

@REIPluginCommon
public class StatuesREIPlugin implements REICommonPlugin {
	public static final CategoryIdentifier<LootDisplay> LOOT = CategoryIdentifier.of(Reference.MOD_ID, "plugins/loot");
	public static final CategoryIdentifier<UpgradeDisplay> UPGRADE = CategoryIdentifier.of(Reference.MOD_ID, "plugins/upgrade");

	@Override
	public void registerDisplays(ServerDisplayRegistry registry) {
		registry.beginRecipeFiller(LootRecipe.class)
				.filterType(StatuesRecipes.LOOT_RECIPE.get())
				.fill(LootDisplay::new);

		registry.beginRecipeFiller(UpgradeRecipe.class)
				.filterType(StatuesRecipes.UPGRADE_RECIPE.get())
				.fill(UpgradeDisplay::new);
	}

	@Override
	public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
		registry.register(Reference.modLoc("loot"), LootDisplay.SERIALIZER);
		registry.register(Reference.modLoc("upgrade"), UpgradeDisplay.SERIALIZER);
	}
}
