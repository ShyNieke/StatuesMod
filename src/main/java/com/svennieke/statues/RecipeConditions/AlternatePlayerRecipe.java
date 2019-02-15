package com.svennieke.statues.RecipeConditions;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.svennieke.statues.config.StatuesConfig;

import net.minecraftforge.common.crafting.IConditionSerializer;

public class AlternatePlayerRecipe implements IConditionSerializer{

	@Override
	public BooleanSupplier parse(JsonObject json) {
		return () -> StatuesConfig.COMMON.playerStatueAlternateRecipe.get();
	}
}
