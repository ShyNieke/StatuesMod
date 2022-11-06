package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class StatueLanguageProvider extends LanguageProvider {

	public StatueLanguageProvider(DataGenerator gen) {
		super(gen, Reference.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + Reference.MOD_ID + ".blocks", "Statue Blocks");
		add("itemGroup." + Reference.MOD_ID + ".items", "Statue Items");

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

		add("statues.cluckington.info", "Also known as Cluckington");
		add("statues.royalnugget.info", "Made for and from royals");
		add("statues.villager.info", "WIP, Trades in the future.");
		add("statues.tea.info", "Now with less milk.");
		add("statues.cup.info", "I can't believe its edible.");
		add("statues.last.known.location", "Tracking last known location of, %s");

		addEntityType(StatueRegistry.STATUE_BAT, "Statue Bat");
		addEntityType(StatueRegistry.PLAYER_STATUE_ENTITY, "Player Statue");

		add("statues:container.shulkerStatue", "Shulker Statue");
		add("statues:container.shulker_statue.more", "and %s more...");

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

		add("curios.identifier.statue", "Statue");

		add("statues.gui.jei.category.loot", "Statue Loot");
		add("statues.gui.jei.category.upgrade", "Statue Upgrade");

		add("statues.info.level", "Level:");
		add("statues.info.kills", "Kills:");
		add("statues.info.upgrade_slots", "Upgrade Slots:");

		add("statues.upgrade.level.1", "I");
		add("statues.upgrade.level.2", "II");
		add("statues.upgrade.level.3", "III");
		add("statues.upgrade.level.4", "IV");
		add("statues.upgrade.level.5", "V");
		add("statues.upgrade.level.6", "VI");
		add("statues.upgrade.level.7", "VII");
		add("statues.upgrade.level.8", "VIII");
		add("statues.upgrade.level.9", "IX");
		add("statues.upgrade.level.10", "X");

		add("statues.upgrade.glowing.name", "Glowing");
		add("statues.upgrade.unglowing.name", "unglowing");
		add("statues.upgrade.spawner.name", "Spawner");
		add("statues.upgrade.despawner.name", "Despawner");
		add("statues.upgrade.mob_killer.name", "Mob Killer");
		add("statues.upgrade.looting.name", "Looting");
		add("statues.upgrade.automation.name", "Automation");
		add("statues.upgrade.speed.name", "Speed");
		add("statues.upgrade.interaction.name", "Interaction");
		add("statues.upgrade.sound.name", "Sound");
	}

	public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
		this.addSubtitle(sound.get(), name);
	}

	public void addSubtitle(SoundEvent sound, String name) {
		String path = Reference.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, name);
	}
}
