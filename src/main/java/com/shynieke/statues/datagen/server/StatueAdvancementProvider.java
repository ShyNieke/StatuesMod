package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.EnterBlockTrigger;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Optional;

public class StatueAdvancementProvider {

	public static SingleRegistryBootstrap<Advancement> create() {
		return new AdvancementProvider(List.of(StatueAdvancementGenerator::new));
	}

	public static class StatueAdvancementGenerator extends AdvancementSubProvider {
		private final HolderGetter<Block> blocks;

		protected StatueAdvancementGenerator(BootstrapContext<Advancement> output) {
			super(output);
			this.blocks = output.lookup(Registries.BLOCK);
		}

		public void generate() {
			//Root advancement
			AdvancementHolder root = Advancement.Builder.advancement()
					.display(rootDisplay(StatueRegistry.STATUE_CORE.get(), advancementPrefix("root" + ".title"),
							advancementPrefix("root" + ".desc"), modLoc("block/pebble")))
					.addCriterion("core", EnterBlockTrigger.TriggerInstance.entersBlock(this.blocks, Blocks.AIR))
					.save(this.output, rootID("root"));

			AdvancementHolder elderGuardian = onHoldBlock(StatueRegistry.ELDER_GUARDIAN_STATUE, AdvancementType.GOAL, false, root);
			onHoldBlock(StatueRegistry.RAVAGER_STATUE, AdvancementType.GOAL, false, elderGuardian);

			AdvancementHolder zombie = onHoldBlock(StatueRegistry.ZOMBIE_STATUE, root);
			AdvancementHolder babyZombie = onHoldBlock(StatueRegistry.BABY_ZOMBIE_STATUE, zombie);
			AdvancementHolder flood = onHoldBlock(StatueRegistry.FLOOD_STATUE, AdvancementType.GOAL, true, babyZombie);
			AdvancementHolder husk = onHoldBlock(StatueRegistry.HUSK_STATUE, zombie);
			AdvancementHolder creeper = onHoldBlock(StatueRegistry.CREEPER_STATUE, husk);
			AdvancementHolder campfire = onHoldBlock(StatueRegistry.CAMPFIRE_STATUE, AdvancementType.CHALLENGE, true, creeper);
			AdvancementHolder drowned = onHoldBlock(StatueRegistry.DROWNED_STATUE, husk);
			AdvancementHolder guardian = onHoldBlock(StatueRegistry.GUARDIAN_STATUE, drowned);
			AdvancementHolder spider = onHoldBlock(StatueRegistry.SPIDER_STATUE, husk);
			AdvancementHolder slime = onHoldBlock(StatueRegistry.SLIME_STATUE, spider);

			AdvancementHolder statueTable = onHoldBlock(StatueRegistry.STATUE_TABLE, root);
			AdvancementHolder displayStand = onHoldBlock(StatueRegistry.DISPLAY_STAND, statueTable);
			AdvancementHolder infoStatue = onHoldBlock(StatueRegistry.INFO_STATUE, displayStand);
			AdvancementHolder bumbo = onHoldBlock(StatueRegistry.BUMBO_STATUE, infoStatue);

			AdvancementHolder enderman = onHoldBlock(StatueRegistry.ENDERMAN_STATUE, root);
			AdvancementHolder endermite = onHoldBlock(StatueRegistry.ENDERMITE_STATUE, enderman);
			AdvancementHolder shulker = onHoldBlock(StatueRegistry.SHULKER_STATUE, endermite);

			AdvancementHolder cod = onHoldBlock(StatueRegistry.COD_STATUE, root);
			AdvancementHolder salmon = onHoldBlock(StatueRegistry.SALMON_STATUE, cod);
			AdvancementHolder tropical = onHoldAnyBlock(StatueRegistry.TROPICAL_FISH_B, AdvancementType.TASK, false,
					salmon, "tropical_fish_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.TROPICAL_FISH_B, StatueRegistry.TROPICAL_FISH_BB, StatueRegistry.TROPICAL_FISH_BE,
					StatueRegistry.TROPICAL_FISH_BM, StatueRegistry.TROPICAL_FISH_BMB, StatueRegistry.TROPICAL_FISH_BMS,
					StatueRegistry.TROPICAL_FISH_E, StatueRegistry.TROPICAL_FISH_ES, StatueRegistry.TROPICAL_FISH_HB,
					StatueRegistry.TROPICAL_FISH_SB, StatueRegistry.TROPICAL_FISH_SD, StatueRegistry.TROPICAL_FISH_SS
			);
			AdvancementHolder tropicalAll = onHoldAnyBlock(StatueRegistry.TROPICAL_FISH_BB, AdvancementType.GOAL, false,
					tropical, "tropical_fish_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.TROPICAL_FISH_B, StatueRegistry.TROPICAL_FISH_BB, StatueRegistry.TROPICAL_FISH_BE,
					StatueRegistry.TROPICAL_FISH_BM, StatueRegistry.TROPICAL_FISH_BMB, StatueRegistry.TROPICAL_FISH_BMS,
					StatueRegistry.TROPICAL_FISH_E, StatueRegistry.TROPICAL_FISH_ES, StatueRegistry.TROPICAL_FISH_HB,
					StatueRegistry.TROPICAL_FISH_SB, StatueRegistry.TROPICAL_FISH_SD, StatueRegistry.TROPICAL_FISH_SS
			);
			AdvancementHolder axolotl = onHoldAnyBlock(StatueRegistry.AXOLOTL_LUCY_STATUE, AdvancementType.TASK, false,
					salmon, "axolotl_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.AXOLOTL_LUCY_STATUE, StatueRegistry.AXOLOTL_WILD_STATUE,
					StatueRegistry.AXOLOTL_GOLD_STATUE, StatueRegistry.AXOLOTL_CYAN_STATUE,
					StatueRegistry.AXOLOTL_BLUE_STATUE
			);
			AdvancementHolder axolotlAll = onHoldAnyBlock(StatueRegistry.AXOLOTL_GOLD_STATUE, AdvancementType.GOAL, false,
					axolotl, "axolotl_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.AXOLOTL_LUCY_STATUE, StatueRegistry.AXOLOTL_WILD_STATUE,
					StatueRegistry.AXOLOTL_GOLD_STATUE, StatueRegistry.AXOLOTL_CYAN_STATUE,
					StatueRegistry.AXOLOTL_BLUE_STATUE
			);

			AdvancementHolder pufferSmall = onHoldBlock(StatueRegistry.PUFFERFISH_SMALL_STATUE, salmon);
			AdvancementHolder pufferMedium = onHoldBlock(StatueRegistry.PUFFERFISH_MEDIUM_STATUE, pufferSmall);
			AdvancementHolder puffer = onHoldBlock(StatueRegistry.PUFFERFISH_STATUE, pufferMedium);
			AdvancementHolder dolphin = onHoldBlock(StatueRegistry.DOLPHIN_STATUE, salmon);
			AdvancementHolder squid = onHoldBlock(StatueRegistry.SQUID_STATUE, dolphin);
			AdvancementHolder turtle = onHoldBlock(StatueRegistry.TURTLE_STATUE, squid);

			AdvancementHolder witch = onHoldBlock(StatueRegistry.WITCH_STATUE, root);
			AdvancementHolder pillager = onHoldBlock(StatueRegistry.PILLAGER_STATUE, witch);
			AdvancementHolder vindicator = onHoldBlock(StatueRegistry.VINDICATOR_STATUE, pillager);
			onHoldBlock(StatueRegistry.EVOKER_STATUE, vindicator);

			AdvancementHolder cow = onHoldBlock(StatueRegistry.COW_STATUE, root);
			AdvancementHolder mooshroom = onHoldBlock(StatueRegistry.MOOSHROOM_STATUE, cow);
			AdvancementHolder mooshroomBrown = onHoldBlock(StatueRegistry.BROWN_MOOSHROOM_STATUE, mooshroom);
			AdvancementHolder pig = onHoldBlock(StatueRegistry.PIG_STATUE, cow);
			AdvancementHolder snowman = onHoldBlock(StatueRegistry.SNOW_GOLEM_STATUE, cow);
			AdvancementHolder allay = onHoldBlock(StatueRegistry.ALLAY_STATUE, cow);

			AdvancementHolder wasteland = onHoldBlock(StatueRegistry.WASTELAND_STATUE, AdvancementType.CHALLENGE, true, pig);
			AdvancementHolder chicken = onHoldBlock(StatueRegistry.CHICKEN_STATUE, cow);
			AdvancementHolder kingCluck = onHoldBlock(StatueRegistry.KING_CLUCK_STATUE, AdvancementType.GOAL, true, chicken);
			AdvancementHolder chickenJockey = onHoldBlock(StatueRegistry.CHICKEN_JOCKEY_STATUE, chicken);
			AdvancementHolder sheep = onHoldAnyBlock(StatueRegistry.SHEEP_STATUE_WHITE, AdvancementType.TASK, false,
					cow, "sheep_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.SHEEP_SHAVEN_STATUE, StatueRegistry.SHEEP_STATUE_BLACK, StatueRegistry.SHEEP_STATUE_BLUE,
					StatueRegistry.SHEEP_STATUE_BROWN, StatueRegistry.SHEEP_STATUE_CYAN, StatueRegistry.SHEEP_STATUE_GRAY,
					StatueRegistry.SHEEP_STATUE_GREEN, StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, StatueRegistry.SHEEP_STATUE_LIGHT_GRAY,
					StatueRegistry.SHEEP_STATUE_LIME, StatueRegistry.SHEEP_STATUE_MAGENTA, StatueRegistry.SHEEP_STATUE_ORANGE,
					StatueRegistry.SHEEP_STATUE_PINK, StatueRegistry.SHEEP_STATUE_PURPLE, StatueRegistry.SHEEP_STATUE_RED,
					StatueRegistry.SHEEP_STATUE_WHITE, StatueRegistry.SHEEP_STATUE_YELLOW
			);
			AdvancementHolder sheepAll = onHoldAnyBlock(StatueRegistry.SHEEP_STATUE_WHITE, AdvancementType.GOAL, false,
					sheep, "sheep_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.SHEEP_SHAVEN_STATUE, StatueRegistry.SHEEP_STATUE_BLACK, StatueRegistry.SHEEP_STATUE_BLUE,
					StatueRegistry.SHEEP_STATUE_BROWN, StatueRegistry.SHEEP_STATUE_CYAN, StatueRegistry.SHEEP_STATUE_GRAY,
					StatueRegistry.SHEEP_STATUE_GREEN, StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, StatueRegistry.SHEEP_STATUE_LIGHT_GRAY,
					StatueRegistry.SHEEP_STATUE_LIME, StatueRegistry.SHEEP_STATUE_MAGENTA, StatueRegistry.SHEEP_STATUE_ORANGE,
					StatueRegistry.SHEEP_STATUE_PINK, StatueRegistry.SHEEP_STATUE_PURPLE, StatueRegistry.SHEEP_STATUE_RED,
					StatueRegistry.SHEEP_STATUE_WHITE, StatueRegistry.SHEEP_STATUE_YELLOW
			);

			AdvancementHolder cat = onHoldAnyBlock(StatueRegistry.CAT_JELLIE_STATUE, AdvancementType.TASK, false,
					root, "cat_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.CAT_BLACK_STATUE, StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, StatueRegistry.CAT_CALICO_STATUE,
					StatueRegistry.CAT_JELLIE_STATUE, StatueRegistry.CAT_PERSIAN_STATUE, StatueRegistry.CAT_RAGDOLL_STATUE,
					StatueRegistry.CAT_RED_STATUE, StatueRegistry.CAT_SIAMESE_STATUE, StatueRegistry.CAT_TABBY_STATUE,
					StatueRegistry.CAT_TUXEDO_STATUE, StatueRegistry.CAT_WHITE_STATUE
			);
			AdvancementHolder catAll = onHoldAnyBlock(StatueRegistry.CAT_TUXEDO_STATUE, AdvancementType.GOAL, false,
					cat, "cat_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.CAT_BLACK_STATUE, StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, StatueRegistry.CAT_CALICO_STATUE,
					StatueRegistry.CAT_JELLIE_STATUE, StatueRegistry.CAT_PERSIAN_STATUE, StatueRegistry.CAT_RAGDOLL_STATUE,
					StatueRegistry.CAT_RED_STATUE, StatueRegistry.CAT_SIAMESE_STATUE, StatueRegistry.CAT_TABBY_STATUE,
					StatueRegistry.CAT_TUXEDO_STATUE, StatueRegistry.CAT_WHITE_STATUE
			);
			AdvancementHolder fox = onHoldBlock(StatueRegistry.FOX_STATUE, cat);
			AdvancementHolder foxSnow = onHoldBlock(StatueRegistry.FOX_SNOW_STATUE, fox);
			AdvancementHolder panda = onHoldAnyBlock(StatueRegistry.PANDA_LAZY_STATUE, AdvancementType.TASK, false,
					foxSnow, "panda_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.PANDA_ANGRY_STATUE, StatueRegistry.PANDA_BROWN_STATUE, StatueRegistry.PANDA_LAZY_STATUE,
					StatueRegistry.PANDA_NORMAL_STATUE, StatueRegistry.PANDA_PLAYFUL_STATUE, StatueRegistry.PANDA_WEAK_STATUE,
					StatueRegistry.PANDA_WORRIED_STATUE
			);
			AdvancementHolder pandaAll = onHoldAnyBlock(StatueRegistry.PANDA_BROWN_STATUE, AdvancementType.GOAL, false,
					panda, "panda_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.PANDA_ANGRY_STATUE, StatueRegistry.PANDA_BROWN_STATUE, StatueRegistry.PANDA_LAZY_STATUE,
					StatueRegistry.PANDA_NORMAL_STATUE, StatueRegistry.PANDA_PLAYFUL_STATUE, StatueRegistry.PANDA_WEAK_STATUE,
					StatueRegistry.PANDA_WORRIED_STATUE
			);
			AdvancementHolder bee = onHoldBlock(StatueRegistry.BEE_STATUE, foxSnow);
			AdvancementHolder beeTrans = onHoldBlock(StatueRegistry.TRANS_BEE_STATUE, AdvancementType.GOAL, false, bee);
			AdvancementHolder beeAngry = onHoldBlock(StatueRegistry.ANGRY_BEE_STATUE, bee);
			AdvancementHolder rabbit = onHoldAnyBlock(StatueRegistry.RABBIT_BR_STATUE, AdvancementType.TASK, false,
					foxSnow, "rabbit_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.RABBIT_BR_STATUE, StatueRegistry.RABBIT_BS_STATUE, StatueRegistry.RABBIT_BW_STATUE,
					StatueRegistry.RABBIT_GO_STATUE, StatueRegistry.RABBIT_WH_STATUE, StatueRegistry.RABBIT_WS_STATUE
			);
			AdvancementHolder rabbitAll = killerCollection(StatueRegistry.RABBIT_WH_STATUE, rabbit, "rabbit_all_statue",
					StatueRegistry.RABBIT_BR_STATUE, StatueRegistry.RABBIT_BS_STATUE, StatueRegistry.RABBIT_BW_STATUE,
					StatueRegistry.RABBIT_GO_STATUE, StatueRegistry.RABBIT_WH_STATUE, StatueRegistry.RABBIT_WS_STATUE
			);
			AdvancementHolder frog = onHoldAnyBlock(StatueRegistry.FROG_TEMPERATE_STATUE, AdvancementType.TASK, false,
					root, "frog_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.FROG_TEMPERATE_STATUE, StatueRegistry.FROG_WARM_STATUE, StatueRegistry.FROG_COLD_STATUE
			);
			AdvancementHolder frogAll = onHoldAnyBlock(StatueRegistry.FROG_COLD_STATUE, AdvancementType.GOAL, false,
					frog, "frog_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.FROG_TEMPERATE_STATUE, StatueRegistry.FROG_WARM_STATUE, StatueRegistry.FROG_COLD_STATUE
			);
			AdvancementHolder tadpole = onHoldBlock(StatueRegistry.TADPOLE_STATUE, frog);

			AdvancementHolder blaze = onHoldBlock(StatueRegistry.BLAZE_STATUE, root);
			AdvancementHolder ghast = onHoldBlock(StatueRegistry.GHAST_STATUE, blaze);
			AdvancementHolder magma = onHoldBlock(StatueRegistry.MAGMA_STATUE, ghast);
			AdvancementHolder zombified_piglin = onHoldBlock(StatueRegistry.ZOMBIFIED_PIGLIN_STATUE, ghast);

			onHoldBlock(StatueRegistry.PLAYER_STATUE, root);

		}

		/**
		 * Adds an advancement for holding a given block.
		 *
		 * @param registryObject The block registry object.
		 * @param type           The frame type.
		 * @param hidden         If the advancement is hidden.
		 * @param root           The root advancement.
		 */
		protected AdvancementHolder onHoldBlock(DeferredHolder<Block, ? extends Block> registryObject,
		                                        AdvancementType type, boolean hidden, AdvancementHolder root) {
			String path = registryObject.getId().getPath();
			Identifier registryLocation = modLoc(path);

			DisplayInfo info = hidden ? hiddenDisplay(registryObject.get(), path, type) : simpleDisplay(registryObject.get(), path, type);
			return Advancement.Builder.advancement()
					.display(info)
					.parent(root)
					.addCriterion(path, onHeldItems(registryObject.get()))
					.save(this.output, rootID(registryLocation.getPath()));
		}

		/**
		 * Adds an advancement for holding a given block.
		 *
		 * @param deferredHolder The block registry object.
		 * @param root           The root advancement.
		 */
		protected AdvancementHolder onHoldBlock(DeferredBlock<? extends Block> deferredHolder,
		                                        AdvancementHolder root) {
			String path = deferredHolder.getId().getPath();
			Identifier registryLocation = modLoc(path);

			return Advancement.Builder.advancement()
					.display(simpleDisplay(deferredHolder.get(), path, AdvancementType.TASK))
					.parent(root)
					.addCriterion(path, onHeldItems(deferredHolder.get()))
					.save(this.output, rootID(registryLocation.getPath()));
		}


		/**
		 * Adds an advancement for holding any/all the given items.
		 *
		 * @param deferredHolder  The display block registry object.
		 * @param type            The frame type.
		 * @param hidden          If the advancement is hidden.
		 * @param root            The root advancement.
		 * @param path            The path of the advancement.
		 * @param registryObjects The block registry objects that you need to hold one of.
		 */
		protected AdvancementHolder onHoldAnyBlock(DeferredBlock<? extends Block> deferredHolder,
		                                           AdvancementType type, boolean hidden, AdvancementHolder root, String path,
		                                           AdvancementRequirements.Strategy strategy, DeferredBlock<? extends Block>... registryObjects) {
			Identifier registryLocation = modLoc(path);

			DisplayInfo info = hidden ? hiddenDisplay(deferredHolder.get(), path, type) : simpleDisplay(deferredHolder.get(), path, type);
			Advancement.Builder builder = Advancement.Builder.advancement()
					.display(info)
					.parent(root);

			for (DeferredBlock<? extends Block> registryObject : registryObjects) {
				builder.addCriterion(registryObject.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(registryObject.get()));
			}
			return builder.requirements(strategy).save(this.output, rootID(registryLocation.getPath()));
		}

		/**
		 * Adds an advancement for holding all the given items and trigger the killer function.
		 *
		 * @param displayObject   The display block registry object.
		 * @param root            The root advancement.
		 * @param path            The path of the advancement.
		 * @param deferredHolders The block registry objects that you need to hold one of.
		 */
		protected AdvancementHolder killerCollection(DeferredBlock<? extends Block> displayObject,
		                                             AdvancementHolder root, String path, DeferredBlock<? extends Block>... deferredHolders) {
			Identifier registryLocation = modLoc(path);

			Advancement.Builder builder = Advancement.Builder.advancement()
					.display(simpleDisplay(displayObject.get(), path, AdvancementType.GOAL))
					.parent(root);

			for (DeferredBlock<? extends Block> registryObject : deferredHolders) {
				builder.addCriterion(registryObject.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(registryObject.get()));
			}
			return builder
					.rewards(AdvancementRewards.Builder.function(modLoc("killer_rabbit")))
					.requirements(AdvancementRequirements.Strategy.AND)
					.save(this.output, rootID(registryLocation.getPath()));
		}

		/**
		 * Adds an advancement for holding a given item.
		 *
		 * @param deferredHolder The block registry object.
		 * @param type           The frame type.
		 * @param hidden         If the advancement is hidden.
		 * @param root           The root advancement.
		 */
		protected AdvancementHolder onHoldItem(DeferredHolder<Item, ? extends Item> deferredHolder,
		                                       AdvancementType type, boolean hidden, AdvancementHolder root) {
			String path = deferredHolder.getId().getPath();
			Identifier registryLocation = modLoc(path);

			DisplayInfo info = hidden ? hiddenDisplay(deferredHolder.get(), path, type) : simpleDisplay(deferredHolder.get(), path, type);
			return Advancement.Builder.advancement()
					.display(info)
					.parent(root)
					.addCriterion(path, onHeldItems(deferredHolder.get()))
					.save(this.output, rootID(registryLocation.getPath()));
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
		protected DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, Identifier background) {
			return new DisplayInfo(new ItemStackTemplate(icon.asItem()),
					Component.translatable(titleKey),
					Component.translatable(descKey),
					Optional.of(new ClientAsset.ResourceTexture(background)), AdvancementType.TASK, false, false, false);
		}

		/**
		 * Generate a simple DisplayInfo object.
		 *
		 * @param icon The icon to use.
		 * @param name The name of the advancement.
		 * @return The DisplayInfo object.
		 */
		protected DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType type) {
			return new DisplayInfo(new ItemStackTemplate(icon.asItem()),
					Component.translatable(advancementPrefix(name + ".title")),
					Component.translatable(advancementPrefix(name + ".desc")),
					Optional.empty(), type, true, true, false);
		}


		/**
		 * Generate a simple DisplayInfo object.
		 *
		 * @param icon The icon to use.
		 * @param name The name of the advancement.
		 * @return The DisplayInfo object.
		 */
		protected DisplayInfo hiddenDisplay(ItemLike icon, String name, AdvancementType type) {
			return new DisplayInfo(new ItemStackTemplate(icon.asItem()),
					Component.translatable(advancementPrefix(name + ".title")),
					Component.translatable(advancementPrefix(name + ".desc")),
					Optional.empty(), type, true, true, true);
		}

		/**
		 * Get a trigger instance for holding items.
		 *
		 * @param items The items.
		 * @return The trigger instance.
		 */
		protected Criterion<InventoryChangeTrigger.TriggerInstance> onHeldItems(ItemLike... items) {
			return InventoryChangeTrigger.TriggerInstance.hasItems(items);
		}

		/**
		 * Generate a Identifier that has the mod ID as the namespace.
		 *
		 * @param path The path.
		 * @return The Identifier.
		 */
		private Identifier modLoc(String path) {
			return Reference.modLoc(path);
		}

		/**
		 * Generate an advancement prefix.
		 *
		 * @param name The name of the advancement.
		 * @return The prefix.
		 */
		private String advancementPrefix(String name) {
			return "advancement." + Reference.MOD_ID + "." + name;
		}

		/**
		 * Generate a root advancement ID.
		 *
		 * @param name The name of the advancement.
		 * @return The advancement ID.
		 */
		private String rootID(String name) {
			return modLoc(name).toString();
		}
	}
}
