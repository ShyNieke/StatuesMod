package com.shynieke.statues.config;

import com.shynieke.statues.Statues;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class StatuesConfig {

	public enum EnumDeathSource {
		ALL,
		PLAYER,
		PLAYER_FAKEPLAYER
	}

	public static class Common {

		public final DoubleValue statueDropChance;
		public final EnumValue<EnumDeathSource> statueKillSource;

		public final BooleanValue playerDropsStatue;
		public final EnumValue<EnumDeathSource> playerStatueKillSource;
		public final DoubleValue playerStatueDropChance;
		public final BooleanValue playerCompass;

		public final BooleanValue statueBatSpawning;

		//Lucky Players
		public final ConfigValue<List<? extends String>> lucky_players;

		//Messages
		public final ConfigValue<List<? extends String>> info_messages;

		Common(ForgeConfigSpec.Builder builder) {
			//General settings
			builder.comment("General settings")
					.push("general");

			statueBatSpawning = builder
					.comment("Setting this to false disables the statue bat from spawning naturally (Default: true)")
					.define("statueBatSpawning", true);

			builder.pop();

			//Statue drop settings
			builder.comment("Statue drop Settings")
					.push("drops");

			statueDropChance = builder
					.comment("The drop chance of statues when statue drops is true (Default: 0.01)")
					.defineInRange("statueDropChance", 0.01, 0, 1.0);

			statueKillSource = builder
					.comment("Source of death that determines how the Statues drop (Default: \"PLAYER\") [player statue has it's own config option]",
							"ALL = All sources",
							"PLAYER = Player only",
							"PLAYER_FAKEPLAYER = Players / Fake players only")
					.defineEnum("statueKillSource", EnumDeathSource.PLAYER);

			builder.pop();

			//Player settings
			builder.comment("Player Statue Settings")
					.push("player");

			playerDropsStatue = builder
					.comment("When true players will drop Player Statues when killed (Default: true)")
					.define("playerDropsStatue", true);

			playerStatueKillSource = builder
					.comment("Source of death that determines how the Player Statue drops (Default: \"PLAYER\") [player statue has it's own config option]",
							"ALL = All sources",
							"PLAYER = Player only",
							"PLAYER_FAKEPLAYER = Players / Fake players only")
					.defineEnum("playerDropsKillSource", EnumDeathSource.PLAYER);

			playerStatueDropChance = builder
					.comment("The drop chance of player statues when playerDropsStatue is enabled (Default: 1.0)")
					.defineInRange("statueDropChance", 1.0, 0, 1.0);

			playerCompass = builder
					.comment("When true Statues will add a player compass (Default: true)")
					.define("playerCompass", true);

			builder.pop();

			//Info messages
			builder.comment("Info messages")
					.push("info_messages");

			String[] messages = new String[]
					{
							"Statues is still in beta not all mobs have a statue yet",
							"Chickens are not royal, prove me wrong by placing the right statue on a royal looking block",
							"Undead stop burning in water, would a statue burn on a block that looks like water?",
							"Fun Fact: I was supposed to be a christmas special item, but plans got changed and now I am here",
							"Pigs love mud how will they react on a more sandy block",
							"Did you know chicken jockeys exist even in statues? go ahead try it out place a baby zombie statue on a chicken statue",
							"Huge thanks to Xisuma for helping create his statue",
							"Gone Fishing",
							"1-3 tall they seem, prickly is a bit mean, Give them a hat and see their true means",
							"Right click a fake mob with a statue core to prevent them from despawning",
							"Right click a player statue with a comparator to emit redstone when that player is online",
							"Right click a player statue with a compass to bound the compass to the last known location of the player",
							"A player and a creeper with blue fire in the middle",
							"How to make blue fire, if fire itself does not work",
							"Rename a bee 'Trans Bee' to show support for trans people",
							"Using a Statue Core on a player statue will make them grow, and will add more features to it",
							"The bat has returned, find them in caves near you, or in your local hellish dimension"
					};

			info_messages = builder
					.comment("Adding lines / removing lines specifies what the informative statue can say")
					.defineList("info_messages", Arrays.asList(messages), o -> (o instanceof String));

			builder.pop();

			// Lucky Players
			builder.comment("Lucky players")
					.push("lucky_players");

			String[] luckyPlayers = new String[]
					{
							"iskall85"
					};

			lucky_players = builder
					.comment("Adding usernames will make these users have less luck with getting statues")
					.defineList("lucky_players", Arrays.asList(luckyPlayers), o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		Statues.LOGGER.debug("Loaded Statues' config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading configEvent) {
		Statues.LOGGER.fatal("Statues' config just got changed on the file system!");
	}
}
