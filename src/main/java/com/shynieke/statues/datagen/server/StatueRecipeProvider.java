package com.shynieke.statues.datagen.server;

import com.google.gson.JsonObject;
import com.shynieke.statues.Reference;
import com.shynieke.statues.datagen.server.recipe.LootRecipeBuilder;
import com.shynieke.statues.datagen.server.recipe.UpgradeRecipeBuilder;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.PartialNBTIngredient;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StatueRecipeProvider extends RecipeProvider {

	public StatueRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BABY_ZOMBIE_STATUE.get()))
				.result1(Items.ROTTEN_FLESH).result2(Items.IRON_NUGGET).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BEE_STATUE.get(), StatueRegistry.ANGRY_BEE_STATUE.get(), StatueRegistry.TRANS_BEE_STATUE.get()))
				.result2(Items.HONEYCOMB).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BLAZE_STATUE.get()))
				.result1(Items.BLAZE_POWDER).result2(Items.BLAZE_ROD).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CHICKEN_STATUE.get()))
				.result1(Items.FEATHER).result2(Items.CHICKEN).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CHICKEN_JOCKEY_STATUE.get()))
				.result1(Items.ROTTEN_FLESH).result2(Items.FEATHER).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.KING_CLUCK_STATUE.get()))
				.result1(StatueRegistry.NUGGET.get()).result3(Items.GOLD_NUGGET).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.COW_STATUE.get()))
				.result1(Items.BEEF).result3(Items.LEATHER).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CREEPER_STATUE.get()))
				.result1(Items.GUNPOWDER).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.ENDERMAN_STATUE.get()))
				.result1(new ItemStack(StatueRegistry.PEBBLE.get(), 16))
				.result2(Items.ENDER_PEARL).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.GHAST_STATUE.get()))
				.result1(Items.GUNPOWDER).result3(Items.GHAST_TEAR).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.GUARDIAN_STATUE.get()))
				.result1(Items.COD).result2(Items.PRISMARINE_SHARD).result3(Items.PRISMARINE_CRYSTALS).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.HUSK_STATUE.get())).group("zombie")
				.result1(Items.ROTTEN_FLESH).result3(Items.IRON_INGOT).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.DROWNED_STATUE.get())).group("zombie")
				.result1(Items.ROTTEN_FLESH).result3(Items.COPPER_INGOT).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.MAGMA_STATUE.get()))
				.result1(Items.MAGMA_CREAM).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.PIG_STATUE.get()))
				.result3(Items.PORKCHOP).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.RABBIT_BR_STATUE.get(), StatueRegistry.RABBIT_BS_STATUE.get(),
						StatueRegistry.RABBIT_BW_STATUE.get(), StatueRegistry.RABBIT_GO_STATUE.get(),
						StatueRegistry.RABBIT_WH_STATUE.get(), StatueRegistry.RABBIT_WS_STATUE.get()
				))
				.result1(Items.RABBIT_HIDE).result1(Items.RABBIT).result1(Items.RABBIT_FOOT).build(consumer, new ResourceLocation(Reference.MOD_ID, "rabbit_statue"));

		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_WHITE.get())).group("sheep")
				.result1(Blocks.WHITE_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_ORANGE.get())).group("sheep")
				.result1(Blocks.ORANGE_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_MAGENTA.get())).group("sheep")
				.result1(Blocks.MAGENTA_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.get())).group("sheep")
				.result1(Blocks.LIGHT_BLUE_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_YELLOW.get())).group("sheep")
				.result1(Blocks.YELLOW_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_LIME.get())).group("sheep")
				.result1(Blocks.LIME_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_PINK.get())).group("sheep")
				.result1(Blocks.PINK_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_GRAY.get())).group("sheep")
				.result1(Blocks.GRAY_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.get())).group("sheep")
				.result1(Blocks.LIGHT_GRAY_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_CYAN.get())).group("sheep")
				.result1(Blocks.CYAN_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_PURPLE.get())).group("sheep")
				.result1(Blocks.PURPLE_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_BLUE.get())).group("sheep")
				.result1(Blocks.BLUE_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_BROWN.get())).group("sheep")
				.result1(Blocks.BROWN_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_GREEN.get())).group("sheep")
				.result1(Blocks.GREEN_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_RED.get())).group("sheep")
				.result1(Blocks.RED_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_STATUE_BLACK.get())).group("sheep")
				.result1(Blocks.BLACK_WOOL).result3(Items.MUTTON).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SHEEP_SHAVEN_STATUE.get())).group("sheep")
				.result3(Items.MUTTON).build(consumer);

		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SLIME_STATUE.get()))
				.result2(Items.SLIME_BALL).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SNOW_GOLEM_STATUE.get()))
				.result1(Items.SNOWBALL).result3(Items.PUMPKIN).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SQUID_STATUE.get()))
				.result2(Items.INK_SAC).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.VILLAGER_BR_STATUE.get(), StatueRegistry.VILLAGER_GR_STATUE.get(),
						StatueRegistry.VILLAGER_PU_STATUE.get(), StatueRegistry.VILLAGER_WH_STATUE.get()))
				.result3(Items.EMERALD).build(consumer, new ResourceLocation(Reference.MOD_ID, "villager_statue")); //TODO: Use trading in future
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.WITCH_STATUE.get()))
				.result1(Items.GLOWSTONE_DUST).result2(Items.REDSTONE).result3(Items.GLASS_BOTTLE).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.WASTELAND_STATUE.get()))
				.result1(StatueRegistry.TEA.get()).result2(getWastelandBlock()).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.ZOMBIE_STATUE.get())).group("zombie")
				.result1(Items.ROTTEN_FLESH).result3(Items.IRON_INGOT).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.PUFFERFISH_STATUE.get(),
						StatueRegistry.PUFFERFISH_MEDIUM_STATUE.get(), StatueRegistry.PUFFERFISH_SMALL_STATUE.get()))
				.result2(Items.PUFFERFISH).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.SPIDER_STATUE.get()))
				.result1(Items.STRING).result2(Items.SPIDER_EYE).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.EVOKER_STATUE.get()))
				.result3(Items.TOTEM_OF_UNDYING).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.CAMPFIRE_STATUE.get()))
				.result1(StatueRegistry.MARSHMALLOW.get()).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.TURTLE_STATUE.get()))
				.result1(Items.SEAGRASS).result2(Items.BOWL).result3(Items.SCUTE).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.MOOSHROOM_STATUE.get()))
				.result1(Items.BEEF).result2(Items.RED_MUSHROOM).result3(Items.RED_MUSHROOM_BLOCK).build(consumer);
		LootRecipeBuilder.loot(Ingredient.of(StatueRegistry.BROWN_MOOSHROOM_STATUE.get()))
				.result1(Items.BEEF).result2(Items.BROWN_MUSHROOM).result3(Items.BROWN_MUSHROOM_BLOCK).build(consumer);

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), new ArrayList<>())
				.requiresCore().upgradeType(UpgradeType.UPGRADE).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/statue_upgrade"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.GLOW_INK_SAC)))
				.upgradeType(UpgradeType.GLOWING).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/glowing"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.INK_SAC)))
				.upgradeType(UpgradeType.UNGLOWING).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/unglowing"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.ECHO_SHARD),
						Ingredient.of(Tags.Items.EGGS), Ingredient.of(Tags.Items.ENDER_PEARLS), Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS)))
				.upgradeType(UpgradeType.SPAWNER).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/spawner"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.HEADS),
						Ingredient.of(Items.MYCELIUM), Ingredient.of(Items.LANTERN))).requiresCore()
				.upgradeType(UpgradeType.DESPAWNER).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/despawner"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.DIAMOND_SWORD))).tier(0)
				.upgradeType(UpgradeType.MOB_KILLER).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/mob_killer"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS),
						Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS), Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS), PartialNBTIngredient.of(Items.ENCHANTED_BOOK, new CompoundTag()))).tier(1)
				.upgradeType(UpgradeType.MOB_KILLER).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/mob_killer_2"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.EXPERIENCE_BOTTLE))).tier(2)
				.upgradeType(UpgradeType.MOB_KILLER).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/mob_killer_3"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.ENDER_PEARLS),
						Ingredient.of(Tags.Items.GUNPOWDER), Ingredient.of(Tags.Items.BONES), Ingredient.of(Items.ROTTEN_FLESH)))
				.upgradeType(UpgradeType.LOOTING).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/looting"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.HOPPER), Ingredient.of(Items.OBSERVER)))
				.upgradeType(UpgradeType.AUTOMATION).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/automation"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Tags.Items.DUSTS_REDSTONE),
						Ingredient.of(Items.SUGAR), Ingredient.of(Items.CLOCK)))
				.upgradeType(UpgradeType.SPEED).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/speed"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.STATUE_INTERACTABLE), List.of(Ingredient.of(Items.SCULK_SENSOR)))
				.upgradeType(UpgradeType.INTERACTION).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/interaction"));

		UpgradeRecipeBuilder.upgrade(Ingredient.of(StatueTags.UPGRADEABLE_STATUES), List.of(Ingredient.of(Items.NOTE_BLOCK),
						Ingredient.of(Items.AMETHYST_SHARD)))
				.upgradeType(UpgradeType.INTERACTION).build(consumer, new ResourceLocation(Reference.MOD_ID, "upgrade/sound"));
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
	protected void saveAdvancement(CachedOutput cache, JsonObject advancementJson, Path path) {
		// Nope
	}
}
