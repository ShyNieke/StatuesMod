package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StatuesRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Reference.MOD_ID);

	public static final RegistryObject<RecipeType<LootRecipe>> LOOT_RECIPE = RECIPE_TYPES.register("loot_recipe", () -> new RecipeType<>() {
	});
	public static final RegistryObject<RecipeType<UpgradeRecipe>> UPGRADE_RECIPE = RECIPE_TYPES.register("upgrade_recipe", () -> new RecipeType<>() {
	});

	public static final RegistryObject<HardcoreRecipe.Serializer> HARDCORE_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("hardcore_shaped", HardcoreRecipe.Serializer::new);
	public static final RegistryObject<LootRecipe.Serializer> LOOT_SERIALIZER = RECIPE_SERIALIZERS.register("loot", LootRecipe.Serializer::new);
	public static final RegistryObject<UpgradeRecipe.Serializer> UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register("upgrade", UpgradeRecipe.Serializer::new);
}
