package com.svennieke.statues.RecipeConditions;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.svennieke.statues.config.StatuesConfigGen;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class PlayerCraftingRecipe implements IConditionFactory{

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> StatuesConfigGen.player.PlayerCrafting;
	}
}
