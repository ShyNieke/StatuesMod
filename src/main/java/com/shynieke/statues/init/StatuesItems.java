package com.shynieke.statues.init;

import com.shynieke.statues.config.StatuesConfigGen;
import com.shynieke.statues.items.ItemCharredMarshmallow;
import com.shynieke.statues.items.ItemCup;
import com.shynieke.statues.items.ItemMarshmallow;
import com.shynieke.statues.items.ItemMooshroomSoup;
import com.shynieke.statues.items.ItemPlayerCompass;
import com.shynieke.statues.items.ItemRoyalNugget;
import com.shynieke.statues.items.ItemStatueCore;
import com.shynieke.statues.items.ItemTea;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@EventBusSubscriber
public class StatuesItems {

	public static ItemFood nugget, soup, tea, cup;
	public static Item core, player_compass, marshmallow_charred;
	public static ItemFood marshmallow, marshmallow_cooked, marshmallow_golden;

	public static ArrayList<Item> ITEMS = new ArrayList<>();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		nugget = registerItem(new ItemRoyalNugget(4, 0.1f, "royalnugget"));
		core = registerItem(new ItemStatueCore("statuecore"));
		soup = registerItem(new ItemMooshroomSoup(6, 0.3F, "mooshroomsoup"));
		tea = registerItem(new ItemTea("tea"));
		cup = registerItem(new ItemCup(1, 0.2F, "cup"));

		if (StatuesConfigGen.player.PlayerCompass) {
			player_compass = registerItem(new ItemPlayerCompass("playercompass"));
		}

		marshmallow = registerItem(new ItemMarshmallow(1, 0.1f, "marshmallow"));
		marshmallow_cooked = registerItem(new ItemMarshmallow(4, 0.2f, "marshmallowcooked"));
		marshmallow_golden = registerItem(new ItemMarshmallow(3, 0.6f, "marshmallowgolden").setAlwaysEdible());
		marshmallow_charred = registerItem(new ItemCharredMarshmallow("marshmallowcharred"));

		registry.registerAll(ITEMS.toArray(new Item[0]));
	}

	public static <T extends Item> T registerItem(T item) {
		ITEMS.add(item);
		return item;
	}
}
