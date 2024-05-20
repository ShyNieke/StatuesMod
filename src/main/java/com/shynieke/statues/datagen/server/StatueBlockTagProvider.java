package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StatueBlockTagProvider extends BlockTagsProvider {
	public StatueBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(StatueTags.STATUE_BLOCKS).add(StatueRegistry.ANGRY_BEE_STATUE.get(), StatueRegistry.BABY_ZOMBIE_STATUE.get(), StatueRegistry.BEE_STATUE.get(), StatueRegistry.TRANS_BEE_STATUE.get(), StatueRegistry.BLAZE_STATUE.get(),
				StatueRegistry.BROWN_MOOSHROOM_STATUE.get(), StatueRegistry.CAMPFIRE_STATUE.get(), StatueRegistry.CAT_BLACK_STATUE.get(), StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get(), StatueRegistry.CAT_CALICO_STATUE.get(),
				StatueRegistry.CAT_JELLIE_STATUE.get(), StatueRegistry.CAT_PERSIAN_STATUE.get(), StatueRegistry.CAT_RAGDOLL_STATUE.get(), StatueRegistry.CAT_RED_STATUE.get(), StatueRegistry.CAT_SIAMESE_STATUE.get(), StatueRegistry.CAT_TABBY_STATUE.get(),
				StatueRegistry.CAT_TUXEDO_STATUE.get(), StatueRegistry.CAT_WHITE_STATUE.get(), StatueRegistry.CHICKEN_JOCKEY_STATUE.get(), StatueRegistry.CHICKEN_STATUE.get(), StatueRegistry.COD_STATUE.get(), StatueRegistry.COW_STATUE.get(),
				StatueRegistry.CREEPER_STATUE.get(), StatueRegistry.DETECTIVE_PLATYPUS.get(), StatueRegistry.DOLPHIN_STATUE.get(), StatueRegistry.DROWNED_STATUE.get(), StatueRegistry.ELDER_GUARDIAN_STATUE.get(), StatueRegistry.ENDERMAN_STATUE.get(),
				StatueRegistry.ENDERMITE_STATUE.get(), StatueRegistry.EVOKER_STATUE.get(), StatueRegistry.FLOOD_STATUE.get(), StatueRegistry.FOX_SNOW_STATUE.get(), StatueRegistry.FOX_STATUE.get(), StatueRegistry.GHAST_STATUE.get(), StatueRegistry.GUARDIAN_STATUE.get(),
				StatueRegistry.HUSK_STATUE.get(), StatueRegistry.INFO_STATUE.get(), StatueRegistry.KING_CLUCK_STATUE.get(), StatueRegistry.MAGMA_STATUE.get(), StatueRegistry.MOOSHROOM_STATUE.get(), StatueRegistry.PANDA_ANGRY_STATUE.get(),
				StatueRegistry.PANDA_BROWN_STATUE.get(), StatueRegistry.PANDA_LAZY_STATUE.get(), StatueRegistry.PANDA_NORMAL_STATUE.get(), StatueRegistry.PANDA_PLAYFUL_STATUE.get(), StatueRegistry.PANDA_WEAK_STATUE.get(),
				StatueRegistry.PANDA_WORRIED_STATUE.get(), StatueRegistry.PIG_STATUE.get(), StatueRegistry.PILLAGER_STATUE.get(), StatueRegistry.PLAYER_STATUE.get(), StatueRegistry.PUFFERFISH_MEDIUM_STATUE.get(),
				StatueRegistry.PUFFERFISH_SMALL_STATUE.get(), StatueRegistry.PUFFERFISH_STATUE.get(), StatueRegistry.RABBIT_BR_STATUE.get(), StatueRegistry.RABBIT_BS_STATUE.get(), StatueRegistry.RABBIT_BW_STATUE.get(),
				StatueRegistry.RABBIT_GO_STATUE.get(), StatueRegistry.RABBIT_WH_STATUE.get(), StatueRegistry.RABBIT_WS_STATUE.get(), StatueRegistry.RAVAGER_STATUE.get(), StatueRegistry.SALMON_STATUE.get(), StatueRegistry.SHEEP_SHAVEN_STATUE.get(),
				StatueRegistry.SHEEP_STATUE_BLACK.get(), StatueRegistry.SHEEP_STATUE_BLUE.get(), StatueRegistry.SHEEP_STATUE_BROWN.get(), StatueRegistry.SHEEP_STATUE_CYAN.get(), StatueRegistry.SHEEP_STATUE_GRAY.get(), StatueRegistry.SHEEP_STATUE_GREEN.get(),
				StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.get(), StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.get(), StatueRegistry.SHEEP_STATUE_LIME.get(), StatueRegistry.SHEEP_STATUE_MAGENTA.get(), StatueRegistry.SHEEP_STATUE_ORANGE.get(),
				StatueRegistry.SHEEP_STATUE_PINK.get(), StatueRegistry.SHEEP_STATUE_PURPLE.get(), StatueRegistry.SHEEP_STATUE_RED.get(), StatueRegistry.SHEEP_STATUE_WHITE.get(), StatueRegistry.SHEEP_STATUE_YELLOW.get(), StatueRegistry.SHULKER_STATUE.get(),
				StatueRegistry.SLIME_STATUE.get(), StatueRegistry.SNOW_GOLEM_STATUE.get(), StatueRegistry.SPIDER_STATUE.get(), StatueRegistry.SQUID_STATUE.get(), StatueRegistry.TOTEM_OF_UNDYING_STATUE.get(), StatueRegistry.TROPICAL_FISH_B.get(),
				StatueRegistry.TROPICAL_FISH_BB.get(), StatueRegistry.TROPICAL_FISH_BE.get(), StatueRegistry.TROPICAL_FISH_BM.get(), StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(), StatueRegistry.TROPICAL_FISH_E.get(),
				StatueRegistry.TROPICAL_FISH_ES.get(), StatueRegistry.TROPICAL_FISH_HB.get(), StatueRegistry.TROPICAL_FISH_SB.get(), StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get(), StatueRegistry.TURTLE_STATUE.get(),
				StatueRegistry.VILLAGER_BR_STATUE.get(), StatueRegistry.VILLAGER_GR_STATUE.get(), StatueRegistry.VILLAGER_PU_STATUE.get(), StatueRegistry.VILLAGER_WH_STATUE.get(), StatueRegistry.VINDICATOR_STATUE.get(), StatueRegistry.WASTELAND_STATUE.get(),
				StatueRegistry.WITCH_STATUE.get(), StatueRegistry.ZOMBIE_STATUE.get(), StatueRegistry.BUMBO_STATUE.get(), StatueRegistry.TROPIBEE.get(), StatueRegistry.EAGLE_RAY.get(), StatueRegistry.SLABFISH.get(), StatueRegistry.AZZARO.get(),
				StatueRegistry.ALLAY_STATUE.get(), StatueRegistry.AXOLOTL_LUCY_STATUE.get(), StatueRegistry.AXOLOTL_WILD_STATUE.get(), StatueRegistry.AXOLOTL_GOLD_STATUE.get(), StatueRegistry.AXOLOTL_CYAN_STATUE.get(), StatueRegistry.AXOLOTL_BLUE_STATUE.get(),
				StatueRegistry.FROG_TEMPERATE_STATUE.get(), StatueRegistry.FROG_WARM_STATUE.get(), StatueRegistry.FROG_COLD_STATUE.get(), StatueRegistry.TADPOLE_STATUE.get(), StatueRegistry.WARDEN_STATUE.get());

		this.tag(StatueTags.IS_TROPICAL_FISH).add(
				StatueRegistry.TROPICAL_FISH_B.get(), StatueRegistry.TROPICAL_FISH_BB.get(),
				StatueRegistry.TROPICAL_FISH_BE.get(), StatueRegistry.TROPICAL_FISH_BM.get(),
				StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(),
				StatueRegistry.TROPICAL_FISH_E.get(), StatueRegistry.TROPICAL_FISH_ES.get(),
				StatueRegistry.TROPICAL_FISH_HB.get(), StatueRegistry.TROPICAL_FISH_SB.get(),
				StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get()
		);
	}
}
