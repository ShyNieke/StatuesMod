package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.datagen.server.recipe.LootRecipeBuilder;
import com.shynieke.statues.datagen.server.recipe.UpgradeRecipeBuilder;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.NBTIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StatueRecipeProvider extends RecipeProvider {

	public StatueRecipeProvider(PackOutput packOutput, CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void buildRecipes(RecipeOutput consumer) {
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BABY_ZOMBIE_STATUE.get()))
				.result1(Items.ROTTEN_FLESH).result2(Items.IRON_NUGGET).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BEE_STATUE.get(), StatueRegistry.ANGRY_BEE_STATUE.get(), StatueRegistry.TRANS_BEE_STATUE.get()))
				.result2(Items.HONEYCOMB).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BLAZE_STATUE.get()))
				.result1(Items.BLAZE_POWDER).result2(Items.BLAZE_ROD).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CAT_BLACK_STATUE.get(), StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get(),
						StatueRegistry.CAT_CALICO_STATUE.get(), StatueRegistry.CAT_JELLIE_STATUE.get(),
						StatueRegistry.CAT_PERSIAN_STATUE.get(), StatueRegistry.CAT_RAGDOLL_STATUE.get(),
						StatueRegistry.CAT_RED_STATUE.get(), StatueRegistry.CAT_SIAMESE_STATUE.get(),
						StatueRegistry.CAT_TABBY_STATUE.get(), StatueRegistry.CAT_TUXEDO_STATUE.get(),
						StatueRegistry.CAT_WHITE_STATUE.get()))
				.result1(Items.STRING).result3(getIOU()).save(consumer,
						new ResourceLocation(Reference.MOD_ID, "loot/cat_statue")); //TODO: Allow the sleeping loot table
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CHICKEN_STATUE.get()))
				.result1(Items.FEATHER).result2(Items.CHICKEN).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CHICKEN_JOCKEY_STATUE.get()))
				.result1(Items.ROTTEN_FLESH).result2(Items.FEATHER).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.KING_CLUCK_STATUE.get()))
				.result1(StatueRegistry.NUGGET.get()).result3(Items.GOLD_NUGGET).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.COW_STATUE.get()))
				.result1(Items.BEEF).result3(Items.LEATHER).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CREEPER_STATUE.get()))
				.result1(Items.GUNPOWDER).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.COD_STATUE.get()))
				.result1(Items.COD).result2(Items.BONE_MEAL).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.DOLPHIN_STATUE.get()))
				.result1(Items.COD).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.ENDERMAN_STATUE.get()))
				.result1(new ItemStack(StatueRegistry.PEBBLE.get(), 16))
				.result2(Items.ENDER_PEARL).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.ELDER_GUARDIAN_STATUE.get()))
				.result1(Items.PRISMARINE_CRYSTALS, 0.75F).result2(Items.WET_SPONGE).result3(Items.PRISMARINE_CRYSTALS, 0.25F).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.FLOOD_STATUE.get()))
				.result1(Items.FIREWORK_ROCKET).result2(Items.ROTTEN_FLESH).result3(Items.EMERALD).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.FOX_STATUE.get(), StatueRegistry.FOX_SNOW_STATUE.get()))
				.result1(Items.SWEET_BERRIES).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.GHAST_STATUE.get()))
				.result1(Items.GUNPOWDER).result3(Items.GHAST_TEAR).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.GUARDIAN_STATUE.get()))
				.result1(Items.COD).result2(Items.PRISMARINE_SHARD).result3(Items.PRISMARINE_CRYSTALS).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.HUSK_STATUE.get())).group("zombie")
				.result1(Items.ROTTEN_FLESH).result3(Items.IRON_INGOT).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.DROWNED_STATUE.get())).group("zombie")
				.result1(Items.ROTTEN_FLESH).result3(Items.COPPER_INGOT).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.MAGMA_STATUE.get()))
				.result1(Items.MAGMA_CREAM).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.PIG_STATUE.get()))
				.result3(Items.PORKCHOP).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.PANDA_ANGRY_STATUE.get(),
						StatueRegistry.PANDA_BROWN_STATUE.get(), StatueRegistry.PANDA_LAZY_STATUE.get(),
						StatueRegistry.PANDA_NORMAL_STATUE.get(), StatueRegistry.PANDA_PLAYFUL_STATUE.get(),
						StatueRegistry.PANDA_WEAK_STATUE.get(), StatueRegistry.PANDA_WORRIED_STATUE.get()))
				.result1(Items.BAMBOO).save(consumer,
						new ResourceLocation(Reference.MOD_ID, "loot/panda_statue"));
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.PILLAGER_STATUE.get()))
				.result1(Items.ARROW).result2(Items.CROSSBOW, 0.25F).result3(Raid.getLeaderBannerInstance(), 0.05F).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.RABBIT_BR_STATUE.get(), StatueRegistry.RABBIT_BS_STATUE.get(),
						StatueRegistry.RABBIT_BW_STATUE.get(), StatueRegistry.RABBIT_GO_STATUE.get(),
						StatueRegistry.RABBIT_WH_STATUE.get(), StatueRegistry.RABBIT_WS_STATUE.get()
				))
				.result1(Items.RABBIT_HIDE).result1(Items.RABBIT).result1(Items.RABBIT_FOOT).save(consumer,
						new ResourceLocation(Reference.MOD_ID, "loot/rabbit_statue"));
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.RAVAGER_STATUE.get()))
				.result3(Items.SADDLE).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SALMON_STATUE.get()))
				.result1(Items.SALMON).result2(Items.BONE_MEAL).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.TROPICAL_FISH_B.get(), StatueRegistry.TROPICAL_FISH_BB.get(),
						StatueRegistry.TROPICAL_FISH_BE.get(), StatueRegistry.TROPICAL_FISH_BM.get(),
						StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(),
						StatueRegistry.TROPICAL_FISH_E.get(), StatueRegistry.TROPICAL_FISH_ES.get(),
						StatueRegistry.TROPICAL_FISH_HB.get(), StatueRegistry.TROPICAL_FISH_SB.get(),
						StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get()))
				.result1(Items.TROPICAL_FISH).result2(Items.BONE_MEAL).save(consumer,
						new ResourceLocation(Reference.MOD_ID, "loot/tropical_fish"));
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHULKER_STATUE.get()))
				.result3(Items.SHULKER_SHELL).save(consumer);

		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_WHITE.get())).group("sheep")
				.result1(Blocks.WHITE_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_ORANGE.get())).group("sheep")
				.result1(Blocks.ORANGE_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_MAGENTA.get())).group("sheep")
				.result1(Blocks.MAGENTA_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.get())).group("sheep")
				.result1(Blocks.LIGHT_BLUE_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_YELLOW.get())).group("sheep")
				.result1(Blocks.YELLOW_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_LIME.get())).group("sheep")
				.result1(Blocks.LIME_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_PINK.get())).group("sheep")
				.result1(Blocks.PINK_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_GRAY.get())).group("sheep")
				.result1(Blocks.GRAY_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.get())).group("sheep")
				.result1(Blocks.LIGHT_GRAY_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_CYAN.get())).group("sheep")
				.result1(Blocks.CYAN_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_PURPLE.get())).group("sheep")
				.result1(Blocks.PURPLE_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_BLUE.get())).group("sheep")
				.result1(Blocks.BLUE_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_BROWN.get())).group("sheep")
				.result1(Blocks.BROWN_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_GREEN.get())).group("sheep")
				.result1(Blocks.GREEN_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_RED.get())).group("sheep")
				.result1(Blocks.RED_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_BLACK.get())).group("sheep")
				.result1(Blocks.BLACK_WOOL).result3(Items.MUTTON).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_SHAVEN_STATUE.get())).group("sheep")
				.result3(Items.MUTTON).save(consumer);

		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SLIME_STATUE.get()))
				.result2(Items.SLIME_BALL).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SNOW_GOLEM_STATUE.get()))
				.result1(Items.SNOWBALL).result3(Items.PUMPKIN).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SQUID_STATUE.get()))
				.result2(Items.INK_SAC).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.VILLAGER_BR_STATUE.get(), StatueRegistry.VILLAGER_GR_STATUE.get(),
						StatueRegistry.VILLAGER_PU_STATUE.get(), StatueRegistry.VILLAGER_WH_STATUE.get()))
				.result3(Items.EMERALD).save(consumer, new ResourceLocation(Reference.MOD_ID, "loot/villager_statue")); //TODO: Use trading in future
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.WITCH_STATUE.get()))
				.result1(Items.GLOWSTONE_DUST).result2(Items.REDSTONE).result3(Items.GLASS_BOTTLE).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.WASTELAND_STATUE.get()))
				.result1(StatueRegistry.TEA.get()).result2(getWastelandBlock()).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.ZOMBIE_STATUE.get())).group("zombie")
				.result1(Items.ROTTEN_FLESH).result3(Items.IRON_INGOT).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.PUFFERFISH_STATUE.get(),
						StatueRegistry.PUFFERFISH_MEDIUM_STATUE.get(), StatueRegistry.PUFFERFISH_SMALL_STATUE.get()))
				.result2(Items.PUFFERFISH).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SPIDER_STATUE.get()))
				.result1(Items.STRING).result2(Items.SPIDER_EYE).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.EVOKER_STATUE.get()))
				.result3(Items.TOTEM_OF_UNDYING).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CAMPFIRE_STATUE.get()))
				.result1(StatueRegistry.MARSHMALLOW.get()).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.TURTLE_STATUE.get()))
				.result1(Items.SEAGRASS).result2(Items.BOWL).result3(Items.SCUTE).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.MOOSHROOM_STATUE.get()))
				.result1(Items.BEEF).result2(Items.RED_MUSHROOM).result3(Items.RED_MUSHROOM_BLOCK).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BROWN_MOOSHROOM_STATUE.get()))
				.result1(Items.BEEF).result2(Items.BROWN_MUSHROOM).result3(Items.BROWN_MUSHROOM_BLOCK).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.VINDICATOR_STATUE.get()))
				.result2(Items.EMERALD).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.ALLAY_STATUE.get()))
				.result1(Items.LIGHT_BLUE_DYE).result2(Items.LIGHT_BLUE_DYE).result3(Items.LIGHT_BLUE_DYE).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.AXOLOTL_LUCY_STATUE.get())).result1(Items.TROPICAL_FISH).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.AXOLOTL_WILD_STATUE.get())).result1(Items.TROPICAL_FISH).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.AXOLOTL_GOLD_STATUE.get())).result1(Items.TROPICAL_FISH).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.AXOLOTL_CYAN_STATUE.get())).result1(Items.TROPICAL_FISH).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.AXOLOTL_BLUE_STATUE.get())).result1(Items.TROPICAL_FISH).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.FROG_TEMPERATE_STATUE.get()))
				.result1(Items.SLIME_BALL).result3(Items.OCHRE_FROGLIGHT).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.FROG_WARM_STATUE.get()))
				.result1(Items.SLIME_BALL).result3(Items.PEARLESCENT_FROGLIGHT).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.FROG_COLD_STATUE.get()))
				.result1(Items.SLIME_BALL).result3(Items.VERDANT_FROGLIGHT).save(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.WARDEN_STATUE.get()))
				.result1(Items.SCULK_SENSOR).result2(Items.SCULK_SHRIEKER).result3(Items.SCULK_CATALYST).save(consumer);

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), new ArrayList<>())
				.requiresCore().upgradeType(UpgradeType.UPGRADE).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/statue_upgrade"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.GLOW_INK_SAC)))
				.upgradeType(UpgradeType.GLOWING).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/glowing"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.INK_SAC)))
				.upgradeType(UpgradeType.UNGLOWING).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/unglowing"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.ECHO_SHARD),
						Ingredient.of(Tags.Items.EGGS), Ingredient.of(Tags.Items.ENDER_PEARLS), Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS)))
				.upgradeType(UpgradeType.SPAWNER).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/spawner"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.HEADS),
						Ingredient.of(Items.MYCELIUM), Ingredient.of(Items.LANTERN)))
				.upgradeType(UpgradeType.DESPAWNER).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/despawner"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.DIAMOND_SWORD))).tier(0)
				.upgradeType(UpgradeType.MOB_KILLER).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/mob_killer"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS),
						Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS), Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS),
						NBTIngredient.of(false, new CompoundTag(), Items.ENCHANTED_BOOK))).tier(1)
				.upgradeType(UpgradeType.MOB_KILLER).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/mob_killer_2"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.EXPERIENCE_BOTTLE))).tier(2)
				.upgradeType(UpgradeType.MOB_KILLER).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/mob_killer_3"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.ENDER_PEARLS),
						Ingredient.of(Tags.Items.GUNPOWDER), Ingredient.of(Tags.Items.BONES), Ingredient.of(Items.ROTTEN_FLESH)))
				.upgradeType(UpgradeType.LOOTING).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/looting"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.HOPPER), Ingredient.of(Items.OBSERVER)))
				.upgradeType(UpgradeType.AUTOMATION).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/automation"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.DUSTS_REDSTONE),
						Ingredient.of(Items.SUGAR), Ingredient.of(Items.CLOCK)))
				.upgradeType(UpgradeType.SPEED).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/speed"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.STATUE_INTERACTABLE), List.of(Ingredient.of(Items.SCULK_SENSOR)))
				.upgradeType(UpgradeType.INTERACTION).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/interaction"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.NOTE_BLOCK),
						Ingredient.of(Items.AMETHYST_SHARD)))
				.upgradeType(UpgradeType.INTERACTION).save(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/sound"));

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, StatueRegistry.STATUE_TABLE.get())
				.pattern(" P ")
				.pattern("DCD")
				.pattern("DDD")
				.define('P', Items.PISTON)
				.define('C', Tags.Items.CHESTS_WOODEN)
				.define('D', Tags.Items.COBBLESTONE_DEEPSLATE)
				.unlockedBy("has_wooden_chest", has(Tags.Items.CHESTS_WOODEN)).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, StatueRegistry.INFO_STATUE.get())
				.pattern(" R ").pattern("BCB").pattern("CCC")
				.define('C', Tags.Items.COBBLESTONE)
				.define('B', Items.BOOK)
				.define('R', Tags.Items.DYES_RED)
				.unlockedBy("has_book", has(Items.BOOK)).save(consumer);

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.QUARTZ_BLOCK), RecipeCategory.DECORATIONS,
						StatueRegistry.DISPLAY_STAND.get(), 2)
				.unlockedBy("has_quartz_block", has(Items.QUARTZ_BLOCK))
				.save(consumer, new ResourceLocation(Reference.MOD_ID, "display_stand_from_quartz_block_stonecutting"));

	}


	private static ItemStack getIOU() {
		ItemStack paperStack = new ItemStack(Items.PAPER).setHoverName(Component.literal("I.O.U").withStyle(ChatFormatting.LIGHT_PURPLE));
		return paperStack;
	}

	private static ItemStack getWastelandBlock() {
		ItemStack wasteland = new ItemStack(Blocks.SAND).setHoverName(Component.literal("Wasteland Block").withStyle(ChatFormatting.LIGHT_PURPLE));
		wasteland.enchant(Enchantments.VANISHING_CURSE, 1);
		CompoundTag nbt = wasteland.hasTag() ? wasteland.getTag() : new CompoundTag();
		if (nbt != null) {
			nbt.putInt("HideFlags", 1);
			wasteland.setTag(nbt);
		}
		return wasteland;
	}

	@Override
	protected CompletableFuture<?> buildAdvancement(CachedOutput cachedOutput, AdvancementHolder advancementHolder) {
		return null;
	}
}
