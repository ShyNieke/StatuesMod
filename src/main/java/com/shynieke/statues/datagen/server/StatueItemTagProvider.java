package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class StatueItemTagProvider extends ItemTagsProvider {

	public StatueItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Reference.MOD_ID);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(StatueTags.UPGRADEABLE_STATUES).add(StatueRegistry.ANGRY_BEE_STATUE.get().asItem(),
				StatueRegistry.BABY_ZOMBIE_STATUE.get().asItem(), StatueRegistry.BEE_STATUE.get().asItem(),
				StatueRegistry.TRANS_BEE_STATUE.get().asItem(), StatueRegistry.BLAZE_STATUE.get().asItem(),
				StatueRegistry.BROWN_MOOSHROOM_STATUE.get().asItem(), StatueRegistry.CAMPFIRE_STATUE.get().asItem(),
				StatueRegistry.CAT_BLACK_STATUE.get().asItem(), StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get().asItem(),
				StatueRegistry.CAT_CALICO_STATUE.get().asItem(), StatueRegistry.CAT_JELLIE_STATUE.get().asItem(),
				StatueRegistry.CAT_PERSIAN_STATUE.get().asItem(), StatueRegistry.CAT_RAGDOLL_STATUE.get().asItem(),
				StatueRegistry.CAT_RED_STATUE.get().asItem(), StatueRegistry.CAT_SIAMESE_STATUE.get().asItem(),
				StatueRegistry.CAT_TABBY_STATUE.get().asItem(), StatueRegistry.CAT_TUXEDO_STATUE.get().asItem(),
				StatueRegistry.CAT_WHITE_STATUE.get().asItem(), StatueRegistry.CHICKEN_JOCKEY_STATUE.get().asItem(),
				StatueRegistry.CHICKEN_STATUE.get().asItem(), StatueRegistry.COD_STATUE.get().asItem(),
				StatueRegistry.COW_STATUE.get().asItem(), StatueRegistry.CREEPER_STATUE.get().asItem(),
				StatueRegistry.DOLPHIN_STATUE.get().asItem(), StatueRegistry.DROWNED_STATUE.get().asItem(),
				StatueRegistry.ELDER_GUARDIAN_STATUE.get().asItem(), StatueRegistry.ENDERMAN_STATUE.get().asItem(),
				StatueRegistry.ENDERMITE_STATUE.get().asItem(), StatueRegistry.EVOKER_STATUE.get().asItem(),
				StatueRegistry.FLOOD_STATUE.get().asItem(), StatueRegistry.FOX_SNOW_STATUE.get().asItem(),
				StatueRegistry.FOX_STATUE.get().asItem(), StatueRegistry.GHAST_STATUE.get().asItem(),
				StatueRegistry.GUARDIAN_STATUE.get().asItem(), StatueRegistry.HUSK_STATUE.get().asItem(),
				StatueRegistry.KING_CLUCK_STATUE.get().asItem(), StatueRegistry.MAGMA_STATUE.get().asItem(),
				StatueRegistry.MOOSHROOM_STATUE.get().asItem(), StatueRegistry.PANDA_ANGRY_STATUE.get().asItem(),
				StatueRegistry.PANDA_BROWN_STATUE.get().asItem(), StatueRegistry.PANDA_LAZY_STATUE.get().asItem(),
				StatueRegistry.PANDA_NORMAL_STATUE.get().asItem(), StatueRegistry.PANDA_PLAYFUL_STATUE.get().asItem(),
				StatueRegistry.PANDA_WEAK_STATUE.get().asItem(), StatueRegistry.PANDA_WORRIED_STATUE.get().asItem(),
				StatueRegistry.PIG_STATUE.get().asItem(), StatueRegistry.PILLAGER_STATUE.get().asItem(),
				StatueRegistry.PUFFERFISH_MEDIUM_STATUE.get().asItem(), StatueRegistry.PUFFERFISH_SMALL_STATUE.get().asItem(),
				StatueRegistry.PUFFERFISH_STATUE.get().asItem(), StatueRegistry.RABBIT_BR_STATUE.get().asItem(),
				StatueRegistry.RABBIT_BS_STATUE.get().asItem(), StatueRegistry.RABBIT_BW_STATUE.get().asItem(),
				StatueRegistry.RABBIT_GO_STATUE.get().asItem(), StatueRegistry.RABBIT_WH_STATUE.get().asItem(),
				StatueRegistry.RABBIT_WS_STATUE.get().asItem(), StatueRegistry.RAVAGER_STATUE.get().asItem(),
				StatueRegistry.SALMON_STATUE.get().asItem(), StatueRegistry.SHEEP_SHAVEN_STATUE.get().asItem(),
				StatueRegistry.SHEEP_STATUE_BLACK.get().asItem(), StatueRegistry.SHEEP_STATUE_BLUE.get().asItem(),
				StatueRegistry.SHEEP_STATUE_BROWN.get().asItem(), StatueRegistry.SHEEP_STATUE_CYAN.get().asItem(),
				StatueRegistry.SHEEP_STATUE_GRAY.get().asItem(), StatueRegistry.SHEEP_STATUE_GREEN.get().asItem(),
				StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.get().asItem(), StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.get().asItem(),
				StatueRegistry.SHEEP_STATUE_LIME.get().asItem(), StatueRegistry.SHEEP_STATUE_MAGENTA.get().asItem(),
				StatueRegistry.SHEEP_STATUE_ORANGE.get().asItem(), StatueRegistry.SHEEP_STATUE_PINK.get().asItem(),
				StatueRegistry.SHEEP_STATUE_PURPLE.get().asItem(), StatueRegistry.SHEEP_STATUE_RED.get().asItem(),
				StatueRegistry.SHEEP_STATUE_WHITE.get().asItem(), StatueRegistry.SHEEP_STATUE_YELLOW.get().asItem(),
				StatueRegistry.SHULKER_STATUE.get().asItem(), StatueRegistry.SLIME_STATUE.get().asItem(),
				StatueRegistry.SNOW_GOLEM_STATUE.get().asItem(), StatueRegistry.SPIDER_STATUE.get().asItem(),
				StatueRegistry.SQUID_STATUE.get().asItem(), StatueRegistry.TROPICAL_FISH_B.get().asItem(),
				StatueRegistry.TROPICAL_FISH_BB.get().asItem(), StatueRegistry.TROPICAL_FISH_BE.get().asItem(),
				StatueRegistry.TROPICAL_FISH_BM.get().asItem(), StatueRegistry.TROPICAL_FISH_BMB.get().asItem(),
				StatueRegistry.TROPICAL_FISH_BMS.get().asItem(), StatueRegistry.TROPICAL_FISH_E.get().asItem(),
				StatueRegistry.TROPICAL_FISH_ES.get().asItem(), StatueRegistry.TROPICAL_FISH_HB.get().asItem(),
				StatueRegistry.TROPICAL_FISH_SB.get().asItem(), StatueRegistry.TROPICAL_FISH_SD.get().asItem(),
				StatueRegistry.TROPICAL_FISH_SS.get().asItem(), StatueRegistry.TURTLE_STATUE.get().asItem(),
				StatueRegistry.VILLAGER_BR_STATUE.get().asItem(), StatueRegistry.VILLAGER_GR_STATUE.get().asItem(),
				StatueRegistry.VILLAGER_PU_STATUE.get().asItem(), StatueRegistry.VILLAGER_WH_STATUE.get().asItem(),
				StatueRegistry.VINDICATOR_STATUE.get().asItem(), StatueRegistry.WASTELAND_STATUE.get().asItem(),
				StatueRegistry.WITCH_STATUE.get().asItem(), StatueRegistry.ZOMBIE_STATUE.get().asItem(),
				StatueRegistry.ALLAY_STATUE.get().asItem(), StatueRegistry.AXOLOTL_LUCY_STATUE.get().asItem(),
				StatueRegistry.AXOLOTL_WILD_STATUE.get().asItem(), StatueRegistry.AXOLOTL_GOLD_STATUE.get().asItem(),
				StatueRegistry.AXOLOTL_CYAN_STATUE.get().asItem(), StatueRegistry.AXOLOTL_BLUE_STATUE.get().asItem(),
				StatueRegistry.FROG_TEMPERATE_STATUE.get().asItem(), StatueRegistry.FROG_WARM_STATUE.get().asItem(),
				StatueRegistry.FROG_COLD_STATUE.get().asItem(), StatueRegistry.WARDEN_STATUE.get().asItem(),
				StatueRegistry.ENDERMITE_STATUE.get().asItem(), StatueRegistry.TADPOLE_STATUE.get().asItem());

		this.tag(StatueTags.LOOTABLE_STATUES).add(StatueRegistry.ANGRY_BEE_STATUE.get().asItem(),
				StatueRegistry.BABY_ZOMBIE_STATUE.get().asItem(), StatueRegistry.BEE_STATUE.get().asItem(),
				StatueRegistry.TRANS_BEE_STATUE.get().asItem(), StatueRegistry.BLAZE_STATUE.get().asItem(),
				StatueRegistry.BROWN_MOOSHROOM_STATUE.get().asItem(), StatueRegistry.CAMPFIRE_STATUE.get().asItem(),
				StatueRegistry.CAT_BLACK_STATUE.get().asItem(), StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get().asItem(),
				StatueRegistry.CAT_CALICO_STATUE.get().asItem(), StatueRegistry.CAT_JELLIE_STATUE.get().asItem(),
				StatueRegistry.CAT_PERSIAN_STATUE.get().asItem(), StatueRegistry.CAT_RAGDOLL_STATUE.get().asItem(),
				StatueRegistry.CAT_RED_STATUE.get().asItem(), StatueRegistry.CAT_SIAMESE_STATUE.get().asItem(),
				StatueRegistry.CAT_TABBY_STATUE.get().asItem(), StatueRegistry.CAT_TUXEDO_STATUE.get().asItem(),
				StatueRegistry.CAT_WHITE_STATUE.get().asItem(), StatueRegistry.CHICKEN_JOCKEY_STATUE.get().asItem(),
				StatueRegistry.CHICKEN_STATUE.get().asItem(), StatueRegistry.COD_STATUE.get().asItem(),
				StatueRegistry.COW_STATUE.get().asItem(), StatueRegistry.CREEPER_STATUE.get().asItem(),
				StatueRegistry.DOLPHIN_STATUE.get().asItem(), StatueRegistry.DROWNED_STATUE.get().asItem(),
				StatueRegistry.ELDER_GUARDIAN_STATUE.get().asItem(), StatueRegistry.ENDERMAN_STATUE.get().asItem(),
				StatueRegistry.ENDERMITE_STATUE.get().asItem(), StatueRegistry.EVOKER_STATUE.get().asItem(),
				StatueRegistry.FLOOD_STATUE.get().asItem(), StatueRegistry.FOX_SNOW_STATUE.get().asItem(),
				StatueRegistry.FOX_STATUE.get().asItem(), StatueRegistry.GHAST_STATUE.get().asItem(),
				StatueRegistry.GUARDIAN_STATUE.get().asItem(), StatueRegistry.HUSK_STATUE.get().asItem(),
				StatueRegistry.KING_CLUCK_STATUE.get().asItem(), StatueRegistry.MAGMA_STATUE.get().asItem(),
				StatueRegistry.MOOSHROOM_STATUE.get().asItem(), StatueRegistry.PANDA_ANGRY_STATUE.get().asItem(),
				StatueRegistry.PANDA_BROWN_STATUE.get().asItem(), StatueRegistry.PANDA_LAZY_STATUE.get().asItem(),
				StatueRegistry.PANDA_NORMAL_STATUE.get().asItem(), StatueRegistry.PANDA_PLAYFUL_STATUE.get().asItem(),
				StatueRegistry.PANDA_WEAK_STATUE.get().asItem(), StatueRegistry.PANDA_WORRIED_STATUE.get().asItem(),
				StatueRegistry.PIG_STATUE.get().asItem(), StatueRegistry.PILLAGER_STATUE.get().asItem(),
				StatueRegistry.PUFFERFISH_MEDIUM_STATUE.get().asItem(), StatueRegistry.PUFFERFISH_SMALL_STATUE.get().asItem(),
				StatueRegistry.PUFFERFISH_STATUE.get().asItem(), StatueRegistry.RABBIT_BR_STATUE.get().asItem(),
				StatueRegistry.RABBIT_BS_STATUE.get().asItem(), StatueRegistry.RABBIT_BW_STATUE.get().asItem(),
				StatueRegistry.RABBIT_GO_STATUE.get().asItem(), StatueRegistry.RABBIT_WH_STATUE.get().asItem(),
				StatueRegistry.RABBIT_WS_STATUE.get().asItem(), StatueRegistry.RAVAGER_STATUE.get().asItem(),
				StatueRegistry.SALMON_STATUE.get().asItem(), StatueRegistry.SHEEP_SHAVEN_STATUE.get().asItem(),
				StatueRegistry.SHEEP_STATUE_BLACK.get().asItem(), StatueRegistry.SHEEP_STATUE_BLUE.get().asItem(),
				StatueRegistry.SHEEP_STATUE_BROWN.get().asItem(), StatueRegistry.SHEEP_STATUE_CYAN.get().asItem(),
				StatueRegistry.SHEEP_STATUE_GRAY.get().asItem(), StatueRegistry.SHEEP_STATUE_GREEN.get().asItem(),
				StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.get().asItem(), StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.get().asItem(),
				StatueRegistry.SHEEP_STATUE_LIME.get().asItem(), StatueRegistry.SHEEP_STATUE_MAGENTA.get().asItem(),
				StatueRegistry.SHEEP_STATUE_ORANGE.get().asItem(), StatueRegistry.SHEEP_STATUE_PINK.get().asItem(),
				StatueRegistry.SHEEP_STATUE_PURPLE.get().asItem(), StatueRegistry.SHEEP_STATUE_RED.get().asItem(),
				StatueRegistry.SHEEP_STATUE_WHITE.get().asItem(), StatueRegistry.SHEEP_STATUE_YELLOW.get().asItem(),
				StatueRegistry.SHULKER_STATUE.get().asItem(), StatueRegistry.SLIME_STATUE.get().asItem(),
				StatueRegistry.SNOW_GOLEM_STATUE.get().asItem(), StatueRegistry.SPIDER_STATUE.get().asItem(),
				StatueRegistry.SQUID_STATUE.get().asItem(), StatueRegistry.TROPICAL_FISH_B.get().asItem(),
				StatueRegistry.TROPICAL_FISH_BB.get().asItem(), StatueRegistry.TROPICAL_FISH_BE.get().asItem(),
				StatueRegistry.TROPICAL_FISH_BM.get().asItem(), StatueRegistry.TROPICAL_FISH_BMB.get().asItem(),
				StatueRegistry.TROPICAL_FISH_BMS.get().asItem(), StatueRegistry.TROPICAL_FISH_E.get().asItem(),
				StatueRegistry.TROPICAL_FISH_ES.get().asItem(), StatueRegistry.TROPICAL_FISH_HB.get().asItem(),
				StatueRegistry.TROPICAL_FISH_SB.get().asItem(), StatueRegistry.TROPICAL_FISH_SD.get().asItem(),
				StatueRegistry.TROPICAL_FISH_SS.get().asItem(), StatueRegistry.TURTLE_STATUE.get().asItem(),
				StatueRegistry.VILLAGER_BR_STATUE.get().asItem(), StatueRegistry.VILLAGER_GR_STATUE.get().asItem(),
				StatueRegistry.VILLAGER_PU_STATUE.get().asItem(), StatueRegistry.VILLAGER_WH_STATUE.get().asItem(),
				StatueRegistry.VINDICATOR_STATUE.get().asItem(), StatueRegistry.WASTELAND_STATUE.get().asItem(),
				StatueRegistry.WITCH_STATUE.get().asItem(), StatueRegistry.ZOMBIE_STATUE.get().asItem(),
				StatueRegistry.ALLAY_STATUE.get().asItem(), StatueRegistry.AXOLOTL_LUCY_STATUE.get().asItem(),
				StatueRegistry.AXOLOTL_WILD_STATUE.get().asItem(), StatueRegistry.AXOLOTL_GOLD_STATUE.get().asItem(),
				StatueRegistry.AXOLOTL_CYAN_STATUE.get().asItem(), StatueRegistry.AXOLOTL_BLUE_STATUE.get().asItem(),
				StatueRegistry.FROG_TEMPERATE_STATUE.get().asItem(), StatueRegistry.FROG_WARM_STATUE.get().asItem(),
				StatueRegistry.FROG_COLD_STATUE.get().asItem(), StatueRegistry.WARDEN_STATUE.get().asItem());

		this.tag(StatueTags.STATUE_INTERACTABLE).add(StatueRegistry.FLOOD_STATUE.get().asItem(), StatueRegistry.MOOSHROOM_STATUE.get().asItem(),
				StatueRegistry.BROWN_MOOSHROOM_STATUE.get().asItem(), StatueRegistry.COW_STATUE.get().asItem(), StatueRegistry.SPIDER_STATUE.get().asItem(),
				StatueRegistry.SHULKER_STATUE.get().asItem());

		// Used to use copy() but don't know how to do that now...
		this.tag(StatueTags.STATUES_ITEMS).add(StatueRegistry.ANGRY_BEE_STATUE.asItem(), StatueRegistry.BABY_ZOMBIE_STATUE.asItem(), StatueRegistry.BEE_STATUE.asItem(), StatueRegistry.TRANS_BEE_STATUE.asItem(), StatueRegistry.BLAZE_STATUE.asItem(),
				StatueRegistry.BROWN_MOOSHROOM_STATUE.asItem(), StatueRegistry.CAMPFIRE_STATUE.asItem(), StatueRegistry.CAT_BLACK_STATUE.asItem(), StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.asItem(), StatueRegistry.CAT_CALICO_STATUE.asItem(),
				StatueRegistry.CAT_JELLIE_STATUE.asItem(), StatueRegistry.CAT_PERSIAN_STATUE.asItem(), StatueRegistry.CAT_RAGDOLL_STATUE.asItem(), StatueRegistry.CAT_RED_STATUE.asItem(), StatueRegistry.CAT_SIAMESE_STATUE.asItem(), StatueRegistry.CAT_TABBY_STATUE.asItem(),
				StatueRegistry.CAT_TUXEDO_STATUE.asItem(), StatueRegistry.CAT_WHITE_STATUE.asItem(), StatueRegistry.CHICKEN_JOCKEY_STATUE.asItem(), StatueRegistry.CHICKEN_STATUE.asItem(), StatueRegistry.COD_STATUE.asItem(), StatueRegistry.COW_STATUE.asItem(),
				StatueRegistry.CREEPER_STATUE.asItem(), StatueRegistry.DETECTIVE_PLATYPUS.asItem(), StatueRegistry.DOLPHIN_STATUE.asItem(), StatueRegistry.DROWNED_STATUE.asItem(), StatueRegistry.ELDER_GUARDIAN_STATUE.asItem(), StatueRegistry.ENDERMAN_STATUE.asItem(),
				StatueRegistry.ENDERMITE_STATUE.asItem(), StatueRegistry.EVOKER_STATUE.asItem(), StatueRegistry.FLOOD_STATUE.asItem(), StatueRegistry.FOX_SNOW_STATUE.asItem(), StatueRegistry.FOX_STATUE.asItem(), StatueRegistry.GHAST_STATUE.asItem(), StatueRegistry.GUARDIAN_STATUE.asItem(),
				StatueRegistry.HUSK_STATUE.asItem(), StatueRegistry.INFO_STATUE.asItem(), StatueRegistry.KING_CLUCK_STATUE.asItem(), StatueRegistry.MAGMA_STATUE.asItem(), StatueRegistry.MOOSHROOM_STATUE.asItem(), StatueRegistry.PANDA_ANGRY_STATUE.asItem(),
				StatueRegistry.PANDA_BROWN_STATUE.asItem(), StatueRegistry.PANDA_LAZY_STATUE.asItem(), StatueRegistry.PANDA_NORMAL_STATUE.asItem(), StatueRegistry.PANDA_PLAYFUL_STATUE.asItem(), StatueRegistry.PANDA_WEAK_STATUE.asItem(),
				StatueRegistry.PANDA_WORRIED_STATUE.asItem(), StatueRegistry.PIG_STATUE.asItem(), StatueRegistry.PILLAGER_STATUE.asItem(), StatueRegistry.PLAYER_STATUE.asItem(), StatueRegistry.PUFFERFISH_MEDIUM_STATUE.asItem(),
				StatueRegistry.PUFFERFISH_SMALL_STATUE.asItem(), StatueRegistry.PUFFERFISH_STATUE.asItem(), StatueRegistry.RABBIT_BR_STATUE.asItem(), StatueRegistry.RABBIT_BS_STATUE.asItem(), StatueRegistry.RABBIT_BW_STATUE.asItem(),
				StatueRegistry.RABBIT_GO_STATUE.asItem(), StatueRegistry.RABBIT_WH_STATUE.asItem(), StatueRegistry.RABBIT_WS_STATUE.asItem(), StatueRegistry.RAVAGER_STATUE.asItem(), StatueRegistry.SALMON_STATUE.asItem(), StatueRegistry.SHEEP_SHAVEN_STATUE.asItem(),
				StatueRegistry.SHEEP_STATUE_BLACK.asItem(), StatueRegistry.SHEEP_STATUE_BLUE.asItem(), StatueRegistry.SHEEP_STATUE_BROWN.asItem(), StatueRegistry.SHEEP_STATUE_CYAN.asItem(), StatueRegistry.SHEEP_STATUE_GRAY.asItem(), StatueRegistry.SHEEP_STATUE_GREEN.asItem(),
				StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.asItem(), StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.asItem(), StatueRegistry.SHEEP_STATUE_LIME.asItem(), StatueRegistry.SHEEP_STATUE_MAGENTA.asItem(), StatueRegistry.SHEEP_STATUE_ORANGE.asItem(),
				StatueRegistry.SHEEP_STATUE_PINK.asItem(), StatueRegistry.SHEEP_STATUE_PURPLE.asItem(), StatueRegistry.SHEEP_STATUE_RED.asItem(), StatueRegistry.SHEEP_STATUE_WHITE.asItem(), StatueRegistry.SHEEP_STATUE_YELLOW.asItem(), StatueRegistry.SHULKER_STATUE.asItem(),
				StatueRegistry.SLIME_STATUE.asItem(), StatueRegistry.SNOW_GOLEM_STATUE.asItem(), StatueRegistry.SPIDER_STATUE.asItem(), StatueRegistry.SQUID_STATUE.asItem(), StatueRegistry.TOTEM_OF_UNDYING_STATUE.asItem(), StatueRegistry.TROPICAL_FISH_B.asItem(),
				StatueRegistry.TROPICAL_FISH_BB.asItem(), StatueRegistry.TROPICAL_FISH_BE.asItem(), StatueRegistry.TROPICAL_FISH_BM.asItem(), StatueRegistry.TROPICAL_FISH_BMB.asItem(), StatueRegistry.TROPICAL_FISH_BMS.asItem(), StatueRegistry.TROPICAL_FISH_E.asItem(),
				StatueRegistry.TROPICAL_FISH_ES.asItem(), StatueRegistry.TROPICAL_FISH_HB.asItem(), StatueRegistry.TROPICAL_FISH_SB.asItem(), StatueRegistry.TROPICAL_FISH_SD.asItem(), StatueRegistry.TROPICAL_FISH_SS.asItem(), StatueRegistry.TURTLE_STATUE.asItem(),
				StatueRegistry.VILLAGER_BR_STATUE.asItem(), StatueRegistry.VILLAGER_GR_STATUE.asItem(), StatueRegistry.VILLAGER_PU_STATUE.asItem(), StatueRegistry.VILLAGER_WH_STATUE.asItem(), StatueRegistry.VINDICATOR_STATUE.asItem(), StatueRegistry.WASTELAND_STATUE.asItem(),
				StatueRegistry.WITCH_STATUE.asItem(), StatueRegistry.ZOMBIE_STATUE.asItem(), StatueRegistry.BUMBO_STATUE.asItem(), StatueRegistry.TROPIBEE.asItem(), StatueRegistry.EAGLE_RAY.asItem(), StatueRegistry.SLABFISH.asItem(), StatueRegistry.AZZARO.asItem(),
				StatueRegistry.ALLAY_STATUE.asItem(), StatueRegistry.AXOLOTL_LUCY_STATUE.asItem(), StatueRegistry.AXOLOTL_WILD_STATUE.asItem(), StatueRegistry.AXOLOTL_GOLD_STATUE.asItem(), StatueRegistry.AXOLOTL_CYAN_STATUE.asItem(), StatueRegistry.AXOLOTL_BLUE_STATUE.asItem(),
				StatueRegistry.FROG_TEMPERATE_STATUE.asItem(), StatueRegistry.FROG_WARM_STATUE.asItem(), StatueRegistry.FROG_COLD_STATUE.asItem(), StatueRegistry.TADPOLE_STATUE.asItem(), StatueRegistry.WARDEN_STATUE.asItem());

		
		this.tag(StatueTags.CURIOS_STATUE).addTag(StatueTags.STATUES_ITEMS).add(StatueRegistry.DISPLAY_STAND.get().asItem(), StatueRegistry.SOMBRERO.get().asItem());

		this.tag(StatueTags.STATUE_CORE).add(StatueRegistry.STATUE_CORE.get());
		this.tag(StatueTags.PLAYER_UPGRADE_ITEM).addTag(StatueTags.STATUE_CORE);
		this.tag(ItemTags.DECORATED_POT_SHERDS).add(StatueRegistry.STATUE_CORE_POTTERY_SHERD.get());
	}
}
