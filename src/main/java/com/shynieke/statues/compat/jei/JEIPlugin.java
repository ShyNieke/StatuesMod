package com.shynieke.statues.compat.jei;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.compat.jei.category.LootCategory;
import com.shynieke.statues.compat.jei.category.UpgradeCategory;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.registry.StatueRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

	public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Reference.MOD_ID, "main");

	public static final ResourceLocation LOOT_BACKGROUND = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/loot.png");
	public static final RecipeType<LootRecipe> LOOT_TYPE = RecipeType.create(Reference.MOD_ID, "loot", LootRecipe.class);

	public static final ResourceLocation UPGRADE_BACKGROUND = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/upgrade.png");
	public static final RecipeType<UpgradeRecipe> UPGRADE_TYPE = RecipeType.create(Reference.MOD_ID, "upgrade", UpgradeRecipe.class);

	@Nullable
	private IRecipeCategory<LootRecipe> lootCategory;

	@Nullable
	private IRecipeCategory<UpgradeRecipe> upgradeCategory;

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(StatueRegistry.STATUE_TABLE.get()), LOOT_TYPE);
		registration.addRecipeCatalyst(new ItemStack(StatueRegistry.STATUE_TABLE.get()), UPGRADE_TYPE);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(
				lootCategory = new LootCategory(guiHelper),
				upgradeCategory = new UpgradeCategory(guiHelper)
		);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
		registration.addRecipes(LOOT_TYPE, clientLevel.getRecipeManager().getAllRecipesFor(StatuesRecipes.LOOT_RECIPE.get()));
		registration.addRecipes(UPGRADE_TYPE, clientLevel.getRecipeManager().getAllRecipesFor(StatuesRecipes.UPGRADE_RECIPE.get()));

		List<StatueBlockItem> statues = StatueRegistry.ITEMS.getEntries().stream()
				.filter(registryObject -> registryObject.get() instanceof StatueBlockItem statueBlock &&
						statueBlock.getBlock() instanceof AbstractStatueBase statueBase && !statueBase.isHiddenStatue())
				.map(registryObject -> (StatueBlockItem) registryObject.get()).toList();
		double chance = StatuesConfig.COMMON.statueDropChance.get();
		for (StatueBlockItem statue : statues) {
			if (statue.getEntity() == null) {
				Statues.LOGGER.error("Tried adding info to statue but statue {} has no entity linked", ForgeRegistries.ITEMS.getKey(statue));
			} else {
				int chancePercentage = (int) (chance * 100);
				if (statue.getEntity() == EntityType.WARDEN || statue.getEntity() == EntityType.ELDER_GUARDIAN) {
					chancePercentage = 100;
				} else if (statue.getEntity() == EntityType.RAVAGER) {
					chancePercentage = 25;
				}
				registration.addItemStackInfo(statue.getDefaultInstance(),
						Component.translatable("statues.gui.jei.statue.info",
								Component.translatable(statue.getDescriptionId()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD),
								Component.literal(chancePercentage + "%").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD),
								Component.translatable(statue.getEntity().getDescriptionId()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)
						)
				);
			}
		}
		registration.addItemStackInfo(StatueRegistry.STATUE_CORE.get().getDefaultInstance(),
				Component.translatable("statues.gui.jei.statue_core.info",
						Component.translatable(StatueRegistry.STATUE_CORE.get().getDescriptionId()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD),
						Component.translatable(StatueRegistry.STATUE_BAT.get().getDescriptionId()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)
				),
				Component.translatable("statues.gui.jei.statue_core.info2",
						Component.translatable(StatueRegistry.STATUE_BAT.get().getDescriptionId()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)
				)
		);
		registration.addItemStackInfo(StatueRegistry.STATUE_TABLE.get().asItem().getDefaultInstance(),
				Component.translatable("statues.gui.jei.statue_table.info"),
				Component.translatable("statues.gui.jei.statue_table.info2"),
				Component.translatable("statues.gui.jei.statue_table.info3"),
				Component.translatable("statues.gui.jei.statue_table.info4"),
				Component.translatable("statues.gui.jei.statue_table.info5"),
				Component.translatable("statues.gui.jei.statue_table.info6"),
				Component.translatable("statues.gui.jei.statue_table.info7"),
				Component.translatable("statues.gui.jei.statue_table.info8")
		);
	}
}
