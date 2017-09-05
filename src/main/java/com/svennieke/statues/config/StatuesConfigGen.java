package com.svennieke.statues.config;

import com.svennieke.statues.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("statues.config.title")
public class StatuesConfigGen {
	
	@Config.Comment({"General settings"})
	public static General general = new General();
	
	@Config.Comment({"Other settings"})
	public static OtherStuff othersettings = new OtherStuff();
	
	@Config.Comment({"Other settings"})
	public static TextMessages messages = new TextMessages();
	
	@Config.Comment({"Statue interactive customisation settings, inputting invalid names will make that value null (null = doesn't drop)"})
	public static Statues statues = new Statues();
	
	public static class General{
		
		@Config.RequiresMcRestart
		@Config.Comment("Tier 1 needs to be crafted with Statue Core (Default: true)" +
						"Disabling this makes tier1 statues drop from mobs.")
		public boolean Tier1Crafting = true;
		
		@Config.Comment("The drop chance of statues when statue drops is true (Default: 0.01)" +
						" [This option only takes effect when NewSystem is false]")
		public double OldDropChance = 0.01;
		
		@Config.Comment("The amount of time [in seconds] that you have to wait before being able to interact with the statues (tier 2 and higher)"+
						"(Default: 60)")
		public int InteractionTimer = 60;
		
		@Config.RequiresMcRestart
		@Config.Comment("Setting this to false disables the tier 3 and 4 recipes. (Default: true)")
		public boolean CraftableInteraction = true;
	}
	
	public static class OtherStuff{
		
		@Config.RequiresMcRestart
		@Config.Comment("Changing this changes the amount of time needed to harvest a statue, Higher = more time needed. Lower = faster harvested "+
						"(Default: 0.6) [0.6 is the same as vanilla grass]")
		public double StatueHardness = 0.6;
		
		@Config.RequiresMcRestart
		@Config.Comment("Changing this changes the stacksize of the mushroom soup (Default: 8)")
		public int SoupStack = 8;
	}
	
	public static class TextMessages{
		@Config.Comment("Adding lines / removing lines specifies what the informative statue can say")
		public String[] info_messages = new String[]
				{
				"Statues is still in beta not all mobs have a statue yet", 
				"Chickens are not royal, prove me wrong by placing the right statue on a royal looking block",
				"Undead stop burning in water, would a statue burn on a block that looks like water?",
				"Fun Fact: I was supposed to be a christmas special item, but plans got changed and now I am here"
				};
		
	}

	public static class Statues{
		public final BabyZombie babyzombie = new BabyZombie();
		public final Blaze blaze = new Blaze();
		public final Chicken chicken = new Chicken();
		public final Cow cow = new Cow();
		public final Creeper creeper = new Creeper();
		public final Mooshroom mooshroom = new Mooshroom();
		public final Pig pig = new Pig();
		public final Rabbit rabbit = new Rabbit();
		public final Sheep sheep = new Sheep();
		public final SheepShaven sheepshaven = new SheepShaven();
		public final Slime slime = new Slime();
		public final SnowGolem snowgolem = new SnowGolem();
		public final Squid squid = new Squid();
		public final Villager villager = new Villager();
		
		
		public static class BabyZombie{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:rotten_flesh";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:iron_nugget";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Blaze{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:blaze_powder";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:blaze_rod";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Chicken{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:feather";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:egg";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Cow{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Creeper{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:gunpowder";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Mooshroom{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Pig{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "minecraft:porkchop";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Rabbit{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:rabbit_hide";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:rabbit";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "minecraft:rabbit_foot";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Sheep{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:mutton";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class SheepShaven{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:wool";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "minecraft:mutton";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Slime{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:slime_ball";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class SnowGolem{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "minecraft:snowball";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "snowball:pumpkin";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Squid{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "minecraft:dye";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
		
		public static class Villager{
			@Config.Comment("Changing this changes the item that drops 100% of the time")
			public String item1 = "";
			
			@Config.Comment("Metadata for the item")
			public int item1meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 50% of the time")
			public String item2 = "";
			
			@Config.Comment("Metadata for the item")
			public int item2meta = 0;
			
			@Config.Comment("Changing this changes the item that drops 10% of the time")
			public String item3 = "minecraft:emerald";
			
			@Config.Comment("Metadata for the item")
			public int item3meta = 0;
			
		}
	}
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}