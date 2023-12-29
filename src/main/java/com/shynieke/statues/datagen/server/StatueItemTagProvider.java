package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class StatueItemTagProvider extends ItemTagsProvider {

	public StatueItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
								 TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTagProvider.contentsGetter(), Reference.MOD_ID, existingFileHelper);
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
				StatueRegistry.ALLAY_STATUE.get().asItem());

		this.tag(StatueTags.STATUE_INTERACTABLE).add(StatueRegistry.FLOOD_STATUE.get().asItem(), StatueRegistry.MOOSHROOM_STATUE.get().asItem(),
				StatueRegistry.BROWN_MOOSHROOM_STATUE.get().asItem(), StatueRegistry.COW_STATUE.get().asItem(), StatueRegistry.SPIDER_STATUE.get().asItem(),
				StatueRegistry.SHULKER_STATUE.get().asItem());

		this.copy(StatueTags.STATUE_BLOCKS, StatueTags.STATUES_ITEMS);

		this.tag(StatueTags.CURIOS_STATUE).addTag(StatueTags.STATUES_ITEMS).add(StatueRegistry.DISPLAY_STAND.get().asItem(), StatueRegistry.SOMBRERO.get().asItem());

		this.tag(StatueTags.STATUE_CORE).add(StatueRegistry.STATUE_CORE.get());
		this.tag(StatueTags.PLAYER_UPGRADE_ITEM).addTag(StatueTags.STATUE_CORE);
	}
}
