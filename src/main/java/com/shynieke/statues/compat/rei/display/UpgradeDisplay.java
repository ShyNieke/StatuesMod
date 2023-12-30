package com.shynieke.statues.compat.rei.display;

import com.shynieke.statues.compat.rei.REIPlugin;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueTags;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public final class UpgradeDisplay implements Display {
	protected final boolean requireCore;
	private final UpgradeType upgradeType;
	private final int tier;

	private final List<EntryIngredient> inputEntries;
	private final List<EntryIngredient> outputEntries;

	private final EntryIngredient centerEntry;
	private final List<EntryIngredient> catalystsEntries;
	private final EntryIngredient resultEntry;
	private final EntryIngredient coreEntry;

	public UpgradeDisplay(List<ItemStack> centerStacks, NonNullList<Ingredient> catalysts, List<ItemStack> stacks, boolean requireCore,
						  UpgradeType upgradeType, int tier) {
		this.requireCore = requireCore;
		this.upgradeType = upgradeType;
		this.tier = tier;

		this.inputEntries = new ArrayList<>();
		this.inputEntries.add(EntryIngredients.ofItemStacks(centerStacks));
		if (requireCore)
			this.inputEntries.add(EntryIngredients.ofIngredient(Ingredient.of(StatueTags.STATUE_CORE)));
		this.inputEntries.addAll(EntryIngredients.ofIngredients(catalysts));

		this.outputEntries = List.of(EntryIngredients.ofItemStacks(stacks));

		this.centerEntry = EntryIngredients.ofItemStacks(centerStacks);
		this.catalystsEntries = EntryIngredients.ofIngredients(catalysts);
		this.resultEntry = EntryIngredients.ofItemStacks(stacks);
		this.coreEntry = EntryIngredients.ofIngredient(Ingredient.of(StatueTags.STATUE_CORE));
	}

	public EntryIngredient getCenter() {
		return centerEntry;
	}

	public List<EntryIngredient> getCatalysts() {
		return catalystsEntries;
	}

	public EntryIngredient getResult() {
		return resultEntry;
	}

	public boolean isRequireCore() {
		return requireCore;
	}

	public EntryIngredient getCore() {
		return coreEntry;
	}

	public UpgradeType getUpgradeType() {
		return upgradeType;
	}

	public int getTier() {
		return tier;
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return inputEntries;
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return outputEntries;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REIPlugin.UPGRADE;
	}
}
