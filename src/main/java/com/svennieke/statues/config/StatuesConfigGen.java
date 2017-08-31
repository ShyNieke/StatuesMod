package com.svennieke.statues.config;

import java.util.Random;

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
				"Chickens are not royal, Prove me wrong by placing the right statue on a royal looking block",
				"Undead stop burning in water Would a statue burn on a block that looks like water?",
				"Fun Fact: i was suposed to be a christmass special item, but plans got changed and now i am here"
				};
		
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