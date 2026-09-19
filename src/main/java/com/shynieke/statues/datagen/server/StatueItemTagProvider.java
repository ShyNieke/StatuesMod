package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.concurrent.CompletableFuture;

public class StatueItemTagProvider extends ItemTagsProvider {

	public StatueItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Reference.MOD_ID);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(StatueTags.UPGRADEABLE_STATUES).add(
				getKey(StatueRegistry.ANGRY_BEE_STATUE),
				getKey(StatueRegistry.BABY_ZOMBIE_STATUE), getKey(StatueRegistry.BEE_STATUE),
				getKey(StatueRegistry.TRANS_BEE_STATUE), getKey(StatueRegistry.BLAZE_STATUE),
				getKey(StatueRegistry.BROWN_MOOSHROOM_STATUE), getKey(StatueRegistry.CAMPFIRE_STATUE),
				getKey(StatueRegistry.CAT_BLACK_STATUE), getKey(StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE),
				getKey(StatueRegistry.CAT_CALICO_STATUE), getKey(StatueRegistry.CAT_JELLIE_STATUE),
				getKey(StatueRegistry.CAT_PERSIAN_STATUE), getKey(StatueRegistry.CAT_RAGDOLL_STATUE),
				getKey(StatueRegistry.CAT_RED_STATUE), getKey(StatueRegistry.CAT_SIAMESE_STATUE),
				getKey(StatueRegistry.CAT_TABBY_STATUE), getKey(StatueRegistry.CAT_TUXEDO_STATUE),
				getKey(StatueRegistry.CAT_WHITE_STATUE), getKey(StatueRegistry.CHICKEN_JOCKEY_STATUE),
				getKey(StatueRegistry.CHICKEN_STATUE), getKey(StatueRegistry.COD_STATUE),
				getKey(StatueRegistry.COW_STATUE), getKey(StatueRegistry.CREEPER_STATUE),
				getKey(StatueRegistry.DOLPHIN_STATUE), getKey(StatueRegistry.DROWNED_STATUE),
				getKey(StatueRegistry.ELDER_GUARDIAN_STATUE), getKey(StatueRegistry.ENDERMAN_STATUE),
				getKey(StatueRegistry.ENDERMITE_STATUE), getKey(StatueRegistry.EVOKER_STATUE),
				getKey(StatueRegistry.FLOOD_STATUE), getKey(StatueRegistry.FOX_SNOW_STATUE),
				getKey(StatueRegistry.FOX_STATUE), getKey(StatueRegistry.GHAST_STATUE),
				getKey(StatueRegistry.GUARDIAN_STATUE), getKey(StatueRegistry.HUSK_STATUE),
				getKey(StatueRegistry.KING_CLUCK_STATUE), getKey(StatueRegistry.MAGMA_STATUE),
				getKey(StatueRegistry.MOOSHROOM_STATUE), getKey(StatueRegistry.PANDA_ANGRY_STATUE),
				getKey(StatueRegistry.PANDA_BROWN_STATUE), getKey(StatueRegistry.PANDA_LAZY_STATUE),
				getKey(StatueRegistry.PANDA_NORMAL_STATUE), getKey(StatueRegistry.PANDA_PLAYFUL_STATUE),
				getKey(StatueRegistry.PANDA_WEAK_STATUE), getKey(StatueRegistry.PANDA_WORRIED_STATUE),
				getKey(StatueRegistry.PIG_STATUE), getKey(StatueRegistry.PILLAGER_STATUE),
				getKey(StatueRegistry.PUFFERFISH_MEDIUM_STATUE), getKey(StatueRegistry.PUFFERFISH_SMALL_STATUE),
				getKey(StatueRegistry.PUFFERFISH_STATUE), getKey(StatueRegistry.RABBIT_BR_STATUE),
				getKey(StatueRegistry.RABBIT_BS_STATUE), getKey(StatueRegistry.RABBIT_BW_STATUE),
				getKey(StatueRegistry.RABBIT_GO_STATUE), getKey(StatueRegistry.RABBIT_WH_STATUE),
				getKey(StatueRegistry.RABBIT_WS_STATUE), getKey(StatueRegistry.RAVAGER_STATUE),
				getKey(StatueRegistry.SALMON_STATUE), getKey(StatueRegistry.SHEEP_SHAVEN_STATUE),
				getKey(StatueRegistry.SHEEP_STATUE_BLACK), getKey(StatueRegistry.SHEEP_STATUE_BLUE),
				getKey(StatueRegistry.SHEEP_STATUE_BROWN), getKey(StatueRegistry.SHEEP_STATUE_CYAN),
				getKey(StatueRegistry.SHEEP_STATUE_GRAY), getKey(StatueRegistry.SHEEP_STATUE_GREEN),
				getKey(StatueRegistry.SHEEP_STATUE_LIGHT_BLUE), getKey(StatueRegistry.SHEEP_STATUE_LIGHT_GRAY),
				getKey(StatueRegistry.SHEEP_STATUE_LIME), getKey(StatueRegistry.SHEEP_STATUE_MAGENTA),
				getKey(StatueRegistry.SHEEP_STATUE_ORANGE), getKey(StatueRegistry.SHEEP_STATUE_PINK),
				getKey(StatueRegistry.SHEEP_STATUE_PURPLE), getKey(StatueRegistry.SHEEP_STATUE_RED),
				getKey(StatueRegistry.SHEEP_STATUE_WHITE), getKey(StatueRegistry.SHEEP_STATUE_YELLOW),
				getKey(StatueRegistry.SHULKER_STATUE), getKey(StatueRegistry.SLIME_STATUE),
				getKey(StatueRegistry.SNOW_GOLEM_STATUE), getKey(StatueRegistry.SPIDER_STATUE),
				getKey(StatueRegistry.SQUID_STATUE), getKey(StatueRegistry.TROPICAL_FISH_B),
				getKey(StatueRegistry.TROPICAL_FISH_BB), getKey(StatueRegistry.TROPICAL_FISH_BE),
				getKey(StatueRegistry.TROPICAL_FISH_BM), getKey(StatueRegistry.TROPICAL_FISH_BMB),
				getKey(StatueRegistry.TROPICAL_FISH_BMS), getKey(StatueRegistry.TROPICAL_FISH_E),
				getKey(StatueRegistry.TROPICAL_FISH_ES), getKey(StatueRegistry.TROPICAL_FISH_HB),
				getKey(StatueRegistry.TROPICAL_FISH_SB), getKey(StatueRegistry.TROPICAL_FISH_SD),
				getKey(StatueRegistry.TROPICAL_FISH_SS), getKey(StatueRegistry.TURTLE_STATUE),
				getKey(StatueRegistry.VILLAGER_BR_STATUE), getKey(StatueRegistry.VILLAGER_GR_STATUE),
				getKey(StatueRegistry.VILLAGER_PU_STATUE), getKey(StatueRegistry.VILLAGER_WH_STATUE),
				getKey(StatueRegistry.VINDICATOR_STATUE), getKey(StatueRegistry.WASTELAND_STATUE),
				getKey(StatueRegistry.WITCH_STATUE), getKey(StatueRegistry.ZOMBIE_STATUE),
				getKey(StatueRegistry.ALLAY_STATUE), getKey(StatueRegistry.AXOLOTL_LUCY_STATUE),
				getKey(StatueRegistry.AXOLOTL_WILD_STATUE), getKey(StatueRegistry.AXOLOTL_GOLD_STATUE),
				getKey(StatueRegistry.AXOLOTL_CYAN_STATUE), getKey(StatueRegistry.AXOLOTL_BLUE_STATUE),
				getKey(StatueRegistry.FROG_TEMPERATE_STATUE), getKey(StatueRegistry.FROG_WARM_STATUE),
				getKey(StatueRegistry.FROG_COLD_STATUE), getKey(StatueRegistry.WARDEN_STATUE),
				getKey(StatueRegistry.ENDERMITE_STATUE), getKey(StatueRegistry.TADPOLE_STATUE),
				getKey(StatueRegistry.ZOMBIFIED_PIGLIN_STATUE)
		);

		this.tag(StatueTags.LOOTABLE_STATUES).add(getKey(StatueRegistry.ANGRY_BEE_STATUE),
				getKey(StatueRegistry.BABY_ZOMBIE_STATUE), getKey(StatueRegistry.BEE_STATUE),
				getKey(StatueRegistry.TRANS_BEE_STATUE), getKey(StatueRegistry.BLAZE_STATUE),
				getKey(StatueRegistry.BROWN_MOOSHROOM_STATUE), getKey(StatueRegistry.CAMPFIRE_STATUE),
				getKey(StatueRegistry.CAT_BLACK_STATUE), getKey(StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE),
				getKey(StatueRegistry.CAT_CALICO_STATUE), getKey(StatueRegistry.CAT_JELLIE_STATUE),
				getKey(StatueRegistry.CAT_PERSIAN_STATUE), getKey(StatueRegistry.CAT_RAGDOLL_STATUE),
				getKey(StatueRegistry.CAT_RED_STATUE), getKey(StatueRegistry.CAT_SIAMESE_STATUE),
				getKey(StatueRegistry.CAT_TABBY_STATUE), getKey(StatueRegistry.CAT_TUXEDO_STATUE),
				getKey(StatueRegistry.CAT_WHITE_STATUE), getKey(StatueRegistry.CHICKEN_JOCKEY_STATUE),
				getKey(StatueRegistry.CHICKEN_STATUE), getKey(StatueRegistry.COD_STATUE),
				getKey(StatueRegistry.COW_STATUE), getKey(StatueRegistry.CREEPER_STATUE),
				getKey(StatueRegistry.DOLPHIN_STATUE), getKey(StatueRegistry.DROWNED_STATUE),
				getKey(StatueRegistry.ELDER_GUARDIAN_STATUE), getKey(StatueRegistry.ENDERMAN_STATUE),
				getKey(StatueRegistry.ENDERMITE_STATUE), getKey(StatueRegistry.EVOKER_STATUE),
				getKey(StatueRegistry.FLOOD_STATUE), getKey(StatueRegistry.FOX_SNOW_STATUE),
				getKey(StatueRegistry.FOX_STATUE), getKey(StatueRegistry.GHAST_STATUE),
				getKey(StatueRegistry.GUARDIAN_STATUE), getKey(StatueRegistry.HUSK_STATUE),
				getKey(StatueRegistry.KING_CLUCK_STATUE), getKey(StatueRegistry.MAGMA_STATUE),
				getKey(StatueRegistry.MOOSHROOM_STATUE), getKey(StatueRegistry.PANDA_ANGRY_STATUE),
				getKey(StatueRegistry.PANDA_BROWN_STATUE), getKey(StatueRegistry.PANDA_LAZY_STATUE),
				getKey(StatueRegistry.PANDA_NORMAL_STATUE), getKey(StatueRegistry.PANDA_PLAYFUL_STATUE),
				getKey(StatueRegistry.PANDA_WEAK_STATUE), getKey(StatueRegistry.PANDA_WORRIED_STATUE),
				getKey(StatueRegistry.PIG_STATUE), getKey(StatueRegistry.PILLAGER_STATUE),
				getKey(StatueRegistry.PUFFERFISH_MEDIUM_STATUE), getKey(StatueRegistry.PUFFERFISH_SMALL_STATUE),
				getKey(StatueRegistry.PUFFERFISH_STATUE), getKey(StatueRegistry.RABBIT_BR_STATUE),
				getKey(StatueRegistry.RABBIT_BS_STATUE), getKey(StatueRegistry.RABBIT_BW_STATUE),
				getKey(StatueRegistry.RABBIT_GO_STATUE), getKey(StatueRegistry.RABBIT_WH_STATUE),
				getKey(StatueRegistry.RABBIT_WS_STATUE), getKey(StatueRegistry.RAVAGER_STATUE),
				getKey(StatueRegistry.SALMON_STATUE), getKey(StatueRegistry.SHEEP_SHAVEN_STATUE),
				getKey(StatueRegistry.SHEEP_STATUE_BLACK), getKey(StatueRegistry.SHEEP_STATUE_BLUE),
				getKey(StatueRegistry.SHEEP_STATUE_BROWN), getKey(StatueRegistry.SHEEP_STATUE_CYAN),
				getKey(StatueRegistry.SHEEP_STATUE_GRAY), getKey(StatueRegistry.SHEEP_STATUE_GREEN),
				getKey(StatueRegistry.SHEEP_STATUE_LIGHT_BLUE), getKey(StatueRegistry.SHEEP_STATUE_LIGHT_GRAY),
				getKey(StatueRegistry.SHEEP_STATUE_LIME), getKey(StatueRegistry.SHEEP_STATUE_MAGENTA),
				getKey(StatueRegistry.SHEEP_STATUE_ORANGE), getKey(StatueRegistry.SHEEP_STATUE_PINK),
				getKey(StatueRegistry.SHEEP_STATUE_PURPLE), getKey(StatueRegistry.SHEEP_STATUE_RED),
				getKey(StatueRegistry.SHEEP_STATUE_WHITE), getKey(StatueRegistry.SHEEP_STATUE_YELLOW),
				getKey(StatueRegistry.SHULKER_STATUE), getKey(StatueRegistry.SLIME_STATUE),
				getKey(StatueRegistry.SNOW_GOLEM_STATUE), getKey(StatueRegistry.SPIDER_STATUE),
				getKey(StatueRegistry.SQUID_STATUE), getKey(StatueRegistry.TROPICAL_FISH_B),
				getKey(StatueRegistry.TROPICAL_FISH_BB), getKey(StatueRegistry.TROPICAL_FISH_BE),
				getKey(StatueRegistry.TROPICAL_FISH_BM), getKey(StatueRegistry.TROPICAL_FISH_BMB),
				getKey(StatueRegistry.TROPICAL_FISH_BMS), getKey(StatueRegistry.TROPICAL_FISH_E),
				getKey(StatueRegistry.TROPICAL_FISH_ES), getKey(StatueRegistry.TROPICAL_FISH_HB),
				getKey(StatueRegistry.TROPICAL_FISH_SB), getKey(StatueRegistry.TROPICAL_FISH_SD),
				getKey(StatueRegistry.TROPICAL_FISH_SS), getKey(StatueRegistry.TURTLE_STATUE),
				getKey(StatueRegistry.VILLAGER_BR_STATUE), getKey(StatueRegistry.VILLAGER_GR_STATUE),
				getKey(StatueRegistry.VILLAGER_PU_STATUE), getKey(StatueRegistry.VILLAGER_WH_STATUE),
				getKey(StatueRegistry.VINDICATOR_STATUE), getKey(StatueRegistry.WASTELAND_STATUE),
				getKey(StatueRegistry.WITCH_STATUE), getKey(StatueRegistry.ZOMBIE_STATUE),
				getKey(StatueRegistry.ALLAY_STATUE), getKey(StatueRegistry.AXOLOTL_LUCY_STATUE),
				getKey(StatueRegistry.AXOLOTL_WILD_STATUE), getKey(StatueRegistry.AXOLOTL_GOLD_STATUE),
				getKey(StatueRegistry.AXOLOTL_CYAN_STATUE), getKey(StatueRegistry.AXOLOTL_BLUE_STATUE),
				getKey(StatueRegistry.FROG_TEMPERATE_STATUE), getKey(StatueRegistry.FROG_WARM_STATUE),
				getKey(StatueRegistry.FROG_COLD_STATUE), getKey(StatueRegistry.WARDEN_STATUE),
				getKey(StatueRegistry.ZOMBIFIED_PIGLIN_STATUE));

		this.tag(StatueTags.STATUE_INTERACTABLE).add(getKey(StatueRegistry.FLOOD_STATUE), getKey(StatueRegistry.MOOSHROOM_STATUE),
				getKey(StatueRegistry.BROWN_MOOSHROOM_STATUE), getKey(StatueRegistry.COW_STATUE), getKey(StatueRegistry.SPIDER_STATUE),
				getKey(StatueRegistry.SHULKER_STATUE));

		// Used to use copy() but don't know how to do that now...
		this.tag(StatueTags.STATUES_ITEMS).add(getKey(StatueRegistry.ANGRY_BEE_STATUE), getKey(StatueRegistry.BABY_ZOMBIE_STATUE), getKey(StatueRegistry.BEE_STATUE), getKey(StatueRegistry.TRANS_BEE_STATUE), getKey(StatueRegistry.BLAZE_STATUE),
				getKey(StatueRegistry.BROWN_MOOSHROOM_STATUE), getKey(StatueRegistry.CAMPFIRE_STATUE), getKey(StatueRegistry.CAT_BLACK_STATUE), getKey(StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE), getKey(StatueRegistry.CAT_CALICO_STATUE),
				getKey(StatueRegistry.CAT_JELLIE_STATUE), getKey(StatueRegistry.CAT_PERSIAN_STATUE), getKey(StatueRegistry.CAT_RAGDOLL_STATUE), getKey(StatueRegistry.CAT_RED_STATUE), getKey(StatueRegistry.CAT_SIAMESE_STATUE), getKey(StatueRegistry.CAT_TABBY_STATUE),
				getKey(StatueRegistry.CAT_TUXEDO_STATUE), getKey(StatueRegistry.CAT_WHITE_STATUE), getKey(StatueRegistry.CHICKEN_JOCKEY_STATUE), getKey(StatueRegistry.CHICKEN_STATUE), getKey(StatueRegistry.COD_STATUE), getKey(StatueRegistry.COW_STATUE),
				getKey(StatueRegistry.CREEPER_STATUE), getKey(StatueRegistry.DETECTIVE_PLATYPUS), getKey(StatueRegistry.DOLPHIN_STATUE), getKey(StatueRegistry.DROWNED_STATUE), getKey(StatueRegistry.ELDER_GUARDIAN_STATUE), getKey(StatueRegistry.ENDERMAN_STATUE),
				getKey(StatueRegistry.ENDERMITE_STATUE), getKey(StatueRegistry.EVOKER_STATUE), getKey(StatueRegistry.FLOOD_STATUE), getKey(StatueRegistry.FOX_SNOW_STATUE), getKey(StatueRegistry.FOX_STATUE), getKey(StatueRegistry.GHAST_STATUE), getKey(StatueRegistry.GUARDIAN_STATUE),
				getKey(StatueRegistry.HUSK_STATUE), getKey(StatueRegistry.INFO_STATUE), getKey(StatueRegistry.KING_CLUCK_STATUE), getKey(StatueRegistry.MAGMA_STATUE), getKey(StatueRegistry.MOOSHROOM_STATUE), getKey(StatueRegistry.PANDA_ANGRY_STATUE),
				getKey(StatueRegistry.PANDA_BROWN_STATUE), getKey(StatueRegistry.PANDA_LAZY_STATUE), getKey(StatueRegistry.PANDA_NORMAL_STATUE), getKey(StatueRegistry.PANDA_PLAYFUL_STATUE), getKey(StatueRegistry.PANDA_WEAK_STATUE),
				getKey(StatueRegistry.PANDA_WORRIED_STATUE), getKey(StatueRegistry.PIG_STATUE), getKey(StatueRegistry.PILLAGER_STATUE), getKey(StatueRegistry.PLAYER_STATUE), getKey(StatueRegistry.PUFFERFISH_MEDIUM_STATUE),
				getKey(StatueRegistry.PUFFERFISH_SMALL_STATUE), getKey(StatueRegistry.PUFFERFISH_STATUE), getKey(StatueRegistry.RABBIT_BR_STATUE), getKey(StatueRegistry.RABBIT_BS_STATUE), getKey(StatueRegistry.RABBIT_BW_STATUE),
				getKey(StatueRegistry.RABBIT_GO_STATUE), getKey(StatueRegistry.RABBIT_WH_STATUE), getKey(StatueRegistry.RABBIT_WS_STATUE), getKey(StatueRegistry.RAVAGER_STATUE), getKey(StatueRegistry.SALMON_STATUE), getKey(StatueRegistry.SHEEP_SHAVEN_STATUE),
				getKey(StatueRegistry.SHEEP_STATUE_BLACK), getKey(StatueRegistry.SHEEP_STATUE_BLUE), getKey(StatueRegistry.SHEEP_STATUE_BROWN), getKey(StatueRegistry.SHEEP_STATUE_CYAN), getKey(StatueRegistry.SHEEP_STATUE_GRAY), getKey(StatueRegistry.SHEEP_STATUE_GREEN),
				getKey(StatueRegistry.SHEEP_STATUE_LIGHT_BLUE), getKey(StatueRegistry.SHEEP_STATUE_LIGHT_GRAY), getKey(StatueRegistry.SHEEP_STATUE_LIME), getKey(StatueRegistry.SHEEP_STATUE_MAGENTA), getKey(StatueRegistry.SHEEP_STATUE_ORANGE),
				getKey(StatueRegistry.SHEEP_STATUE_PINK), getKey(StatueRegistry.SHEEP_STATUE_PURPLE), getKey(StatueRegistry.SHEEP_STATUE_RED), getKey(StatueRegistry.SHEEP_STATUE_WHITE), getKey(StatueRegistry.SHEEP_STATUE_YELLOW), getKey(StatueRegistry.SHULKER_STATUE),
				getKey(StatueRegistry.SLIME_STATUE), getKey(StatueRegistry.SNOW_GOLEM_STATUE), getKey(StatueRegistry.SPIDER_STATUE), getKey(StatueRegistry.SQUID_STATUE), getKey(StatueRegistry.TOTEM_OF_UNDYING_STATUE), getKey(StatueRegistry.TROPICAL_FISH_B),
				getKey(StatueRegistry.TROPICAL_FISH_BB), getKey(StatueRegistry.TROPICAL_FISH_BE), getKey(StatueRegistry.TROPICAL_FISH_BM), getKey(StatueRegistry.TROPICAL_FISH_BMB), getKey(StatueRegistry.TROPICAL_FISH_BMS), getKey(StatueRegistry.TROPICAL_FISH_E),
				getKey(StatueRegistry.TROPICAL_FISH_ES), getKey(StatueRegistry.TROPICAL_FISH_HB), getKey(StatueRegistry.TROPICAL_FISH_SB), getKey(StatueRegistry.TROPICAL_FISH_SD), getKey(StatueRegistry.TROPICAL_FISH_SS), getKey(StatueRegistry.TURTLE_STATUE),
				getKey(StatueRegistry.VILLAGER_BR_STATUE), getKey(StatueRegistry.VILLAGER_GR_STATUE), getKey(StatueRegistry.VILLAGER_PU_STATUE), getKey(StatueRegistry.VILLAGER_WH_STATUE), getKey(StatueRegistry.VINDICATOR_STATUE), getKey(StatueRegistry.WASTELAND_STATUE),
				getKey(StatueRegistry.WITCH_STATUE), getKey(StatueRegistry.ZOMBIE_STATUE), getKey(StatueRegistry.BUMBO_STATUE), getKey(StatueRegistry.TROPIBEE), getKey(StatueRegistry.EAGLE_RAY), getKey(StatueRegistry.SLABFISH), getKey(StatueRegistry.AZZARO),
				getKey(StatueRegistry.ALLAY_STATUE), getKey(StatueRegistry.AXOLOTL_LUCY_STATUE), getKey(StatueRegistry.AXOLOTL_WILD_STATUE), getKey(StatueRegistry.AXOLOTL_GOLD_STATUE), getKey(StatueRegistry.AXOLOTL_CYAN_STATUE), getKey(StatueRegistry.AXOLOTL_BLUE_STATUE),
				getKey(StatueRegistry.FROG_TEMPERATE_STATUE), getKey(StatueRegistry.FROG_WARM_STATUE), getKey(StatueRegistry.FROG_COLD_STATUE), getKey(StatueRegistry.TADPOLE_STATUE), getKey(StatueRegistry.WARDEN_STATUE));


		this.tag(StatueTags.CURIOS_STATUE).addTag(StatueTags.STATUES_ITEMS).add(getKey(StatueRegistry.DISPLAY_STAND), getKey(StatueRegistry.SOMBRERO));

		this.tag(StatueTags.STATUE_CORE).add(StatueRegistry.STATUE_CORE.getKey());
		this.tag(StatueTags.PLAYER_UPGRADE_ITEM).addTag(StatueTags.STATUE_CORE);
		this.tag(ItemTags.DECORATED_POT_SHERDS).add(StatueRegistry.STATUE_CORE_POTTERY_SHERD.getKey());
	}

	private ResourceKey<Item> getKey(DeferredBlock<?> block) {
		return block.asItem().builtInRegistryHolder().key();
	}
}
