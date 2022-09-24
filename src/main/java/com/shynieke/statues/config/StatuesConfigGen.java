package com.shynieke.statues.config;

import com.shynieke.statues.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("statues.config.title")
public class StatuesConfigGen {

	@Config.Comment({"General settings"})
	public static final General general = new General();

	@Config.Comment({"Player Statue Settings"})
	public static final Player player = new Player();

	@Config.Comment({"Other settings"})
	public static final OtherStuff othersettings = new OtherStuff();

	@Config.Comment({"Other settings"})
	public static final TextMessages messages = new TextMessages();

	@Config.Comment({"Lucky Players"})
	public static final LuckyPlayers luckyplayers = new LuckyPlayers();

	@Config.Comment({"Seasonal Events"})
	public static final Events events = new Events();

	public enum EnumDeathSource {
		ALL,
		PLAYER,
		PLAYER_FAKEPLAYER
	}

	public static class General {

		@Config.RequiresMcRestart
		@Config.Comment("Disables all functional statue tiers (tiers that usually drop items) [Default: false]")
		public final boolean nonFunctional = false;

		@Config.RequiresMcRestart
		@Config.Comment("Tier 1 needs to be crafted with Statue Core (Default: true)" +
				" Disabling this makes tier1 statues drop from mobs.")
		public final boolean Tier1Crafting = true;

		@Config.RequiresMcRestart
		@Config.Comment("Setting this to true enables a recipe where the core is replaced by a diamond [ideal for singleplayer when tier 1 crafting is turned off] (Default: false)")
		public final boolean PlayerStatueAlternateRecipe = false;

		@Config.Comment("Setting this to true allows for aquatic statues to be fished up (Default: false)")
		public final boolean AquaticFishing = false;

		@Config.Comment("The drop chance of statues when statue drops is true (Default: 0.01)" +
				" [This option only takes effect when Tier1Crafting is false]")
		public final double OldDropChance = 0.01;

		@Config.Comment("The amount of time [in seconds] that you have to wait before being able to interact with the statues (tier 2 and higher)" +
				"(Default: 60)")
		public final int InteractionTimer = 60;

		@Config.RequiresMcRestart
		@Config.Comment("Setting this to false disables the tier 3,4 and 5 recipes. (Default: true)")
		public final boolean CraftableInteraction = true;

		@Config.Comment("Source of death that determines how the Statues drop [player statue has it's own config option] (Default: PLAYER)")
		public final EnumDeathSource StatueKillSource = EnumDeathSource.PLAYER;


		@Config.RequiresMcRestart
		@Config.Comment("Setting this to false disables the statue bat from spawning naturally. (Default: true)")
		public final boolean StatueBatSpawning = true;
	}

	public static class Player {

		@Config.Comment("Players drop their players Player Statue")
		public final boolean PlayersDropStatue = true;

		@Config.Comment("Changing this will change the chance a player has of dropping a Player Statue when killed by a player [1 in a x chance] (Default: 1)")
		public final int PlayerDropChance = 1;

		@Config.Comment("Source of death that determines how the Player Statue drops (Default: PLAYER)")
		public final EnumDeathSource PlayerStatueKillSource = EnumDeathSource.PLAYER;

		@Config.RequiresMcRestart
		@Config.Comment("Player Statue Crafting (Default: true)")
		public final boolean PlayerCrafting = true;

		@Config.RequiresMcRestart
		@Config.Comment("When true statues will add a player compass (Default: true)")
		public final boolean PlayerCompass = true;
	}

	public static class OtherStuff {

		@Config.RequiresMcRestart
		@Config.Comment("Changing this changes the amount of time needed to harvest a statue, Higher = more time needed. Lower = faster harvested " +
				"(Default: 0.6) [0.6 is the same as vanilla grass]")
		public final double StatueHardness = 0.6;

		@Config.RequiresMcRestart
		@Config.Comment("Changing this changes the stacksize of the mushroom soup (Default: 8)")
		public final int SoupStack = 8;

		@Config.Comment("This either disables or enables the anti-afk system that's in place (Default: true)")
		public final boolean antiAfk = true;
	}

	public static class TextMessages {
		@Config.Comment("Adding lines / removing lines specifies what the informative statue can say")
		public final String[] info_messages = new String[]
				{
						"Statues is still in beta not all mobs have a statue yet",
						"Chickens are not royal, prove me wrong by placing the right statue on a royal looking block",
						"Undead stop burning in water, would a statue burn on a block that looks like water?",
						"Fun Fact: I was supposed to be a christmas special item, but plans got changed and now I am here",
						"Pigs love mud how will they react on a more sandy block",
						"Did you know chicken jockeys exist even in statues? go ahead try it out place a baby zombie statue on a chicken statue",
						"Huge thanks to Xisuma for helping create his statue",
						"Only decorative statues can be changed to special statues",
						"Gone Fishing",
						"A hat to protect you from the heat, get it in a hot biome near you",
						"1-3 tall they seem, prickly is a bit mean, Give them a hat and see their true means",
						"A statue of undying can be found in the pockets of the Woodland residents",
						"Right click a fake mob with a statue core to prevent them from despawning",
						"Right click a player statue with a comparator to emit redstone when that player is online",
						"Right click a player statue with a compass to bound the compass to the last known location of the player",
						"A player and a creeper with blue fire in the middle",
						"How to make blue fire, if fire itself does not work"
				};

	}

	public static class LuckyPlayers {
		@Config.Comment("Adding usernames will make these users have less luck with getting statues")
		public final String[] lucky_players = new String[]
				{
						"iskall85"
				};

	}

	public static class Events {
		@Config.RequiresMcRestart
		@Config.Comment("When enabled makes the fake mobs spawn during the month of October as a halloween event (Default: true)")
		public final boolean halloweenSpawning = true;

		@Config.RequiresMcRestart
		@Config.Comment("The weigth of the fake mobs is divided by this number. (Default: 4)")
		@Config.RangeInt(min = 1)
		public final int fakeSpawningWeigth = 4;
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