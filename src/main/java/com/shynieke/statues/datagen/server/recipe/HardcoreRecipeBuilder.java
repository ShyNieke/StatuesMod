package com.shynieke.statues.datagen.server.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shynieke.statues.recipe.HardcoreRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HardcoreRecipeBuilder implements RecipeBuilder {
	private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final ItemStackTemplate result;
	private final List<String> rows = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;
	private boolean showNotification = true;

	private HardcoreRecipeBuilder(HolderGetter<Item> holderGetter, RecipeCategory category, ItemLike itemLike, int count) {
		this(holderGetter, category, new ItemStackTemplate(itemLike.asItem(), count));
	}

	private HardcoreRecipeBuilder(HolderGetter<Item> holderGetter, RecipeCategory category, ItemStackTemplate result) {
		this.items = holderGetter;
		this.category = category;
		this.result = result;
	}

	public static HardcoreRecipeBuilder hardcore(HolderGetter<Item> holderGetter, RecipeCategory category, ItemLike itemLike) {
		return hardcore(holderGetter, category, itemLike, 1);
	}

	public static HardcoreRecipeBuilder hardcore(HolderGetter<Item> holderGetter, RecipeCategory category, ItemLike itemLike, int count) {
		return new HardcoreRecipeBuilder(holderGetter, category, itemLike, count);
	}

	public static HardcoreRecipeBuilder hardcore(HolderGetter<Item> holderGetter, RecipeCategory category, ItemStackTemplate result) {
		return new HardcoreRecipeBuilder(holderGetter, category, result);
	}

	/**
	 * Adds a key to the recipe pattern.
	 */
	public HardcoreRecipeBuilder define(Character symbol, TagKey<Item> tag) {
		return this.define(symbol, Ingredient.of(this.items.getOrThrow(tag)));
	}

	/**
	 * Adds a key to the recipe pattern.
	 */
	public HardcoreRecipeBuilder define(Character symbol, ItemLike item) {
		return this.define(symbol, Ingredient.of(item));
	}

	/**
	 * Adds a key to the recipe pattern.
	 */
	public HardcoreRecipeBuilder define(Character symbol, Ingredient ingredient) {
		if (this.key.containsKey(symbol)) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
		} else if (symbol == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			this.key.put(symbol, ingredient);
			return this;
		}
	}

	/**
	 * Adds a new entry to the patterns for this recipe.
	 */
	public HardcoreRecipeBuilder pattern(String pattern) {
		if (!this.rows.isEmpty() && pattern.length() != this.rows.getFirst().length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.rows.add(pattern);
			return this;
		}
	}

	public HardcoreRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.advancementBuilder.unlockedBy(name, criterion);
		return this;
	}

	public HardcoreRecipeBuilder group(@Nullable String groupName) {
		this.group = groupName;
		return this;
	}

	public HardcoreRecipeBuilder showNotification(boolean showNotification) {
		this.showNotification = showNotification;
		return this;
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
		ShapedRecipePattern pattern = ShapedRecipePattern.of(this.key, this.rows);
		HardcoreRecipe recipe = new HardcoreRecipe(
				RecipeBuilder.createCraftingCommonInfo(this.showNotification),
				RecipeBuilder.createCraftingBookInfo(this.category, this.group),
				pattern,
				this.result
		);
		output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
	}
}
