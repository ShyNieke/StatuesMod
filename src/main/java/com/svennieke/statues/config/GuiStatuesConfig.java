package com.svennieke.statues.config;

import java.util.List;

import com.svennieke.statues.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiStatuesConfig extends GuiConfig {
	private static final String LANG_PREFIX = Reference.MOD_ID + ".category.";

	public GuiStatuesConfig(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), Reference.MOD_ID, false, false, I18n.format(Reference.MOD_ID + ".config.title"));
	}

	private static List<IConfigElement> getConfigElements() {
		final Configuration configuration = StatuesConfigGen.ConfigurationHolder.getConfiguration();

		final ConfigCategory topLevelCategory = configuration.getCategory(Configuration.CATEGORY_GENERAL);
		topLevelCategory.getChildren().forEach(configCategory -> configCategory.setLanguageKey(LANG_PREFIX + configCategory.getName()));

		return new ConfigElement(topLevelCategory).getChildElements();
	}
}