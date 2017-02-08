package com.svennieke.statues.init;

import com.svennieke.statues.items.ItemRoyalNugget;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StatuesItems {
	
	public static ItemFood nugget;
	
	public static void init(){
		nugget = new ItemRoyalNugget(4, 0.1f, false);
		}
	
	public static void register()
	{
		GameRegistry.register(nugget);
	}
	
	public static void registerRenders()
	{
		registerRender(nugget);
	}
	
	public static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}
