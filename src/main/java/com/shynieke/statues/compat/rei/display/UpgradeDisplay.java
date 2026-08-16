package com.shynieke.statues.compat.rei.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.compat.rei.StatuesREIPlugin;
import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueDataComponents;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UpgradeDisplay implements Display {
	public static final DisplaySerializer<UpgradeDisplay> SERIALIZER = DisplaySerializer.of(
			RecordCodecBuilder.mapCodec(instance -> instance.group(
					EntryIngredient.codec().listOf().fieldOf("inputEntries").forGetter(UpgradeDisplay::getInputEntries),
					EntryIngredient.codec().listOf().fieldOf("outputEntries").forGetter(UpgradeDisplay::getOutputEntries),
					EntryIngredient.codec().fieldOf("centerEntry").forGetter(UpgradeDisplay::getCenterEntry),
					EntryIngredient.codec().listOf().fieldOf("catalystsEntries").forGetter(UpgradeDisplay::getCatalysts),
					EntryIngredient.codec().fieldOf("resultEntry").forGetter(UpgradeDisplay::getResult),
					EntryIngredient.codec().fieldOf("coreEntry").forGetter(UpgradeDisplay::getCore),
					Codec.BOOL.fieldOf("requireCore").forGetter(UpgradeDisplay::isCoreRequired),
					UpgradeType.CODEC.fieldOf("upgradeType").forGetter(UpgradeDisplay::getUpgradeType),
					Codec.INT.fieldOf("tier").forGetter(UpgradeDisplay::getTier)
			).apply(instance, UpgradeDisplay::new)),
			StreamCodec.composite(
					EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
					d -> d.inputEntries,
					EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
					d -> d.outputEntries,
					EntryIngredient.streamCodec(),
					d -> d.centerEntry,
					EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
					d -> d.catalystsEntries,
					EntryIngredient.streamCodec(),
					d -> d.resultEntry,
					EntryIngredient.streamCodec(),
					d -> d.coreEntry,
					ByteBufCodecs.BOOL,
					d -> d.requireCore,
					UpgradeType.STREAM_CODEC,
					d -> d.upgradeType,
					ByteBufCodecs.INT,
					d -> d.tier,
					UpgradeDisplay::new
			));

	private final boolean requireCore;
	private final UpgradeType upgradeType;
	private final int tier;

	private final List<EntryIngredient> inputEntries;
	private final List<EntryIngredient> outputEntries;

	private final EntryIngredient centerEntry;
	private final List<EntryIngredient> catalystsEntries;
	private final EntryIngredient resultEntry;
	private final EntryIngredient coreEntry;

	public UpgradeDisplay(List<EntryIngredient> inputEntries, List<EntryIngredient> outputEntries,
	                      EntryIngredient centerEntry, List<EntryIngredient> catalystsEntries,
	                      EntryIngredient resultEntry, EntryIngredient coreEntry, boolean requireCore,
	                      UpgradeType upgradeType, int tier) {
		this.inputEntries = inputEntries;
		this.outputEntries = outputEntries;
		this.centerEntry = centerEntry;
		this.catalystsEntries = catalystsEntries;
		this.resultEntry = resultEntry;
		this.coreEntry = coreEntry;
		this.requireCore = requireCore;
		this.upgradeType = upgradeType;
		this.tier = tier;


	}

	public UpgradeDisplay(RecipeHolder<UpgradeRecipe> upgradeRecipeRecipeHolder) {
		UpgradeRecipe recipe = upgradeRecipeRecipeHolder.value();

		this.requireCore = recipe.requiresCore();
		this.upgradeType = recipe.getUpgradeType();
		this.tier = recipe.getTier();

		List<ItemStack> centerList = new ArrayList<>();
		for (Holder<Item> centerItem : recipe.getCenter().getValues()) {
			ItemStack stack = new ItemStack(centerItem);
			if (recipe.getUpgradeType() != UpgradeType.UPGRADE)
				fillInTag(stack, recipe);

			if (recipe.getUpgradeType() != UpgradeType.UPGRADE) {
				setUpgradeSlots(stack, 1);
			}
			if (recipe.getUpgradeType() == UpgradeType.UNGLOWING) {
				UpgradeType.GLOWING.apply(stack, -1);
			}

			if (tier > -1) {
				for (int i = 0; i < tier; i++) {
					recipe.getUpgradeType().apply(stack, i);
				}
			}

			centerList.add(stack);
		}
		ItemStack result = recipe.getResultItem().copy();
		List<ItemStack> stacks = new ArrayList<>();
		if (result.isEmpty()) {
			for (ItemStack centerStack : centerList) {
				ItemStack stack = centerStack.copy();

				recipe.getUpgradeType().apply(stack, tier);

				if (recipe.getUpgradeType().isSubsequentUsesSlot())
					setUpgradeSlots(stack, 0);
				stacks.add(stack);
			}
		} else {
			stacks.add(result);
		}
		final List<Ingredient> catalysts = recipe.getCatalysts();
		final Ingredient coreIngredient = recipe.getCoreIngredient();

		this.inputEntries = new ArrayList<>();
		this.inputEntries.add(EntryIngredients.ofItemStacks(centerList));
		if (requireCore)
			this.inputEntries.add(EntryIngredients.ofIngredient(coreIngredient));
		this.inputEntries.addAll(EntryIngredients.ofIngredients(catalysts));

		this.outputEntries = List.of(EntryIngredients.ofItemStacks(stacks));

		this.centerEntry = EntryIngredients.ofItemStacks(centerList);
		this.catalystsEntries = EntryIngredients.ofIngredients(catalysts);
		this.resultEntry = EntryIngredients.ofItemStacks(stacks);
		this.coreEntry = EntryIngredients.ofIngredient(coreIngredient);
	}

	private void fillInTag(ItemStack stack, UpgradeRecipe recipe) {
		int tier = recipe.getTier();
		stack.set(StatueDataComponents.UPGRADED.get(), true);

		StatueStats stats = stack.getOrDefault(StatueDataComponents.STATS.get(), StatueStats.empty());
		stats.setLevel(tier == -1 ? 0 : tier + 1);
		stats.setUpgradeSlots(20);
		stack.set(StatueDataComponents.STATS.get(), stats);
	}

	private void setUpgradeSlots(ItemStack stack, int count) {
		StatueStats stats = stack.getOrDefault(StatueDataComponents.STATS.get(), StatueStats.empty());
		stats.setUpgradeSlots(count);
		stack.set(StatueDataComponents.STATS.get(), stats);
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

	public boolean isCoreRequired() {
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

	public EntryIngredient getCenterEntry() {
		return centerEntry;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return StatuesREIPlugin.UPGRADE;
	}

	@Override
	public Optional<Identifier> getDisplayLocation() {
		return Optional.empty();
	}

	@Nullable
	@Override
	public DisplaySerializer<? extends Display> getSerializer() {
		return SERIALIZER;
	}
}
