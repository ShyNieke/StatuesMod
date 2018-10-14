package com.svennieke.statues.init;

import java.util.ArrayList;

import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.items.ItemCup;
import com.svennieke.statues.items.ItemMooshroomSoup;
import com.svennieke.statues.items.ItemPlayerCompass;
import com.svennieke.statues.items.ItemRoyalNugget;
import com.svennieke.statues.items.ItemStatueCore;
import com.svennieke.statues.items.ItemTea;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class StatuesItems {
	
	public static ItemFood nugget, soup, tea, cup;
	public static Item core, player_compass;//, marshmallow;
	
	public static ArrayList<Item> ITEMS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		nugget = registerItem(new ItemRoyalNugget(4, 0.1f, "royalnugget"));
		core = registerItem(new ItemStatueCore("statuecore"));
		soup = registerItem(new ItemMooshroomSoup(6, 0.3F, "mooshroomsoup"));
		tea = registerItem(new ItemTea("tea"));
		cup = registerItem(new ItemCup(1, 0.2F, "cup"));
		
		if(StatuesConfigGen.player.PlayerCompass)
		{
			player_compass = registerItem(new ItemPlayerCompass("playercompass"));
		}
		
		//marshmallow = registerItem(new ItemRoyalNugget(6, 0.2f, "marshmallow"));

		registry.registerAll(ITEMS.toArray(new Item[0]));
    }
	
	public static <T extends Item> T registerItem(T item)
    {
        ITEMS.add(item);
        return item;
    }
}
