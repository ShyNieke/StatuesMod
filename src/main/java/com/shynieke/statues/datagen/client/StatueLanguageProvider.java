package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class StatueLanguageProvider extends LanguageProvider {

	public StatueLanguageProvider(PackOutput packOutput) {
		super(packOutput, Reference.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + Reference.MOD_ID + ".blocks", "Statue Blocks");
		add("itemGroup." + Reference.MOD_ID + ".items", "Statue Items");
		add("item.statues.statue_core.desc", "Alpha");

		addBlock(StatueRegistry.STATUE_TABLE, "S.T.A.T.U.E");
		addBlock(StatueRegistry.BABY_ZOMBIE_STATUE, "Baby Zombie Statue");
		addBlock(StatueRegistry.ANGRY_BEE_STATUE, "Angry Bee Statue");
		addBlock(StatueRegistry.BEE_STATUE, "Bee Statue");
		addBlock(StatueRegistry.BLAZE_STATUE, "Blaze Statue");
		addBlock(StatueRegistry.BUMBO_STATUE, "Bumbo Statue");
		addBlock(StatueRegistry.CAMPFIRE_STATUE, "Campfire Statue");
		addBlock(StatueRegistry.CHICKEN_JOCKEY_STATUE, "Chicken Jockey Statue");
		addBlock(StatueRegistry.CHICKEN_STATUE, "Chicken Statue");
		addBlock(StatueRegistry.COD_STATUE, "Cod Statue");
		addBlock(StatueRegistry.COW_STATUE, "Cow Statue");
		addBlock(StatueRegistry.CREEPER_STATUE, "Creeper Statue");
		addBlock(StatueRegistry.DISPLAY_STAND, "Display Stand");
		addBlock(StatueRegistry.DOLPHIN_STATUE, "Dolphin Statue");
		addBlock(StatueRegistry.DROWNED_STATUE, "Drowned Statue");
		addBlock(StatueRegistry.ENDERMAN_STATUE, "Enderman Statue");
		addBlock(StatueRegistry.ENDERMITE_STATUE, "Endermite Statue");
		addBlock(StatueRegistry.EVOKER_STATUE, "Evoker Statue");
		addBlock(StatueRegistry.FLOOD_STATUE, "Flood Statue");
		addBlock(StatueRegistry.GHAST_STATUE, "Ghast Statue");
		addBlock(StatueRegistry.GUARDIAN_STATUE, "Guardian Statue");
		addBlock(StatueRegistry.HUSK_STATUE, "Husk Statue");
		addBlock(StatueRegistry.INFO_STATUE, "Informative Statue");
		addBlock(StatueRegistry.KING_CLUCK_STATUE, "King Cluck Statue");
		addBlock(StatueRegistry.MAGMA_STATUE, "Magma Cube Statue");
		addBlock(StatueRegistry.MOOSHROOM_STATUE, "Mooshroom Statue");
		addBlock(StatueRegistry.PEBBLE, "Pebble");
		addBlock(StatueRegistry.PIG_STATUE, "Pig Statue");
		addBlock(StatueRegistry.PLAYER_STATUE, "Player Statue");
		addBlock(StatueRegistry.PUFFERFISH_MEDIUM_STATUE, "Slightly Bloated Pufferfish Statue");
		addBlock(StatueRegistry.PUFFERFISH_SMALL_STATUE, "Small Pufferfish Statue");
		addBlock(StatueRegistry.PUFFERFISH_STATUE, "Bloated Pufferfish Statue");
		addBlock(StatueRegistry.RABBIT_BR_STATUE, "Rabbit Statue");
		addBlock(StatueRegistry.RABBIT_BS_STATUE, "Rabbit Statue");
		addBlock(StatueRegistry.RABBIT_BW_STATUE, "Rabbit Statue");
		addBlock(StatueRegistry.RABBIT_GO_STATUE, "Rabbit Statue");
		addBlock(StatueRegistry.RABBIT_WH_STATUE, "Rabbit Statue");
		addBlock(StatueRegistry.RABBIT_WS_STATUE, "Rabbit Statue");
		addBlock(StatueRegistry.SALMON_STATUE, "Salmon Statue");
		addBlock(StatueRegistry.SHEEP_SHAVEN_STATUE, "Shaven Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_BLACK, "Black Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_BLUE, "Blue Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_BROWN, "Brown Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_CYAN, "Cyan Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_GRAY, "Gray Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_GREEN, "Green Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, "Light Blue Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_LIGHT_GRAY, "Light Gray Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_LIME, "Lime Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_MAGENTA, "Magenta Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_ORANGE, "Orange Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_PINK, "Pink Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_PURPLE, "Purple Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_RED, "Red Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_WHITE, "White Sheep Statue");
		addBlock(StatueRegistry.SHEEP_STATUE_YELLOW, "Yellow Sheep Statue");
		addBlock(StatueRegistry.SHULKER_STATUE, "Shulker Statue");
		addBlock(StatueRegistry.SLIME_STATUE, "Slime Statue");
		addBlock(StatueRegistry.SNOW_GOLEM_STATUE, "Snow Man Statue");
		addBlock(StatueRegistry.SOMBRERO, "Sombrero");
		addBlock(StatueRegistry.SPIDER_STATUE, "Spider Statue");
		addBlock(StatueRegistry.SQUID_STATUE, "Squid Statue");
		addBlock(StatueRegistry.TOTEM_OF_UNDYING_STATUE, "Statue Of Undying");
		addBlock(StatueRegistry.TRANS_BEE_STATUE, "Trans Bee Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_B, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_BB, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_BE, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_BM, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_BMB, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_BMS, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_E, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_ES, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_HB, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_SB, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_SD, "Tropical Fish Statue");
		addBlock(StatueRegistry.TROPICAL_FISH_SS, "Tropical Fish Statue");
		addBlock(StatueRegistry.TURTLE_STATUE, "Turtle Statue");
		addBlock(StatueRegistry.VILLAGER_BR_STATUE, "Villager Statue");
		addBlock(StatueRegistry.VILLAGER_GR_STATUE, "Villager Statue");
		addBlock(StatueRegistry.VILLAGER_PU_STATUE, "Villager Statue");
		addBlock(StatueRegistry.VILLAGER_WH_STATUE, "Villager Statue");
		addBlock(StatueRegistry.WASTELAND_STATUE, "Wasteland Pig Statue");
		addBlock(StatueRegistry.WITCH_STATUE, "Witch Statue");
		addBlock(StatueRegistry.ZOMBIE_STATUE, "Zombie Statue");
		addBlock(StatueRegistry.ELDER_GUARDIAN_STATUE, "Elder Guardian Statue");
		addBlock(StatueRegistry.RAVAGER_STATUE, "Ravager Statue");
		addBlock(StatueRegistry.BROWN_MOOSHROOM_STATUE, "Brown Mooshroom Statue");
		addBlock(StatueRegistry.VINDICATOR_STATUE, "Vindicator Statue");
		addBlock(StatueRegistry.PILLAGER_STATUE, "Pillager Statue");
		addBlock(StatueRegistry.FOX_STATUE, "Fox Statue");
		addBlock(StatueRegistry.FOX_SNOW_STATUE, "Snow Fox Statue");
		addBlock(StatueRegistry.CAT_BLACK_STATUE, "Black Statue");
		addBlock(StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, "British Shorthair Cat Statue");
		addBlock(StatueRegistry.CAT_CALICO_STATUE, "Calico Cat Statue");
		addBlock(StatueRegistry.CAT_JELLIE_STATUE, "Jellie Cat Statue");
		addBlock(StatueRegistry.CAT_PERSIAN_STATUE, "Persian Cat Statue");
		addBlock(StatueRegistry.CAT_RAGDOLL_STATUE, "Ragdoll Cat Statue");
		addBlock(StatueRegistry.CAT_RED_STATUE, "Red Cat Statue");
		addBlock(StatueRegistry.CAT_SIAMESE_STATUE, "Siamese Cat Statue");
		addBlock(StatueRegistry.CAT_TABBY_STATUE, "Tabby Cat Statue");
		addBlock(StatueRegistry.CAT_TUXEDO_STATUE, "Tuxedo Cat Statue");
		addBlock(StatueRegistry.CAT_WHITE_STATUE, "White Cat Statue");
		addBlock(StatueRegistry.PANDA_ANGRY_STATUE, "Angry Panda Statue");
		addBlock(StatueRegistry.PANDA_BROWN_STATUE, "Brown Panda Statue");
		addBlock(StatueRegistry.PANDA_LAZY_STATUE, "Lazy Panda Statue");
		addBlock(StatueRegistry.PANDA_NORMAL_STATUE, "Normal Panda Statue");
		addBlock(StatueRegistry.PANDA_PLAYFUL_STATUE, "Playful Panda Statue");
		addBlock(StatueRegistry.PANDA_WEAK_STATUE, "Weak Panda Statue");
		addBlock(StatueRegistry.PANDA_WORRIED_STATUE, "Worried Panda Statue");
		addBlock(StatueRegistry.DETECTIVE_PLATYPUS, "Detective Platypus Statue");
		addBlock(StatueRegistry.TROPIBEE, "Tropibee Statue");
		addBlock(StatueRegistry.EAGLE_RAY, "Eagle Ray Statue");
		addBlock(StatueRegistry.SLABFISH, "Slabfish Statue");
		addBlock(StatueRegistry.AZZARO, "Azzaro Statue");
		addBlock(StatueRegistry.ALLAY_STATUE, "Allay Statue");
		addBlock(StatueRegistry.AXOLOTL_LUCY_STATUE, "Axolotl Statue");
		addBlock(StatueRegistry.AXOLOTL_WILD_STATUE, "Axolotl Statue");
		addBlock(StatueRegistry.AXOLOTL_GOLD_STATUE, "Axolotl Statue");
		addBlock(StatueRegistry.AXOLOTL_CYAN_STATUE, "Axolotl Statue");
		addBlock(StatueRegistry.AXOLOTL_BLUE_STATUE, "Axolotl Statue");
		addBlock(StatueRegistry.FROG_TEMPERATE_STATUE, "Frog Statue");
		addBlock(StatueRegistry.FROG_WARM_STATUE, "Frog Statue");
		addBlock(StatueRegistry.FROG_COLD_STATUE, "Frog Statue");
		addBlock(StatueRegistry.TADPOLE_STATUE, "Tadpole Statue");
		addBlock(StatueRegistry.WARDEN_STATUE, "Warden Statue");

		addBlock(StatueRegistry.CORE_FLOWER, "Core Flower");
		addBlock(StatueRegistry.CORE_FLOWER_CROP, "Core Flower");

		addItem(StatueRegistry.NUGGET, "Royal Nugget");
		addItem(StatueRegistry.SOUP, "Mooshroom Soup");
		addItem(StatueRegistry.STATUE_CORE, "Statue Core");
		addItem(StatueRegistry.TEA, "Cup Of Tea");
		addItem(StatueRegistry.CUP, "Edible Cup");
		addItem(StatueRegistry.MARSHMALLOW, "Marshmallow");
		addItem(StatueRegistry.MARSHMALLOW_COOKED, "Cooked Marshmallow");
		addItem(StatueRegistry.MARSHMALLOW_GOLDEN, "Golden Marshmallow");
		addItem(StatueRegistry.MARSHMALLOW_CHARRED, "Charred Marshmallow");
		addItem(StatueRegistry.PLAYER_COMPASS, "Player Compass");
		addItem(StatueRegistry.PLAYER_STATUE_SPAWN_EGG, "Player Statue Spawn Egg");
		addItem(StatueRegistry.STATUE_BAT_SPANW_EGG, "Statue Bat Spawn Egg");
		addItem(StatueRegistry.CORE_ARMOR_TRIM_SMITHING_TEMPLATE, "Core Armor Trim Smithing Template");
		addItem(StatueRegistry.CORE_FLOWER_SEED, "Core Flower Seed");
		addItem(StatueRegistry.STATUE_CORE_POTTERY_SHERD, "Statue Core Pottery Sherd");

		add("trim_pattern.statues.core", "Statue Core");

		add("statues.cluckington.info", "Also known as Cluckington");
		add("statues.royalnugget.info", "Made for and from royals");
		add("statues.villager.info", "WIP, Trades in the future.");
		add("statues.tea.info", "Now with less milk.");
		add("statues.cup.info", "I can't believe its edible.");
		add("statues.last.known.location", "Tracking last known location of, %s");

		addEntityType(StatueRegistry.STATUE_BAT, "Statue Bat");
		addEntityType(StatueRegistry.PLAYER_STATUE_ENTITY, "Player Statue");

		add("statues.container.shulker_statue", "Shulker Statue");
		add("statues.container.statue_table", "S.T.A.T.U.E");

		add("statues.playerstatue.gui.title", "Player Statue");
		add("statues.playerstatue.gui.label.small", "Small");
		add("statues.playerstatue.gui.label.locked", "Locked");
		add("statues.playerstatue.gui.label.name_visible", "Name Visible");
		add("statues.playerstatue.gui.label.gravity", "Zero Gravity");
		add("statues.playerstatue.gui.label.y_offset", "Y Offset");
		add("statues.playerstatue.gui.label.rotation", "Rotation");
		add("statues.playerstatue.gui.label.head", "Head");
		add("statues.playerstatue.gui.label.body", "Body");
		add("statues.playerstatue.gui.label.left_leg", "Left Leg");
		add("statues.playerstatue.gui.label.right_leg", "Right Leg");
		add("statues.playerstatue.gui.label.left_arm", "Left Arm");
		add("statues.playerstatue.gui.label.right_arm", "Right Arm");
		add("statues.playerstatue.gui.label.copy", "Copy");
		add("statues.playerstatue.gui.label.paste", "Paste");

		add("statues.player.compass.dimension.failure", "%s is in another dimension.");
		add("statues.player.compass.offline", "%s can not be found, perhaps the player isn't online.");

		addSubtitle(StatueSounds.WASTELAND_HELLO, "Wasteland pig statue says hello");
		addSubtitle(StatueSounds.WASTELAND_ONWARDS, "Wasteland pig statue says to go onwards");
		addSubtitle(StatueSounds.WASTELAND_TEA, "Wasteland pig statue talks about tea");

		addSubtitle(StatueSounds.CAMPFIRE_HELLO, "Campfire statue says hello");
		addSubtitle(StatueSounds.CAMPFIRE_GREETINGS, "Campfire statue greets you");
		addSubtitle(StatueSounds.CAMPFIRE_BYE, "Campfire statue says bye");
		addSubtitle(StatueSounds.CAMPFIRE_SNACKS, "Campfire statue talks about snacks");
		addSubtitle(StatueSounds.CAMPFIRE_COLD, "Campfire statue talks about being cold");
		addSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW, "Campfire statue talks about marshmallows");

		add("option.statues.player.name", "Player Statue Username?");
		add("option.statues.player.data", "Player Statue UUID?");
		add("option.statues.statue.timer", "Statue Timer Info?");

		add("tooltip.statues.player.info", "Player Username: ");
		add("tooltip.statues.player.info2", "Player UUID: ");
		add("tooltip.statues.timer", "Cooldown percentage: ");
		add("tooltip.statues.timer.finished", "Ready for interaction");

		add("gui.statue.loot", "Statue Loot");
		add("gui.statue.fill", "Filling Interactions");
		add("gui.statues.statue_table.chisel.tooltip", "Chisel");
		add("gui.statues.statue_table.invalid_recipe.tooltip", "Invalid Recipe");

		add("statues.networking.player_statue_screen.failed", "Failed to open Player Statue Screen: %s");
		add("statues.networking.player_statue_sync.failed", "Failed to sync Player Statue: %s");
		add("statues.networking.table.failed", "Failed to sync Statue Table: %s");

		add("curios.identifier.statue", "Statue");

		add("statues.gui.jei.category.loot", "Statue Loot");
		add("statues.gui.jei.category.upgrade", "Statue Upgrade");
		add("statues.gui.jei.statue.info", "%s can be obtained as a %s drop chance from killing %s");
		add("statues.gui.jei.statue_core.info", "%s can be obtained by killing %s");
		add("statues.gui.jei.statue_core.info2", "The %s just like the vanilla one can be found in dark areas but be careful!");
		add("statues.gui.jei.statue_table.info", "The Statue Upgrading System allows players to upgrade and level up their mob statues. This entry will provide an overview of how the system works and what upgrades are available.");
		add("statues.gui.jei.statue_table.info2", "To start upgrading a statue, players first need to obtain a statue of the desired mob.");
		add("statues.gui.jei.statue_table.info3", "Once the player has obtained a Statue and a Statue Core, they need to craft a §c§lS.T.A.T.U.E§r (\"Statue Table for Applying Tiered Upgrade Enhancements\") and insert the Statue Core and desired statue. After pressing the \"Chisel\" button, the statue will be upgraded to a state in which it can level up.");
		add("statues.gui.jei.statue_table.info4", "After upgrading the statue, players can level it up by killing 10 more of the corresponding mob while the statue is in their inventory. Every 10 mobs will unlock an upgrade slot up to a max of 16.");
		add("statues.gui.jei.statue_table.info5", "There are several different upgrades available for the statues:");
		add("statues.gui.jei.statue_table.info6", "§lGlowing§r - Allows the statue to emit light\n§lUnglowing§r - Removes previously applied glowing\n§lSpawner§r - Allows the statue to spawn it's mob every so often\n§lDespawner§r - Disallows it's mob from spawning in the nearby area");
		add("statues.gui.jei.statue_table.info7", "§lMob Killer§r - Kills the mob if it gets nearby (1 = Regular drops, 2 = Player drops, 3 = Player drops + XP)\n§lLooting§r - Allows the statue to generate loot\n§lAutomation§r - Allows exporting of loot\n§lSpeed§r - Speeds up the interaction/spawning timer");
		add("statues.gui.jei.statue_table.info8", "§lInteraction§r - Allows special interactions\n§lSound§r - Allows the statue to make sound when right-clicked or powered");


		add("statues.info.level", "Level:");
		add("statues.info.kills", "Kills:");
		add("statues.info.upgrade_slots", "Upgrade Slots:");

		add("statues.upgrade.upgrade.name", "Upgrade");
		add("statues.upgrade.glowing.name", "Glowing");
		add("statues.upgrade.unglowing.name", "Unglowing");
		add("statues.upgrade.spawner.name", "Spawner");
		add("statues.upgrade.despawner.name", "Despawner");
		add("statues.upgrade.mob_killer.name", "Mob Killer");
		add("statues.upgrade.looting.name", "Looting");
		add("statues.upgrade.automation.name", "Automation");
		add("statues.upgrade.speed.name", "Speed");
		add("statues.upgrade.interaction.name", "Interaction");
		add("statues.upgrade.sound.name", "Sound");

		//FakePlayer
		add("fakeplayer.statue", "Statue");

		//Command
		add("commands.statues.upgrade.too_high", "Chosen tier is invalid for this upgrade, the highest level is %s");
		add("commands.statues.upgrade.success", "%s has been upgraded");
		add("commands.statues.upgrade.invalid", "%s is not an upgradable statue");

		//Patchouli
		add("info.statues.book.name", "Statues");
		add("info.statues.book.subtitle", "Little useful mobs");
		add("info.statues.book.landing", "A book to help figure out what statues can do for you.");

		add("info.statues.book.info.name", "Information");
		add("info.statues.book.info.desc", "General info about Statues and it's features.");

		add("info.statues.book.upgrading.entry.name", "Upgrading");
		add("info.statues.book.upgrading.text1", "The Statue Upgrading System allows players to upgrade and level up their mob statues. This entry will provide an overview of how the system works and what upgrades are available.");
		add("info.statues.book.upgrading.text2", "To start upgrading a statue, players first need to obtain a statue of the desired mob. This can be done by killing the mob, with a 1%% chance of obtaining the statue by default (which can be changed in the config). In addition to the statue itself, players also need to obtain a $(l:info/core)Statue Core$().");
		add("info.statues.book.upgrading.text3", "Once the player has obtained a statue and a Statue Core, they need to craft a $(l:info/statue_table)S.T.A.T.U.E$() (\"Statue Table for Applying Tiered Upgrade Enhancements\") and insert the Statue Core and desired statue. After pressing the \"Chisel\" button, the statue will be upgraded to a state in which it can level up.");
		add("info.statues.book.upgrading.text4", "After upgrading the statue, players can level it up by killing 10 more of the corresponding mob while the statue is in their inventory. Every 10 mobs will unlock an upgrade slot up to a max of 16.");
		add("info.statues.book.upgrading.text5", "There are several different upgrades available for the statues:$(br)$(l)Glowing$() - Allows the statue to emit light$(br)$(l)Unglowing$() - Removes previously applied glowing$(br)$(l)Spawner$() - Allows the statue to spawn it's mob every so often$(br)$(l)Despawner$() - Disallows it's mob from spawning in the nearby area");
		add("info.statues.book.upgrading.text6", "$(l)Mob Killer$() - Kills the mob if it gets nearby (1 = Regular drops, 2 = Player drops, 3 = Player drops + XP)$(br)$(l)Looting$() - Allows the statue to generate loot$(br)$(l)Automation$() - Allows exporting of loot$(br)$(l)Speed$() - Speeds up the interaction/spawning timer");
		add("info.statues.book.upgrading.text7", "$(l)Interaction$() - Allows special interactions$(br)$(l)Sound$() - Allows the statue to make sound when right-clicked or powered");

		add("info.statues.book.core.entry.name", "Statue Core");
		add("info.statues.book.core.text1", "Statue Core's are dropped by the Statue Bat.$(br)The Statue bat just like the vanilla one can be found in dark areas but beware! It hurts you back if you try to kill it.");

		add("info.statues.book.statue_table.entry.name", "S.T.A.T.U.E.");
		add("info.statues.book.statue_table.text1", "The S.T.A.T.U.E. or Statue Table for Applying Tiered Upgrade Enhancements is a worktable that can be used to upgrade Statues.");


		add("statues.modeltype.AUTO", "Auto");
		add("statues.modeltype.DEFAULT", "Default");
		add("statues.modeltype.SLIM", "Slim");
		add("statues.playerstatue.gui.label.model_type", "Model Type");
		add("statues.playerstatue.gui.label.position", "Position");

		addAdvancement("root", "Statues", "Killing a mob may sometimes drop a smaller one");
		addAdvancement("player_statue", "A statue of a friend or foe", "Obtain a Player Statue by having to respawn thanks to a player");
		addAdvancement("elder_guardian_statue", "Slaying the elders of the monuments", "Obtain a Elder Guardian Statue");
		addAdvancement("ravager_statue", "Ravaging through the statues", "Obtain a Ravager Statue");
		addAdvancement("zombie_statue", "Just wants to nibble on your brains", "Obtain a Zombie Statue");
		addAdvancement("baby_zombie_statue", "Baby undead", "Obtain a Baby Zombie Statue");
		addAdvancement("flood_statue", "Let the flood happen", "You found the way to create Docm77's secret statue");
		addAdvancement("husk_statue", "Letting a zombie dry up in the sun... would be funny if that worked", "Obtain a Husk Statue");
		addAdvancement("creeper_statue", "It won't blow up this time", "Obtain a Creeper Statue");
		addAdvancement("spider_statue", "BURN IT WITH FIRE!", "Obtain a Spider Statue");
		addAdvancement("slime_statue", "You found a Slime chunk!", "Obtain a Slime Statue");
		addAdvancement("drowned_statue", "Drowning a zombie statue does not work", "Obtain a Drowned Statue");
		addAdvancement("guardian_statue", "Slaying the protectors of the ocean", "Obtain a Guardian Statue");
		addAdvancement("campfire_statue", "Created blue fire, the old fashioned way", "Obtain Etho's secret statue");
		addAdvancement("pig_statue", "You pigged up a Statue", "Obtain a Pig Statue");
		addAdvancement("wasteland_statue", "Riding the pigs!", "You found Xisuma's secret statue");
		addAdvancement("statue_table", "S.T.A.T.U.E", "Wait you can upgrade statues?!");
		addAdvancement("display_stand", "A fancy way to display statues and more!", "You cut the Nether Quartz in just the right pieces");
		addAdvancement("info_statue", "Question about Statues? just right click me", "Crafted an Informative Statue");
		addAdvancement("bumbo_statue", "Bumbo's legacy", "Put a hat on a cactus");
		addAdvancement("witch_statue", "Bewitched!", "Obtain a Witch Statue");
		addAdvancement("pillager_statue", "Crossbow Included", "Obtain a Pillager Statue");
		addAdvancement("vindicator_statue", "Can I axe you a question", "Obtain a Vindicator Statue");
		addAdvancement("evoker_statue", "Wooolooolooo", "Obtain an Evoker Statue");
		addAdvancement("enderman_statue", "It won't teleport around... or at least it should not", "Obtain an Enderman Statue");
		addAdvancement("endermite_statue", "That pearl was in fact infected", "Obtain an Endermite Statue");
		addAdvancement("shulker_statue", "Relocating the City citizen", "Obtain a Shulker Statue");
		addAdvancement("cod_statue", "There are more fish in the sea", "Obtain a Cod Statue");
		addAdvancement("dolphin_statue", "A playful friend", "Obtain a Dolhpin Statue");
		addAdvancement("pufferfish_small_statue", "It is so Smoll protect it all cost!", "Obtain the smallest Pufferfish Statue");
		addAdvancement("pufferfish_medium_statue", "Don't scare it", "Obtain a slightly bloated Pufferfish Statue");
		addAdvancement("pufferfish_statue", "You scared it!", "Obtain a bloated Pufferfish Statue");
		addAdvancement("salmon_statue", "Going upstream!", "Obtain a Salmon Statue");
		addAdvancement("squid_statue", "Release the kraken!", "Obtain a Squid Statue");
		addAdvancement("turtle_statue", "Does it live in a sewer?", "Obtain a Turtle Statue");
		addAdvancement("tropical_fish_statue", "Tropical Fish statue", "Obtain any Tropical Fish Statue");
		addAdvancement("tropical_fish_all_statue", "Extinction of the Coral Reef", "Obtain all Tropical Fish Statue");
		addAdvancement("cow_statue", "This one is amoozing", "Obtain a Cow Statue");
		addAdvancement("mooshroom_statue", "Poisonous cows?", "Obtain a Red Mooshroom Statue");
		addAdvancement("brown_mooshroom_statue", "Fungi Cows?", "Obtain a Brown Mooshroom Statue");
		addAdvancement("sheep_statue", "Sheep Statue", "Obtain any Sheep Statue");
		addAdvancement("sheep_all_statue", "Herding Sheep", "Obtain all Sheep Statue");
		addAdvancement("snow_golem_statue", "Do you want to kill a Snow Man", "Obtain a Snow Man Statue");
		addAdvancement("chicken_statue", "Clearly the chicken came first, there is no egg statue", "Obtain a Chicken Statue");
		addAdvancement("king_cluck_statue", "A royal chicken, who would have thought", "You managed to make a chicken royal!");
		addAdvancement("chicken_jockey_statue", "Zombie loves chicken", "You successfully created a Chicken Jockey");
		addAdvancement("cat_statue", "A purrfect catch", "Collect any Cat Statue");
		addAdvancement("cat_all_statue", "Catastrophic collection", "Collect all Cat Statues");
		addAdvancement("rabbit_statue", "Rabbit statue", "Collect any Rabbit Statue");
		addAdvancement("rabbit_all_statue", "Just Caerbannog missing", "Collect all Rabbit Statues");
		addAdvancement("panda_statue", "Nanananana pandaaaaa", "Collect any Panda Statue");
		addAdvancement("panda_all_statue", "Extinction of the bamboo", "Collect all Panda Statues");
		addAdvancement("fox_statue", "Burning it would make it a firefox right?", "Obtain a Fox Statue");
		addAdvancement("fox_snow_statue", "Snow fox given", "Obtain a Snow Fox Statue");
		addAdvancement("bee_statue", "What is all the buzz about", "Obtain a Bee Statue");
		addAdvancement("trans_bee_statue", "Just bee you!", "TRANS RIGHTS ARE HUMAN RIGHTS!");
		addAdvancement("angry_bee_statue", "This one must sting!", "Obtain an Angry Bee Statue");
		addAdvancement("blaze_statue", "Retrieved from fire", "Obtain a Blaze Statue");
		addAdvancement("ghast_statue", "The squids from the nether", "Obtain a Ghast Statue");
		addAdvancement("magma_statue", "Boiled Slimes", "Obtain a Magma Cube Statue");
		addAdvancement("allay_statue", "Your Allay", "Obtain an allay Statue");
		addAdvancement("axolotl_statue", "Water kitten", "Collect any Axolotl Statue");
		addAdvancement("axolotl_all_statue", "Critically endangered", "Collect all Axolotl Statues");
		addAdvancement("frog_statue", "Ribbiting experience", "Collect any Frog Statue");
		addAdvancement("frog_all_statue", "Combining our powers for reals", "Collect all Frog Statues");
		addAdvancement("tadpole_statue", "Frogn't", "Obtain a Tadpole Statue");
		addAdvancement("warden_statue", "You've been Awardend", "Obtain a Warden Statue");
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event
	 * @param text  The subtitle text
	 */
	public void addSubtitle(Supplier<SoundEvent> sound, String text) {
		this.addSubtitle(sound.get(), text);
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	public void addSubtitle(SoundEvent sound, String text) {
		String path = Reference.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, text);
	}


	/**
	 * Add the translation of an advancement
	 *
	 * @param id          The advancement id
	 * @param name        The name of the advancement
	 * @param description The description of the advancement
	 */
	private void addAdvancement(String id, String name, String description) {
		String prefix = "advancement.statues.";
		add(prefix + id + ".title", name);
		add(prefix + id + ".desc", description);
	}
}
