package com.svennieke.statues.init;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.svennieke.statues.config.StatuesConfigGen;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class StatuesRecipeCondition implements IConditionFactory{

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> StatuesConfigGen.general.NewSystem;
	}

}
