package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StatuesRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Reference.MOD_ID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Reference.MOD_ID);

	public static final Supplier<RecipeType<LootRecipe>> LOOT_RECIPE = RECIPE_TYPES.register("loot_recipe", () -> new RecipeType<>() {
	});
	public static final Supplier<RecipeType<UpgradeRecipe>> UPGRADE_RECIPE = RECIPE_TYPES.register("upgrade_recipe", () -> new RecipeType<>() {
	});

	public static final Supplier<HardcoreRecipe.Serializer> HARDCORE_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("hardcore_shaped", HardcoreRecipe.Serializer::new);
	public static final Supplier<LootRecipe.Serializer> LOOT_SERIALIZER = RECIPE_SERIALIZERS.register("loot", LootRecipe.Serializer::new);
	public static final Supplier<UpgradeRecipe.Serializer> UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register("upgrade", UpgradeRecipe.Serializer::new);
}
