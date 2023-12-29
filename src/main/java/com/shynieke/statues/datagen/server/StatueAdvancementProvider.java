package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class StatueAdvancementProvider extends AdvancementProvider {
	public StatueAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
		super(output, registries, existingFileHelper, List.of(new StatueAdvancementGenerator()));
	}

	public static class StatueAdvancementGenerator implements AdvancementGenerator {

		@Override
		public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
			//Root advancement
			AdvancementHolder root = Advancement.Builder.advancement()
					.display(rootDisplay(StatueRegistry.STATUE_CORE.get(), advancementPrefix("root" + ".title"),
							advancementPrefix("root" + ".desc"), modLoc("textures/block/pebble.png")))
					.addCriterion("core", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.AIR))
					.save(consumer, rootID("root"));

			AdvancementHolder elderGuardian = onHoldBlock(consumer, StatueRegistry.ELDER_GUARDIAN_STATUE, AdvancementType.GOAL, false, root);
			onHoldBlock(consumer, StatueRegistry.RAVAGER_STATUE, AdvancementType.GOAL, false, elderGuardian);

			AdvancementHolder zombie = onHoldBlock(consumer, StatueRegistry.ZOMBIE_STATUE, root);
			AdvancementHolder babyZombie = onHoldBlock(consumer, StatueRegistry.BABY_ZOMBIE_STATUE, zombie);
			AdvancementHolder flood = onHoldBlock(consumer, StatueRegistry.FLOOD_STATUE, AdvancementType.GOAL, true, babyZombie);
			AdvancementHolder husk = onHoldBlock(consumer, StatueRegistry.HUSK_STATUE, zombie);
			AdvancementHolder creeper = onHoldBlock(consumer, StatueRegistry.CREEPER_STATUE, husk);
			AdvancementHolder campfire = onHoldBlock(consumer, StatueRegistry.CAMPFIRE_STATUE, AdvancementType.CHALLENGE, true, creeper);
			AdvancementHolder drowned = onHoldBlock(consumer, StatueRegistry.DROWNED_STATUE, husk);
			AdvancementHolder guardian = onHoldBlock(consumer, StatueRegistry.GUARDIAN_STATUE, drowned);
			AdvancementHolder spider = onHoldBlock(consumer, StatueRegistry.SPIDER_STATUE, husk);
			AdvancementHolder slime = onHoldBlock(consumer, StatueRegistry.SLIME_STATUE, spider);

			AdvancementHolder statueTable = onHoldBlock(consumer, StatueRegistry.STATUE_TABLE, root);
			AdvancementHolder displayStand = onHoldBlock(consumer, StatueRegistry.DISPLAY_STAND, statueTable);
			AdvancementHolder infoStatue = onHoldBlock(consumer, StatueRegistry.INFO_STATUE, displayStand);
			AdvancementHolder bumbo = onHoldBlock(consumer, StatueRegistry.BUMBO_STATUE, infoStatue);

			AdvancementHolder enderman = onHoldBlock(consumer, StatueRegistry.ENDERMAN_STATUE, root);
			AdvancementHolder endermite = onHoldBlock(consumer, StatueRegistry.ENDERMITE_STATUE, enderman);
			AdvancementHolder shulker = onHoldBlock(consumer, StatueRegistry.SHULKER_STATUE, endermite);

			AdvancementHolder cod = onHoldBlock(consumer, StatueRegistry.COD_STATUE, root);
			AdvancementHolder salmon = onHoldBlock(consumer, StatueRegistry.SALMON_STATUE, cod);
			AdvancementHolder tropical = onHoldAnyBlock(consumer, StatueRegistry.TROPICAL_FISH_B, AdvancementType.TASK, false,
					salmon, "tropical_fish_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.TROPICAL_FISH_B, StatueRegistry.TROPICAL_FISH_BB, StatueRegistry.TROPICAL_FISH_BE,
					StatueRegistry.TROPICAL_FISH_BM, StatueRegistry.TROPICAL_FISH_BMB, StatueRegistry.TROPICAL_FISH_BMS,
					StatueRegistry.TROPICAL_FISH_E, StatueRegistry.TROPICAL_FISH_ES, StatueRegistry.TROPICAL_FISH_HB,
					StatueRegistry.TROPICAL_FISH_SB, StatueRegistry.TROPICAL_FISH_SD, StatueRegistry.TROPICAL_FISH_SS
			);
			AdvancementHolder tropicalAll = onHoldAnyBlock(consumer, StatueRegistry.TROPICAL_FISH_BB, AdvancementType.GOAL, false,
					tropical, "tropical_fish_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.TROPICAL_FISH_B, StatueRegistry.TROPICAL_FISH_BB, StatueRegistry.TROPICAL_FISH_BE,
					StatueRegistry.TROPICAL_FISH_BM, StatueRegistry.TROPICAL_FISH_BMB, StatueRegistry.TROPICAL_FISH_BMS,
					StatueRegistry.TROPICAL_FISH_E, StatueRegistry.TROPICAL_FISH_ES, StatueRegistry.TROPICAL_FISH_HB,
					StatueRegistry.TROPICAL_FISH_SB, StatueRegistry.TROPICAL_FISH_SD, StatueRegistry.TROPICAL_FISH_SS
			);
			AdvancementHolder axolotl = onHoldAnyBlock(consumer, StatueRegistry.AXOLOTL_LUCY_STATUE, AdvancementType.TASK, false,
					salmon, "axolotl_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.AXOLOTL_LUCY_STATUE, StatueRegistry.AXOLOTL_WILD_STATUE,
					StatueRegistry.AXOLOTL_GOLD_STATUE, StatueRegistry.AXOLOTL_CYAN_STATUE,
					StatueRegistry.AXOLOTL_BLUE_STATUE
			);
			AdvancementHolder axolotlAll = onHoldAnyBlock(consumer, StatueRegistry.AXOLOTL_GOLD_STATUE, AdvancementType.GOAL, false,
					axolotl, "axolotl_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.AXOLOTL_LUCY_STATUE, StatueRegistry.AXOLOTL_WILD_STATUE,
					StatueRegistry.AXOLOTL_GOLD_STATUE, StatueRegistry.AXOLOTL_CYAN_STATUE,
					StatueRegistry.AXOLOTL_BLUE_STATUE
			);

			AdvancementHolder pufferSmall = onHoldBlock(consumer, StatueRegistry.PUFFERFISH_SMALL_STATUE, salmon);
			AdvancementHolder pufferMedium = onHoldBlock(consumer, StatueRegistry.PUFFERFISH_MEDIUM_STATUE, pufferSmall);
			AdvancementHolder puffer = onHoldBlock(consumer, StatueRegistry.PUFFERFISH_STATUE, pufferMedium);
			AdvancementHolder dolphin = onHoldBlock(consumer, StatueRegistry.DOLPHIN_STATUE, salmon);
			AdvancementHolder squid = onHoldBlock(consumer, StatueRegistry.SQUID_STATUE, dolphin);
			AdvancementHolder turtle = onHoldBlock(consumer, StatueRegistry.TURTLE_STATUE, squid);

			AdvancementHolder witch = onHoldBlock(consumer, StatueRegistry.WITCH_STATUE, root);
			AdvancementHolder pillager = onHoldBlock(consumer, StatueRegistry.PILLAGER_STATUE, witch);
			AdvancementHolder vindicator = onHoldBlock(consumer, StatueRegistry.VINDICATOR_STATUE, pillager);
			onHoldBlock(consumer, StatueRegistry.EVOKER_STATUE, vindicator);

			AdvancementHolder cow = onHoldBlock(consumer, StatueRegistry.COW_STATUE, root);
			AdvancementHolder mooshroom = onHoldBlock(consumer, StatueRegistry.MOOSHROOM_STATUE, cow);
			AdvancementHolder mooshroomBrown = onHoldBlock(consumer, StatueRegistry.BROWN_MOOSHROOM_STATUE, mooshroom);
			AdvancementHolder pig = onHoldBlock(consumer, StatueRegistry.PIG_STATUE, cow);
			AdvancementHolder snowman = onHoldBlock(consumer, StatueRegistry.SNOW_GOLEM_STATUE, cow);
			AdvancementHolder allay = onHoldBlock(consumer, StatueRegistry.ALLAY_STATUE, cow);

			AdvancementHolder wasteland = onHoldBlock(consumer, StatueRegistry.WASTELAND_STATUE, AdvancementType.CHALLENGE, true, pig);
			AdvancementHolder chicken = onHoldBlock(consumer, StatueRegistry.CHICKEN_STATUE, cow);
			AdvancementHolder kingCluck = onHoldBlock(consumer, StatueRegistry.KING_CLUCK_STATUE, AdvancementType.GOAL, true, chicken);
			AdvancementHolder chickenJockey = onHoldBlock(consumer, StatueRegistry.CHICKEN_JOCKEY_STATUE, chicken);
			AdvancementHolder sheep = onHoldAnyBlock(consumer, StatueRegistry.SHEEP_STATUE_WHITE, AdvancementType.TASK, false,
					cow, "sheep_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.SHEEP_SHAVEN_STATUE, StatueRegistry.SHEEP_STATUE_BLACK, StatueRegistry.SHEEP_STATUE_BLUE,
					StatueRegistry.SHEEP_STATUE_BROWN, StatueRegistry.SHEEP_STATUE_CYAN, StatueRegistry.SHEEP_STATUE_GRAY,
					StatueRegistry.SHEEP_STATUE_GREEN, StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, StatueRegistry.SHEEP_STATUE_LIGHT_GRAY,
					StatueRegistry.SHEEP_STATUE_LIME, StatueRegistry.SHEEP_STATUE_MAGENTA, StatueRegistry.SHEEP_STATUE_ORANGE,
					StatueRegistry.SHEEP_STATUE_PINK, StatueRegistry.SHEEP_STATUE_PURPLE, StatueRegistry.SHEEP_STATUE_RED,
					StatueRegistry.SHEEP_STATUE_WHITE, StatueRegistry.SHEEP_STATUE_YELLOW
			);
			AdvancementHolder sheepAll = onHoldAnyBlock(consumer, StatueRegistry.SHEEP_STATUE_WHITE, AdvancementType.GOAL, false,
					sheep, "sheep_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.SHEEP_SHAVEN_STATUE, StatueRegistry.SHEEP_STATUE_BLACK, StatueRegistry.SHEEP_STATUE_BLUE,
					StatueRegistry.SHEEP_STATUE_BROWN, StatueRegistry.SHEEP_STATUE_CYAN, StatueRegistry.SHEEP_STATUE_GRAY,
					StatueRegistry.SHEEP_STATUE_GREEN, StatueRegistry.SHEEP_STATUE_LIGHT_BLUE, StatueRegistry.SHEEP_STATUE_LIGHT_GRAY,
					StatueRegistry.SHEEP_STATUE_LIME, StatueRegistry.SHEEP_STATUE_MAGENTA, StatueRegistry.SHEEP_STATUE_ORANGE,
					StatueRegistry.SHEEP_STATUE_PINK, StatueRegistry.SHEEP_STATUE_PURPLE, StatueRegistry.SHEEP_STATUE_RED,
					StatueRegistry.SHEEP_STATUE_WHITE, StatueRegistry.SHEEP_STATUE_YELLOW
			);

			AdvancementHolder cat = onHoldAnyBlock(consumer, StatueRegistry.CAT_JELLIE_STATUE, AdvancementType.TASK, false,
					root, "cat_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.CAT_BLACK_STATUE, StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, StatueRegistry.CAT_CALICO_STATUE,
					StatueRegistry.CAT_JELLIE_STATUE, StatueRegistry.CAT_PERSIAN_STATUE, StatueRegistry.CAT_RAGDOLL_STATUE,
					StatueRegistry.CAT_RED_STATUE, StatueRegistry.CAT_SIAMESE_STATUE, StatueRegistry.CAT_TABBY_STATUE,
					StatueRegistry.CAT_TUXEDO_STATUE, StatueRegistry.CAT_WHITE_STATUE
			);
			AdvancementHolder catAll = onHoldAnyBlock(consumer, StatueRegistry.CAT_TUXEDO_STATUE, AdvancementType.GOAL, false,
					cat, "cat_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.CAT_BLACK_STATUE, StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE, StatueRegistry.CAT_CALICO_STATUE,
					StatueRegistry.CAT_JELLIE_STATUE, StatueRegistry.CAT_PERSIAN_STATUE, StatueRegistry.CAT_RAGDOLL_STATUE,
					StatueRegistry.CAT_RED_STATUE, StatueRegistry.CAT_SIAMESE_STATUE, StatueRegistry.CAT_TABBY_STATUE,
					StatueRegistry.CAT_TUXEDO_STATUE, StatueRegistry.CAT_WHITE_STATUE
			);
			AdvancementHolder fox = onHoldBlock(consumer, StatueRegistry.FOX_STATUE, cat);
			AdvancementHolder foxSnow = onHoldBlock(consumer, StatueRegistry.FOX_SNOW_STATUE, fox);
			AdvancementHolder panda = onHoldAnyBlock(consumer, StatueRegistry.PANDA_LAZY_STATUE, AdvancementType.TASK, false,
					foxSnow, "panda_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.PANDA_ANGRY_STATUE, StatueRegistry.PANDA_BROWN_STATUE, StatueRegistry.PANDA_LAZY_STATUE,
					StatueRegistry.PANDA_NORMAL_STATUE, StatueRegistry.PANDA_PLAYFUL_STATUE, StatueRegistry.PANDA_WEAK_STATUE,
					StatueRegistry.PANDA_WORRIED_STATUE
			);
			AdvancementHolder pandaAll = onHoldAnyBlock(consumer, StatueRegistry.PANDA_BROWN_STATUE, AdvancementType.GOAL, false,
					panda, "panda_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.PANDA_ANGRY_STATUE, StatueRegistry.PANDA_BROWN_STATUE, StatueRegistry.PANDA_LAZY_STATUE,
					StatueRegistry.PANDA_NORMAL_STATUE, StatueRegistry.PANDA_PLAYFUL_STATUE, StatueRegistry.PANDA_WEAK_STATUE,
					StatueRegistry.PANDA_WORRIED_STATUE
			);
			AdvancementHolder bee = onHoldBlock(consumer, StatueRegistry.BEE_STATUE, foxSnow);
			AdvancementHolder beeTrans = onHoldBlock(consumer, StatueRegistry.TRANS_BEE_STATUE, AdvancementType.GOAL, false, bee);
			AdvancementHolder beeAngry = onHoldBlock(consumer, StatueRegistry.ANGRY_BEE_STATUE, bee);
			AdvancementHolder rabbit = onHoldAnyBlock(consumer, StatueRegistry.RABBIT_BR_STATUE, AdvancementType.TASK, false,
					foxSnow, "rabbit_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.RABBIT_BR_STATUE, StatueRegistry.RABBIT_BS_STATUE, StatueRegistry.RABBIT_BW_STATUE,
					StatueRegistry.RABBIT_GO_STATUE, StatueRegistry.RABBIT_WH_STATUE, StatueRegistry.RABBIT_WS_STATUE
			);
			AdvancementHolder rabbitAll = killerCollection(consumer, StatueRegistry.RABBIT_WH_STATUE, rabbit, "rabbit_all_statue",
					StatueRegistry.RABBIT_BR_STATUE, StatueRegistry.RABBIT_BS_STATUE, StatueRegistry.RABBIT_BW_STATUE,
					StatueRegistry.RABBIT_GO_STATUE, StatueRegistry.RABBIT_WH_STATUE, StatueRegistry.RABBIT_WS_STATUE
			);
			AdvancementHolder frog = onHoldAnyBlock(consumer, StatueRegistry.FROG_TEMPERATE_STATUE, AdvancementType.TASK, false,
					root, "frog_statue", AdvancementRequirements.Strategy.OR,
					StatueRegistry.FROG_TEMPERATE_STATUE, StatueRegistry.FROG_WARM_STATUE, StatueRegistry.FROG_COLD_STATUE
			);
			AdvancementHolder frogAll = onHoldAnyBlock(consumer, StatueRegistry.FROG_COLD_STATUE, AdvancementType.GOAL, false,
					frog, "frog_all_statue", AdvancementRequirements.Strategy.AND,
					StatueRegistry.FROG_TEMPERATE_STATUE, StatueRegistry.FROG_WARM_STATUE, StatueRegistry.FROG_COLD_STATUE
			);
			AdvancementHolder tadpole = onHoldBlock(consumer, StatueRegistry.TADPOLE_STATUE, frog);

			AdvancementHolder blaze = onHoldBlock(consumer, StatueRegistry.BLAZE_STATUE, root);
			AdvancementHolder ghast = onHoldBlock(consumer, StatueRegistry.GHAST_STATUE, blaze);
			AdvancementHolder magma = onHoldBlock(consumer, StatueRegistry.MAGMA_STATUE, ghast);

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
		protected static AdvancementHolder onHoldBlock(Consumer<AdvancementHolder> consumer, DeferredHolder<Block, ? extends Block> registryObject,
													   AdvancementType type, boolean hidden, AdvancementHolder root) {
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
		 * @param deferredHolder The block registry object.
		 * @param root           The root advancement.
		 */
		protected static AdvancementHolder onHoldBlock(Consumer<AdvancementHolder> consumer, DeferredBlock<? extends Block> deferredHolder,
													   AdvancementHolder root) {
			String path = deferredHolder.getId().getPath();
			ResourceLocation registryLocation = modLoc(path);

			return Advancement.Builder.advancement()
					.display(simpleDisplay(deferredHolder.get(), path, AdvancementType.TASK))
					.parent(root)
					.addCriterion(path, onHeldItems(deferredHolder.get()))
					.save(consumer, rootID(registryLocation.getPath()));
		}


		/**
		 * Adds an advancement for holding any/all the given items.
		 *
		 * @param consumer        The consumer to add to.
		 * @param deferredHolder  The display block registry object.
		 * @param type            The frame type.
		 * @param hidden          If the advancement is hidden.
		 * @param root            The root advancement.
		 * @param path            The path of the advancement.
		 * @param registryObjects The block registry objects that you need to hold one of.
		 */
		protected static AdvancementHolder onHoldAnyBlock(Consumer<AdvancementHolder> consumer, DeferredBlock<? extends Block> deferredHolder,
														  AdvancementType type, boolean hidden, AdvancementHolder root, String path,
														  AdvancementRequirements.Strategy strategy, DeferredBlock<? extends Block>... registryObjects) {
			ResourceLocation registryLocation = modLoc(path);

			DisplayInfo info = hidden ? hiddenDisplay(deferredHolder.get(), path, type) : simpleDisplay(deferredHolder.get(), path, type);
			Advancement.Builder builder = Advancement.Builder.advancement()
					.display(info)
					.parent(root);

			for (DeferredBlock<? extends Block> registryObject : registryObjects) {
				builder.addCriterion(registryObject.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(registryObject.get()));
			}
			return builder.requirements(strategy).save(consumer, rootID(registryLocation.getPath()));
		}

		/**
		 * Adds an advancement for holding all the given items and trigger the killer function.
		 *
		 * @param consumer        The consumer to add to.
		 * @param displayObject   The display block registry object.
		 * @param root            The root advancement.
		 * @param path            The path of the advancement.
		 * @param deferredHolders The block registry objects that you need to hold one of.
		 */
		protected static AdvancementHolder killerCollection(Consumer<AdvancementHolder> consumer, DeferredBlock<? extends Block> displayObject,
															AdvancementHolder root, String path, DeferredBlock<? extends Block>... deferredHolders) {
			ResourceLocation registryLocation = modLoc(path);

			Advancement.Builder builder = Advancement.Builder.advancement()
					.display(simpleDisplay(displayObject.get(), path, AdvancementType.GOAL))
					.parent(root);

			for (DeferredBlock<? extends Block> registryObject : deferredHolders) {
				builder.addCriterion(registryObject.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(registryObject.get()));
			}
			return builder
					.rewards(AdvancementRewards.Builder.function(modLoc("killer_rabbit")))
					.requirements(AdvancementRequirements.Strategy.AND)
					.save(consumer, rootID(registryLocation.getPath()));
		}

		/**
		 * Adds an advancement for holding a given item.
		 *
		 * @param consumer       The consumer to add to.
		 * @param deferredHolder The block registry object.
		 * @param type           The frame type.
		 * @param hidden         If the advancement is hidden.
		 * @param root           The root advancement.
		 */
		protected static AdvancementHolder onHoldItem(Consumer<AdvancementHolder> consumer, DeferredHolder<Item, ? extends Item> deferredHolder,
													  AdvancementType type, boolean hidden, AdvancementHolder root) {
			String path = deferredHolder.getId().getPath();
			ResourceLocation registryLocation = modLoc(path);

			DisplayInfo info = hidden ? hiddenDisplay(deferredHolder.get(), path, type) : simpleDisplay(deferredHolder.get(), path, type);
			return Advancement.Builder.advancement()
					.display(info)
					.parent(root)
					.addCriterion(path, onHeldItems(deferredHolder.get()))
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
					Optional.of(background), AdvancementType.TASK, false, false, false);
		}

		/**
		 * Generate a simple DisplayInfo object.
		 *
		 * @param icon The icon to use.
		 * @param name The name of the advancement.
		 * @return The DisplayInfo object.
		 */
		protected static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType type) {
			return new DisplayInfo(new ItemStack(icon),
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
		protected static DisplayInfo hiddenDisplay(ItemLike icon, String name, AdvancementType type) {
			return new DisplayInfo(new ItemStack(icon),
					Component.translatable(advancementPrefix(name + ".title")),
					Component.translatable(advancementPrefix(name + ".desc")),
					Optional.empty(), type, true, true, true);
		}

		/**
		 * Get a trigger instance for killing an entity.
		 *
		 * @param entityType The entity type.
		 * @return The trigger instance.
		 */
		protected static Criterion<KilledTrigger.TriggerInstance> onKill(EntityType<?> entityType) {
			return KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityType));
		}

		/**
		 * Get a trigger instance for holding items.
		 *
		 * @param items The items.
		 * @return The trigger instance.
		 */
		protected static Criterion<InventoryChangeTrigger.TriggerInstance> onHeldItems(ItemLike... items) {
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
}
