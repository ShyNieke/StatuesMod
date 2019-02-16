package com.svennieke.statues.config;

import com.svennieke.statues.Statues;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.List;

import static net.minecraftforge.fml.Logging.CORE;

public class StatuesConfig {
//	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
//	public static final Common COMMON = new Common(BUILDER);
//	 
    public static class Common {
		//General
    	public final BooleanValue tier1Crafting;
        public final BooleanValue playerStatueAlternateRecipe; 
        public final DoubleValue oldDropChance;
        public final IntValue interactionTimer;
        public final BooleanValue craftableInteraction;    
        public final IntValue statueKillSource;
        
        //Player
    	public final BooleanValue playersDropStatue;
        public final IntValue playerDropChance; 
        public final IntValue playerStatueKillSource;
        public final BooleanValue playerCrafting;
        public final BooleanValue playerCompass;  
        
        //Other
        public final DoubleValue statueHardness; 
        public final IntValue soupStack;
        //public final BooleanValue antiAfk;
        
        //Messages
        public final ConfigValue<List<String>> info_messages;
        
        //Lucky Players
    	public final ConfigValue<List<String>> lucky_players;

    	//Events
    	public final BooleanValue halloweenSpawning; 
        public final IntValue fakeSpawningWeight;
        
        Common(ForgeConfigSpec.Builder builder) {
        	builder.comment("General server-side settings")
            		.push("general");
        	
        	tier1Crafting = builder
                    .comment("Tier 1 needs to be crafted with Statue Core,",
    						"disabling this makes tier 1 statues drop from mobs.")
					.worldRestart()
                    .define("tier1Crafting", true);
        	
        	playerStatueAlternateRecipe = builder
        			.comment("Setting this to true enables a recipe where the core is replaced by a diamond [ideal for singleplayer when tier 1 crafting is turned off]")
					.worldRestart()
        			.define("playerStatueAlternateRecipe", false);
        	
        	oldDropChance = builder
        			.comment("The drop chance of statues when statue drops is true", 
    						"This option only takes effect when Tier1Crafting is false")
        			.defineInRange("oldDropChance", 0.01, 0.0001, 1.0);
        	
        	interactionTimer = builder
        			.comment("The amount of time [in seconds] that you have to wait before being able to interact with the statues (tier 2 and higher)")
        			.defineInRange("interactionTimer", 60, 0, Integer.MAX_VALUE);
        	
        	craftableInteraction = builder
        			.comment("Setting this to false disables the tier 3, 4 and 5 recipes.")
					.worldRestart()
        			.define("craftableInteraction", true);
        	
        	statueKillSource = builder
        			.comment("Source of death that determines how the Statues drop [player statue has it's own config option]",
        					"ALL = All sources",
        					"PLAYER = Player only",
        					"PLAYER_FAKEPLAYER = Players / Fake players only")
        			.defineInRange("statueKillSource", 1, 0, 2);

			builder.pop();
        	builder.comment("Player Statue Settings")
        			.push("player");
        	
        	playersDropStatue = builder
                    .comment("Players drop their players Player Statue")
                    .define("playersDropStatue", true);

        	playerDropChance = builder
        			.comment("Changing this will change the chance a player has of dropping a Player Statue when killed by a player [1 in a x chance]")
        			.defineInRange("playerDropChance", 1, 1, Integer.MAX_VALUE);
        	
        	playerStatueKillSource = builder
        			.comment("Source of death that determines how the Player Statue drops",
        					"ALL = All sources",
        					"PLAYER = Player only",
        					"PLAYER_FAKEPLAYER = Players / Fake players only")
        			.defineInRange("playerStatueKillSource", 1, 0, 2);
        	
        	playerCrafting = builder
        			.comment("Setting this to true enables Player Statue Crafting")
					.worldRestart()
        			.define("playerCrafting", true);
        	
        	playerCompass = builder
        			.comment("When true Statues will add a player compass")
        			.define("playerCompass", true);

			builder.pop();
        	builder.comment("Other settings")
    		.push("othersettings");
    	
	    	statueHardness = builder
	                .comment("Changing this changes the amount of time needed to harvest a statue, Higher = more time needed. Lower = faster harvested " +
							"[0.6 is the same as vanilla grass]")
	                .defineInRange("statueHardness", 0.6D, 0.1D, 3.0D);
	
	    	soupStack = builder
	    			.comment("Changing this changes the stacksize of the mushroom soup")
	    			.defineInRange("soupStack", 8, 1, 64);
	    	
	//    	antiAfk = builder
	//    			.comment("This either disables or enables the anti-afk system that's in place")
	//    			.define("antiAfk", true);

			builder.pop();
	    	builder.comment("Messages")
    		.push("messages");
	
    		String[] messages = new String[]
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
	
    		info_messages = builder
            .comment("Adding lines / removing lines specifies what the informative statue can say")
            .define("info_messages", Arrays.asList(messages));

			builder.pop();
    		builder.comment("Lucky Players")
            .push("luckyplayers");
        	
    		String[] luckyplayers = new String[]
            		{
        				"iskall85"
            		};
    		
    		lucky_players = builder
                    .comment("Adding usernames will make these users have less luck with getting statues")
                    .define("lucky_players", Arrays.asList(luckyplayers));

			builder.pop();
    		builder.comment("Seasonal Events")
            .push("events");
        	
    		halloweenSpawning = builder
                    .comment("When enabled makes the fake mobs spawn during the month of October as a halloween event")
					.worldRestart()
                    .define("halloweenSpawning", true);

			fakeSpawningWeight = builder
    				.comment("The weigth of the fake mobs is divided by this number.")
					.worldRestart()
    				.defineInRange("fakeSpawningWeigth", 4, 1, 50);
    		
        	builder.pop();
        }
    }
    
//    public static final ForgeConfigSpec commonSpec = BUILDER.build();
    
    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
      
    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        LogManager.getLogger().debug(Statues.STATUESMOD, "Loaded Statues' config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
        LogManager.getLogger().fatal(CORE, "Statues' config just got changed on the file system!");
    }
}
