package com.shynieke.statues.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class StatuesCrafting {

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		addFurnaceRecipes();
	}

	public static void addFurnaceRecipes() {
		GameRegistry.addSmelting(StatuesItems.marshmallow, new ItemStack(StatuesItems.marshmallow_cooked), 0.1F);
		GameRegistry.addSmelting(StatuesItems.marshmallow_cooked, new ItemStack(StatuesItems.marshmallow_charred), 0.1F);
	}
}

