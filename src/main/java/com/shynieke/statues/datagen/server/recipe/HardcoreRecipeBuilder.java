package com.shynieke.statues.datagen.server.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shynieke.statues.recipe.HardcoreRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HardcoreRecipeBuilder implements RecipeBuilder {
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final Item result;
	private final ItemStack resultStack; // Neo: add stack result support
	private final List<String> rows = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;
	private boolean showNotification = true;

	private HardcoreRecipeBuilder(HolderGetter<Item> p_365072_, RecipeCategory p_249996_, ItemLike p_251475_, int p_248948_) {
		this(p_365072_, p_249996_, new ItemStack(p_251475_, p_248948_));
	}

	private HardcoreRecipeBuilder(HolderGetter<Item> p_365072_, RecipeCategory p_249996_, ItemStack result) {
		this.items = p_365072_;
		this.category = p_249996_;
		this.result = result.getItem();
		this.resultStack = result;
	}

	public static HardcoreRecipeBuilder hardcore(HolderGetter<Item> p_364036_, RecipeCategory p_250853_, ItemLike p_249747_) {
		return hardcore(p_364036_, p_250853_, p_249747_, 1);
	}

	public static HardcoreRecipeBuilder hardcore(HolderGetter<Item> p_365019_, RecipeCategory p_251325_, ItemLike p_250636_, int p_249081_) {
		return new HardcoreRecipeBuilder(p_365019_, p_251325_, p_250636_, p_249081_);
	}

	public static HardcoreRecipeBuilder hardcore(HolderGetter<Item> p_365019_, RecipeCategory p_251325_, ItemStack result) {
		return new HardcoreRecipeBuilder(p_365019_, p_251325_, result);
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
		if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.rows.add(pattern);
			return this;
		}
	}

	public HardcoreRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.criteria.put(name, criterion);
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
	public Item getResult() {
		return this.result;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
		ShapedRecipePattern shapedrecipepattern = this.ensureValid(resourceKey);
		Advancement.Builder advancement$builder = recipeOutput.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
				.rewards(AdvancementRewards.Builder.recipe(resourceKey))
				.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(advancement$builder::addCriterion);
		HardcoreRecipe hardcoreRecipe = new HardcoreRecipe(
				Objects.requireNonNullElse(this.group, ""),
				RecipeBuilder.determineBookCategory(this.category),
				shapedrecipepattern,
				this.resultStack,
				this.showNotification
		);
		recipeOutput.accept(resourceKey, hardcoreRecipe, advancement$builder.build(resourceKey.location().withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

	private ShapedRecipePattern ensureValid(ResourceKey<Recipe<?>> p_380175_) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + p_380175_.location());
		} else {
			return ShapedRecipePattern.of(this.key, this.rows);
		}
	}
}
