package com.shynieke.statues.compat.jade;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blockentities.AbstractStatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {

	@Override
	public void register(IWailaCommonRegistration registration) {
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(PastryBodyHandler.INSTANCE, AbstractStatueBase.class);
	}

	public static class PastryBodyHandler implements IBlockComponentProvider {
		private static final ResourceLocation BITES = Reference.modLoc("upgrades");

		public static final PastryBodyHandler INSTANCE = new PastryBodyHandler();

		@Override
		public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
			if (blockAccessor.getBlockEntity() instanceof AbstractStatueBlockEntity statueBlockEntity) {
				iTooltip.add(Component.translatable("statues.info.level").withStyle(ChatFormatting.GOLD)
						.append(" ").append(
								Component.literal(String.valueOf(statueBlockEntity.getStatueLevel())).withStyle(ChatFormatting.YELLOW)
						)
				);
				iTooltip.add(Component.translatable("statues.info.kills").withStyle(ChatFormatting.GOLD)
						.append(" ").append(
								Component.literal(String.valueOf(statueBlockEntity.getKillCount())).withStyle(ChatFormatting.YELLOW)
						)
				);
				iTooltip.add(Component.translatable("statues.info.upgrade_slots").withStyle(ChatFormatting.GOLD)
						.append(" ").append(
								Component.literal(String.valueOf(statueBlockEntity.getUpgradeSlots())).withStyle(ChatFormatting.YELLOW)
						)
				);
				for (var entry : statueBlockEntity.getUpgrades().entrySet()) {
					iTooltip.add(getUpgradeName(entry.getKey(), (int) entry.getValue()));
				}
			}
		}

		private MutableComponent getUpgradeName(String id, int level) {
			String descriptionID = "statues.upgrade." + id + ".name";
			MutableComponent mutablecomponent = Component.translatable(descriptionID).withStyle(ChatFormatting.GRAY);

			if (level > 0) {
				mutablecomponent.append(" ").append(Component.translatable("enchantment.level." + level));
			}

			return mutablecomponent;
		}

		@Override
		public ResourceLocation getUid() {
			return BITES;
		}
	}
}
