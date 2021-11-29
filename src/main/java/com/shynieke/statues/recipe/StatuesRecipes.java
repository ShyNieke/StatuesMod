package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class StatuesRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<HardcoreRecipe.Serializer> HARDCORE_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("hardcore_shaped", HardcoreRecipe.Serializer::new);
}
