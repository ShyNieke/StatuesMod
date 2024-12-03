package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class StatueTags {
	public static final TagKey<Item> CURIOS_STATUE = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "statue"));

	public static final TagKey<Item> STATUES_ITEMS = ItemTags.create(Reference.modLoc("statues"));
	public static final TagKey<Item> UPGRADEABLE_STATUES = ItemTags.create(Reference.modLoc("statues/upgradeable"));
	public static final TagKey<Item> LOOTABLE_STATUES = ItemTags.create(Reference.modLoc("statues/lootable"));
	public static final TagKey<Item> STATUE_INTERACTABLE = ItemTags.create(Reference.modLoc("statues/interactable"));

	public static final TagKey<Block> STATUE_BLOCKS = BlockTags.create(Reference.modLoc("statues"));
	public static final TagKey<Block> IS_TROPICAL_FISH = BlockTags.create(Reference.modLoc("is_tropical_fish"));

	public static final TagKey<Item> PLAYER_UPGRADE_ITEM = ItemTags.create(Reference.modLoc("player_upgrade_item"));
	public static final TagKey<Item> STATUE_CORE = ItemTags.create(Reference.modLoc("core"));

	public static final TagKey<Biome> CAN_SPAWN_STATUE_BAT = TagKey.create(Registries.BIOME, Reference.modLoc("can_spawn_statue_bat"));
	public static final TagKey<Biome> CAN_SPAWN_FEWER_STATUE_BAT = TagKey.create(Registries.BIOME, Reference.modLoc("can_spawn_fewer_statue_bat"));

}
