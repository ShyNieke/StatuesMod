package com.svennieke.statues.init;

import com.google.common.base.Preconditions;
import com.svennieke.statues.Reference;
import com.svennieke.statues.items.PlayerCompassItem;
import com.svennieke.statues.items.StatueCharredMarshmallow;
import com.svennieke.statues.items.StatueCoreItem;
import com.svennieke.statues.items.StatueGoldenMarshmallow;
import com.svennieke.statues.items.StatueMooshroomSoup;
import com.svennieke.statues.items.StatueTeaItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatueItems {

	public static Item nugget, statue_core, soup, tea, cup;
	public static Item marshmallow, marshmallow_cooked, marshmallow_golden, marshmallow_charred;
	public static Item player_compass;

	public static ArrayList<Item> ITEMS = new ArrayList<>();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		cup = registerItem(new Item(itemBuilder().food(StatueFoods.CUP)), "cup");
		marshmallow = registerItem(new Item(itemBuilder().food(StatueFoods.MARSHMALLOW)), "marshmallow");
		marshmallow_charred = registerItem(new StatueCharredMarshmallow(itemBuilder()), "marshmallow_charred");
		marshmallow_cooked = registerItem(new Item(itemBuilder().food(StatueFoods.COOKED_MARSHMALLOW)), "marshmallow_cooked");
		marshmallow_golden = registerItem(new StatueGoldenMarshmallow(itemBuilder(), StatueFoods.GOLDEN_MARSHMALLOW), "marshmallow_golden");
		nugget = registerItem(new Item(itemBuilder().food(StatueFoods.ROYAL_NUGGET)), "royal_nugget");
		player_compass = registerItem(new PlayerCompassItem(itemBuilder()), "player_compass");
		soup = registerItem(new StatueMooshroomSoup(itemBuilder(), StatueFoods.SOUP), "mooshroom_soup");
		statue_core = registerItem(new StatueCoreItem(itemBuilder()), "statue_core");
		tea = registerItem(new StatueTeaItem(itemBuilder(), StatueFoods.TEA), "tea");

		registry.registerAll(ITEMS.toArray(new Item[0]));
	}

	public static <T extends Item> T registerItem(T item, String name)
	{
		item.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		Preconditions.checkNotNull(item, "registryName");
		ITEMS.add(item);
		return item;
	}

	private static Item.Properties itemBuilder()
	{
		return new Item.Properties().group(StatueTabs.STATUES_ITEMS);
	}
}
