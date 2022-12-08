package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class StatueTags {
	public static final TagKey<Item> CURIOS_STATUE = ItemTags.create(new ResourceLocation("curios", "statue"));

	public static final TagKey<Item> STATUES_ITEMS = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "statues"));
	public static final TagKey<Item> UPGRADEABLE_STATUES = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "statues/upgradeable"));
	public static final TagKey<Item> STATUE_INTERACTABLE = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "statues/interactable"));

	public static final TagKey<Block> STATUE_BLOCKS = BlockTags.create(new ResourceLocation(Reference.MOD_ID, "statues"));

	public static final TagKey<Item> PLAYER_UPGRADE_ITEM = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "player_upgrade_item"));
	public static final TagKey<Item> STATUE_CORE = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "core"));

	public static final TagKey<Biome> CAN_SPAWN_STATUE_BAT = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MOD_ID, "can_spawn_statue_bat"));
	public static final TagKey<Biome> CAN_SPAWN_FEWER_STATUE_BAT = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MOD_ID, "can_spawn_fewer_statue_bat"));

}
