package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class StatueAdvancementProvider extends AdvancementProvider {

	public StatueAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
		super(generatorIn, fileHelperIn);
	}

	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
		//Root advancement
		Advancement root = Advancement.Builder.advancement()
				.display(rootDisplay(StatueRegistry.STATUE_CORE.get(), advancementPrefix("root" + ".title"),
						advancementPrefix("root" + ".desc"), modLoc("textures/block/pebble.png")))
				.addCriterion("core", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.AIR))
				.save(consumer, rootID("root"));

		Advancement elderGuardian = onHoldBlock(consumer, StatueRegistry.ELDER_GUARDIAN_STATUE, FrameType.GOAL, false, root);
		onHoldBlock(consumer, StatueRegistry.RAVAGER_STATUE, FrameType.GOAL, false, elderGuardian);

		Advancement zombie = onHoldBlock(consumer, StatueRegistry.ZOMBIE_STATUE, root);
		Advancement babyZombie = onHoldBlock(consumer, StatueRegistry.BABY_ZOMBIE_STATUE, zombie);
		Advancement flood = onHoldBlock(consumer, StatueRegistry.FLOOD_STATUE, FrameType.GOAL, true, babyZombie);
		Advancement husk = onHoldBlock(consumer, StatueRegistry.HUSK_STATUE, zombie);
		Advancement creeper = onHoldBlock(consumer, StatueRegistry.CREEPER_STATUE, husk);
		Advancement campfire = onHoldBlock(consumer, StatueRegistry.CAMPFIRE_STATUE, FrameType.CHALLENGE, true, creeper);
		Advancement drowned = onHoldBlock(consumer, StatueRegistry.DROWNED_STATUE, husk);
		Advancement guardian = onHoldBlock(consumer, StatueRegistry.GUARDIAN_STATUE, drowned);
		Advancement spider = onHoldBlock(consumer, StatueRegistry.SPIDER_STATUE, husk);
		Advancement slime = onHoldBlock(consumer, StatueRegistry.SLIME_STATUE, spider);

		Advancement statueTable = onHoldBlock(consumer, StatueRegistry.STATUE_TABLE, root);
		Advancement displayStand = onHoldBlock(consumer, StatueRegistry.DISPLAY_STAND, statueTable);
		Advancement infoStatue = onHoldBlock(consumer, StatueRegistry.INFO_STATUE, displayStand);
		Advancement bumbo = onHoldBlock(consumer, StatueRegistry.BUMBO_STATUE, infoStatue);

		Advancement enderman = onHoldBlock(consumer, StatueRegistry.ENDERMAN_STATUE, root);
		Advancement endermite = onHoldBlock(consumer, StatueRegistry.ENDERMITE_STATUE, enderman);
		Advancement shulker = onHoldBlock(consumer, StatueRegistry.SHULKER_STATUE, endermite);

		Advancement cod = onHoldBlock(consumer, StatueRegistry.COD_STATUE, root);
		Advancement salmon = onHoldBlock(consumer, StatueRegistry.SALMON_STATUE, cod);
		Advancement tropical = onHoldAnyBlock(consumer, StatueRegistry.TROPICAL_FISH_B, FrameType.TASK, false,
				salmon, "tropical_fish_statue", RequirementsStrategy.OR,
				StatueRegistry.TROPICAL_FISH_B, StatueRegistry.TROPICAL_FISH_BB, StatueRegistry.TROPICAL_FISH_BE,
				StatueRegistry.TROPICAL_FISH_BM, StatueRegistry.TROPICAL_FISH_BMB, StatueRegistry.TROPICAL_FISH_BMS,
				StatueRegistry.TROPICAL_FISH_E, StatueRegistry.TROPICAL_FISH_ES, StatueRegistry.TROPICAL_FISH_HB,
				StatueRegistry.TROPICAL_FISH_SB, StatueRegistry.TROPICAL_FISH_SD, StatueRegistry.TROPICAL_FISH_SS
		);
		Advancement tropicalAll = onHoldAnyBlock(consumer, StatueRegistry.TROPICAL_FISH_BB, FrameType.GOAL, false,
				tropical, "tropical_fish_all_statue", RequirementsStrategy.AND,
				StatueRegistry.TROPICAL_FISH_B, StatueRegistry.TROPICAL_FISH_BB, StatueRegistry.TROPICAL_FISH_BE,
				StatueRegistry.TROPICAL_FISH_BM, StatueRegistry.TROPICAL_FISH_BMB, StatueRegistry.TROPICAL_FISH_BMS,
				StatueRegistry.TROPICAL_FISH_E, StatueRegistry.TROPICAL_FISH_ES, StatueRegistry.TROPICAL_FISH_HB,
				StatueRegistry.TROPICAL_FISH_SB, StatueRegistry.TROPICAL_FISH_SD, StatueRegistry.TROPICAL_FISH_SS
		);
		Advancement axolotl = onHoldAnyBlock(consumer, StatueRegistry.AXOLOTL_LUCY_STATUE, FrameType.TASK, false,
				salmon, "axolotl_statue", RequirementsStrategy.OR,
				StatueRegistry.AXOLOTL_LUCY_STATUE, StatueRegistry.AXOLOTL_WILD_STATUE,
				StatueRegistry.AXOLOTL_GOLD_STATUE, StatueRegistry.AXOLOTL_CYAN_STATUE,
				StatueRegistry.AXOLOTL_BLUE_STATUE
		);
		Advancement axolotlAll = onHoldAnyBlock(consumer, StatueRegistry.AXOLOTL_GOLD_STATUE, FrameType.GOAL, false,
				axolotl, "axolotl_all_statue", RequirementsStrategy.AND,
				StatueRegistry.AXOLOTL_LUCY_STATUE, StatueRegistry.AXOLOTL_WILD_STATUE,
				StatueRegistry.AXOLOTL_GOLD_STATUE, StatueRegistry.AXOLOTL_CYAN_STATUE,
				StatueRegistry.AXOLOTL_BLUE_STATUE
		);

		Advancement pufferSmall = onHoldBlock(consumer, StatueRegistry.PUFFERFISH_SMALL_STATUE, salmon);
		Advancement pufferMedium = onHoldBlock(consumer, StatueRegistry.PUFFERFISH_MEDIUM_STATUE, pufferSmall);
		Advancement puffer = onHoldBlock(consumer, StatueRegistry.PUFFERFISH_STATUE, pufferMedium);
		Advancement dolphin = onHoldBlock(consumer, StatueRegistry.DOLPHIN_STATUE, salmon);
		Advancement squid = onHoldBlock(consumer, StatueRegistry.SQUID_STATUE, dolphin);
		Advancement turtle = onHoldBlock(consumer, StatueRegistry.TURTLE_STATUE, squid);

		Advancement witch = onHoldBlock(consumer, StatueRegistry.WITCH_STATUE, root);
		Advancement pillager = onHoldBlock(consumer, StatueRegistry.PILLAGER_STATUE, witch);
		Advancement vindicator = onHoldBlock(consumer, StatueRegistry.VINDICATOR_STATUE, pillager);
		onHoldBlock(consumer, StatueRegistry.EVOKER_STATUE, vindicator);

		Advancement cow = onHoldBlock(consumer, StatueRegistry.COW_STATUE, root);
		Advancement mooshroom = onHoldBlock(consumer, StatueRegistry.MOOSHROOM_STATUE, cow);
		Advancement mooshroomBrown = onHoldBlock(consumer, StatueRegistry.BROWN_MOOSHROOM_STATUE, mooshroom);
		Advancement pig = onHoldBlock(consumer, StatueRegistry.PIG_STATUE, cow);
		Advancement snowman = onHoldBlock(consumer, StatueRegistry.SNOW_GOLEM_STATUE, cow);
		Advancement allay = onHoldBlock(consumer, StatueRegistry.ALLAY_STATUE, cow);

		Advancement wasteland = onHoldBlock(consumer, StatueRegistry.WASTELAND_STATUE, FrameType.CHALLENGE, true, pig);
		Advancement chicken = onHoldBlock(consumer, StatueRegistry.CHICKEN_STATUE, cow);
		Advancement kingCluck = onHoldBlock(consumer, StatueRegistry.KING_CLUCK_STATUE, FrameType.GOAL, true, chicken);
		Advancement chickenJockey = onHoldBlock(consumer, StatueRegistry.CHICKEN_JOCKEY_STATUE, chicken);
		Advancement sheep = onHoldAnyBlock(consumer, StatueRegistry.SHEEP_STATUE_WHITE, FrameType.TASK, false,
				cow, "sheep_statue", RequirementsStrategy.OR,
				StatueRegistry.SHEEP_SHAVEN_STATUE, StatueRegistry.SHEEP_STATUE_BLACK, StatueRegistry.SHEEP_STATUE_BLUE,
				StatueRegistry.SHEEP_STATUE_BROWN, StatueRegistry.SHEEP_STATUE_CYAN, StatueRegistry.SHEEP_STATUE_GRAY,
				StatueRegistry.SHEEP_STATUE_GREEN, StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, StatueRegistry.SHEEP_STATUE_LIGHT_GRAY,
				StatueRegistry.SHEEP_STATUE_LIME, StatueRegistry.SHEEP_STATUE_MAGENTA, StatueRegistry.SHEEP_STATUE_ORANGE,
				StatueRegistry.SHEEP_STATUE_PINK, StatueRegistry.SHEEP_STATUE_PURPLE, StatueRegistry.SHEEP_STATUE_RED,
				StatueRegistry.SHEEP_STATUE_WHITE, StatueRegistry.SHEEP_STATUE_YELLOW
		);
		Advancement sheepAll = onHoldAnyBlock(consumer, StatueRegistry.SHEEP_STATUE_WHITE, FrameType.GOAL, false,
				sheep, "sheep_all_statue", RequirementsStrategy.AND,
				StatueRegistry.SHEEP_SHAVEN_STATUE, StatueRegistry.SHEEP_STATUE_BLACK, StatueRegistry.SHEEP_STATUE_BLUE,
				StatueRegistry.SHEEP_STATUE_BROWN, StatueRegistry.SHEEP_STATUE_CYAN, StatueRegistry.SHEEP_STATUE_GRAY,
				StatueRegistry.SHEEP_STATUE_GREEN, StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, StatueRegistry.SHEEP_STATUE_LIGHT_GRAY,
				StatueRegistry.SHEEP_STATUE_LIME, StatueRegistry.SHEEP_STATUE_MAGENTA, StatueRegistry.SHEEP_STATUE_ORANGE,
				StatueRegistry.SHEEP_STATUE_PINK, StatueRegistry.SHEEP_STATUE_PURPLE, StatueRegistry.SHEEP_STATUE_RED,
				StatueRegistry.SHEEP_STATUE_WHITE, StatueRegistry.SHEEP_STATUE_YELLOW
		);

		Advancement cat = onHoldAnyBlock(consumer, StatueRegistry.CAT_JELLIE_STATUE, FrameType.TASK, false,
				root, "cat_statue", RequirementsStrategy.OR,
				StatueRegistry.CAT_BLACK_STATUE, StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, StatueRegistry.CAT_CALICO_STATUE,
				StatueRegistry.CAT_JELLIE_STATUE, StatueRegistry.CAT_PERSIAN_STATUE, StatueRegistry.CAT_RAGDOLL_STATUE,
				StatueRegistry.CAT_RED_STATUE, StatueRegistry.CAT_SIAMESE_STATUE, StatueRegistry.CAT_TABBY_STATUE,
				StatueRegistry.CAT_TUXEDO_STATUE, StatueRegistry.CAT_WHITE_STATUE
		);
		Advancement catAll = onHoldAnyBlock(consumer, StatueRegistry.CAT_TUXEDO_STATUE, FrameType.GOAL, false,
				cat, "cat_all_statue", RequirementsStrategy.AND,
				StatueRegistry.CAT_BLACK_STATUE, StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, StatueRegistry.CAT_CALICO_STATUE,
				StatueRegistry.CAT_JELLIE_STATUE, StatueRegistry.CAT_PERSIAN_STATUE, StatueRegistry.CAT_RAGDOLL_STATUE,
				StatueRegistry.CAT_RED_STATUE, StatueRegistry.CAT_SIAMESE_STATUE, StatueRegistry.CAT_TABBY_STATUE,
				StatueRegistry.CAT_TUXEDO_STATUE, StatueRegistry.CAT_WHITE_STATUE
		);
		Advancement fox = onHoldBlock(consumer, StatueRegistry.FOX_STATUE, cat);
		Advancement foxSnow = onHoldBlock(consumer, StatueRegistry.FOX_SNOW_STATUE, fox);
		Advancement panda = onHoldAnyBlock(consumer, StatueRegistry.PANDA_LAZY_STATUE, FrameType.TASK, false,
				foxSnow, "panda_statue", RequirementsStrategy.OR,
				StatueRegistry.PANDA_ANGRY_STATUE, StatueRegistry.PANDA_BROWN_STATUE, StatueRegistry.PANDA_LAZY_STATUE,
				StatueRegistry.PANDA_NORMAL_STATUE, StatueRegistry.PANDA_PLAYFUL_STATUE, StatueRegistry.PANDA_WEAK_STATUE,
				StatueRegistry.PANDA_WORRIED_STATUE
		);
		Advancement pandaAll = onHoldAnyBlock(consumer, StatueRegistry.PANDA_BROWN_STATUE, FrameType.GOAL, false,
				panda, "panda_all_statue", RequirementsStrategy.AND,
				StatueRegistry.PANDA_ANGRY_STATUE, StatueRegistry.PANDA_BROWN_STATUE, StatueRegistry.PANDA_LAZY_STATUE,
				StatueRegistry.PANDA_NORMAL_STATUE, StatueRegistry.PANDA_PLAYFUL_STATUE, StatueRegistry.PANDA_WEAK_STATUE,
				StatueRegistry.PANDA_WORRIED_STATUE
		);
		Advancement bee = onHoldBlock(consumer, StatueRegistry.BEE_STATUE, foxSnow);
		Advancement beeTrans = onHoldBlock(consumer, StatueRegistry.TRANS_BEE_STATUE, FrameType.GOAL, false, bee);
		Advancement beeAngry = onHoldBlock(consumer, StatueRegistry.ANGRY_BEE_STATUE, bee);
		Advancement rabbit = onHoldAnyBlock(consumer, StatueRegistry.RABBIT_BR_STATUE, FrameType.TASK, false,
				foxSnow, "rabbit_statue", RequirementsStrategy.OR,
				StatueRegistry.RABBIT_BR_STATUE, StatueRegistry.RABBIT_BS_STATUE, StatueRegistry.RABBIT_BW_STATUE,
				StatueRegistry.RABBIT_GO_STATUE, StatueRegistry.RABBIT_WH_STATUE, StatueRegistry.RABBIT_WS_STATUE
		);
		Advancement rabbitAll = killerCollection(consumer, StatueRegistry.RABBIT_WH_STATUE, rabbit, "rabbit_all_statue",
				StatueRegistry.RABBIT_BR_STATUE, StatueRegistry.RABBIT_BS_STATUE, StatueRegistry.RABBIT_BW_STATUE,
				StatueRegistry.RABBIT_GO_STATUE, StatueRegistry.RABBIT_WH_STATUE, StatueRegistry.RABBIT_WS_STATUE
		);

		Advancement blaze = onHoldBlock(consumer, StatueRegistry.BLAZE_STATUE, root);
		Advancement ghast = onHoldBlock(consumer, StatueRegistry.GHAST_STATUE, blaze);
		Advancement magma = onHoldBlock(consumer, StatueRegistry.MAGMA_STATUE, ghast);

		onHoldBlock(consumer, StatueRegistry.PLAYER_STATUE, root);

	}

	/**
	 * Adds an advancement for holding a given block.
	 *
	 * @param consumer       The consumer to add to.
	 * @param registryObject The block registry object.
	 * @param type           The frame type.
	 * @param hidden         If the advancement is hidden.
	 * @param root           The root advancement.
	 */
	protected static Advancement onHoldBlock(Consumer<Advancement> consumer, RegistryObject<Block> registryObject, FrameType type, boolean hidden, Advancement root) {
		String path = registryObject.getId().getPath();
		ResourceLocation registryLocation = modLoc(path);

		DisplayInfo info = hidden ? hiddenDisplay(registryObject.get(), path, type) : simpleDisplay(registryObject.get(), path, type);
		return Advancement.Builder.advancement()
				.display(info)
				.parent(root)
				.addCriterion(path, onHeldItems(registryObject.get()))
				.save(consumer, rootID(registryLocation.getPath()));
	}

	/**
	 * Adds an advancement for holding a given block.
	 *
	 * @param consumer       The consumer to add to.
	 * @param registryObject The block registry object.
	 * @param root           The root advancement.
	 */
	protected static Advancement onHoldBlock(Consumer<Advancement> consumer, RegistryObject<Block> registryObject, Advancement root) {
		String path = registryObject.getId().getPath();
		ResourceLocation registryLocation = modLoc(path);

		return Advancement.Builder.advancement()
				.display(simpleDisplay(registryObject.get(), path, FrameType.TASK))
				.parent(root)
				.addCriterion(path, onHeldItems(registryObject.get()))
				.save(consumer, rootID(registryLocation.getPath()));
	}


	/**
	 * Adds an advancement for holding any/all the given items.
	 *
	 * @param consumer        The consumer to add to.
	 * @param displayObject   The display block registry object.
	 * @param type            The frame type.
	 * @param hidden          If the advancement is hidden.
	 * @param root            The root advancement.
	 * @param path            The path of the advancement.
	 * @param registryObjects The block registry objects that you need to hold one of.
	 */
	protected static Advancement onHoldAnyBlock(Consumer<Advancement> consumer, RegistryObject<Block> displayObject, FrameType type, boolean hidden, Advancement root, String path, RequirementsStrategy requirementsStrategy, RegistryObject<Block>... registryObjects) {
		ResourceLocation registryLocation = modLoc(path);

		DisplayInfo info = hidden ? hiddenDisplay(displayObject.get(), path, type) : simpleDisplay(displayObject.get(), path, type);
		Advancement.Builder builder = Advancement.Builder.advancement()
				.display(info)
				.parent(root);

		for (RegistryObject<Block> registryObject : registryObjects) {
			builder.addCriterion(registryObject.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(registryObject.get()));
		}
		return builder.requirements(requirementsStrategy).save(consumer, rootID(registryLocation.getPath()));
	}

	/**
	 * Adds an advancement for holding all the given items and trigger the killer function.
	 *
	 * @param consumer        The consumer to add to.
	 * @param displayObject   The display block registry object.
	 * @param root            The root advancement.
	 * @param path            The path of the advancement.
	 * @param registryObjects The block registry objects that you need to hold one of.
	 */
	protected static Advancement killerCollection(Consumer<Advancement> consumer, RegistryObject<Block> displayObject, Advancement root, String path, RegistryObject<Block>... registryObjects) {
		ResourceLocation registryLocation = modLoc(path);

		Advancement.Builder builder = Advancement.Builder.advancement()
				.display(simpleDisplay(displayObject.get(), path, FrameType.GOAL))
				.parent(root);

		for (RegistryObject<Block> registryObject : registryObjects) {
			builder.addCriterion(registryObject.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(registryObject.get()));
		}
		return builder
				.rewards(AdvancementRewards.Builder.function(modLoc("killer_rabbit")))
				.requirements(RequirementsStrategy.AND)
				.save(consumer, rootID(registryLocation.getPath()));
	}

	/**
	 * Adds an advancement for holding a given item.
	 *
	 * @param consumer       The consumer to add to.
	 * @param registryObject The block registry object.
	 * @param type           The frame type.
	 * @param hidden         If the advancement is hidden.
	 * @param root           The root advancement.
	 */
	protected static Advancement onHoldItem(Consumer<Advancement> consumer, RegistryObject<Item> registryObject, FrameType type, boolean hidden, Advancement root) {
		String path = registryObject.getId().getPath();
		ResourceLocation registryLocation = modLoc(path);

		DisplayInfo info = hidden ? hiddenDisplay(registryObject.get(), path, type) : simpleDisplay(registryObject.get(), path, type);
		return Advancement.Builder.advancement()
				.display(info)
				.parent(root)
				.addCriterion(path, onHeldItems(registryObject.get()))
				.save(consumer, rootID(registryLocation.getPath()));
	}


	/**
	 * Generate a root DisplayInfo object.
	 *
	 * @param icon       The icon to use.
	 * @param titleKey   The title key.
	 * @param descKey    The description key.
	 * @param background The background texture.
	 * @return The DisplayInfo object.
	 */
	protected static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
		return new DisplayInfo(new ItemStack(icon),
				Component.translatable(titleKey),
				Component.translatable(descKey),
				background, FrameType.TASK, false, false, false);
	}

	/**
	 * Generate a simple DisplayInfo object.
	 *
	 * @param icon The icon to use.
	 * @param name The name of the advancement.
	 * @return The DisplayInfo object.
	 */
	protected static DisplayInfo simpleDisplay(ItemLike icon, String name, FrameType type) {
		return new DisplayInfo(new ItemStack(icon),
				Component.translatable(advancementPrefix(name + ".title")),
				Component.translatable(advancementPrefix(name + ".desc")),
				null, type, true, true, false);
	}


	/**
	 * Generate a simple DisplayInfo object.
	 *
	 * @param icon The icon to use.
	 * @param name The name of the advancement.
	 * @return The DisplayInfo object.
	 */
	protected static DisplayInfo hiddenDisplay(ItemLike icon, String name, FrameType type) {
		return new DisplayInfo(new ItemStack(icon),
				Component.translatable(advancementPrefix(name + ".title")),
				Component.translatable(advancementPrefix(name + ".desc")),
				null, type, true, true, true);
	}

	/**
	 * Get a trigger instance for killing an entity.
	 *
	 * @param entityType The entity type.
	 * @return The trigger instance.
	 */
	protected static KilledTrigger.TriggerInstance onKill(EntityType<?> entityType) {
		return KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityType));
	}

	/**
	 * Get a trigger instance for holding items.
	 *
	 * @param items The items.
	 * @return The trigger instance.
	 */
	protected static InventoryChangeTrigger.TriggerInstance onHeldItems(ItemLike... items) {
		return InventoryChangeTrigger.TriggerInstance.hasItems(items);
	}

	/**
	 * Generate a ResourceLocation that has the mod ID as the namespace.
	 *
	 * @param path The path.
	 * @return The ResourceLocation.
	 */
	private static ResourceLocation modLoc(String path) {
		return new ResourceLocation(Reference.MOD_ID, path);
	}

	/**
	 * Generate an advancement prefix.
	 *
	 * @param name The name of the advancement.
	 * @return The prefix.
	 */
	private static String advancementPrefix(String name) {
		return "advancement." + Reference.MOD_ID + "." + name;
	}

	/**
	 * Generate a root advancement ID.
	 *
	 * @param name The name of the advancement.
	 * @return The advancement ID.
	 */
	private static String rootID(String name) {
		return modLoc(name).toString();
	}
}
