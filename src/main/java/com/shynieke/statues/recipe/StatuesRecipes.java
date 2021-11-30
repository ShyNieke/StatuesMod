package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import com.shynieke.statues.recipe.HardcoreRecipe.Serializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StatuesRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<Serializer> HARDCORE_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("hardcore_shaped", HardcoreRecipe.Serializer::new);
}
