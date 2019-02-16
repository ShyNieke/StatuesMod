package com.svennieke.statues.RecipeConditions;

import com.google.gson.JsonObject;
import com.svennieke.statues.config.StatuesConfig;
import net.minecraftforge.common.crafting.IConditionSerializer;

import java.util.function.BooleanSupplier;

public class InteractionCondition implements IConditionSerializer{

	@Override
	public BooleanSupplier parse(JsonObject json) {
		return () -> StatuesConfig.COMMON.craftableInteraction.get();
	}
}
