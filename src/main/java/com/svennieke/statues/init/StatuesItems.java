package com.svennieke.statues.init;

import com.google.common.base.Preconditions;
import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import com.svennieke.statues.items.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatuesItems {
	
	public static ItemFood nugget, soup, tea, cup;
	public static Item core, player_compass;//, marshmallow;
	
	public static ArrayList<Item> ITEMS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		nugget = registerItem(new ItemRoyalNugget(itemBuilder(), 4, 0.1f), "royal_nugget");
		core = registerItem(new ItemStatueCore(itemBuilder()), "statue_core");
		soup = registerItem(new ItemMooshroomSoup(itemBuilder(), 6, 0.3F), "mooshroom_soup");
		tea = registerItem(new ItemTea(itemBuilder()), "tea");
		cup = registerItem(new ItemCup(itemBuilder(), 1, 0.2F), "cup");

		player_compass = registerItem(new ItemPlayerCompass(itemBuilder()), "player_compass");
		
		//marshmallow = registerItem(new ItemRoyalNugget(6, 0.2f, "marshmallow"));
		
		registry.registerAll(ITEMS.toArray(new Item[0]));
    }
	
	public static <T extends Item> T registerItem(T item, String name)
    {
        ITEMS.add(item);
        
        item.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        Preconditions.checkNotNull(item, "registryName");
        Statues.LOGGER.debug(item + " " + name);
        return item;
    }
	
	private static Item.Properties itemBuilder()
	{
		return new Item.Properties();
	}
}
