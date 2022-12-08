package com.shynieke.statues.items;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueTabs;
import com.shynieke.statues.util.UpgradeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class StatueBlockItem extends BlockItem {

	public StatueBlockItem(Block blockIn, Item.Properties builder) {
		super(blockIn, builder.tab(StatueTabs.STATUES_BLOCKS));
	}

	@Nullable
	@Override
	public EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
	}

	public boolean isBaby() {
		return this.getBlock() instanceof AbstractStatueBase statueBase ? statueBase.isBaby() : false;
	}

	public EntityType<?> getEntity() {
		return this.getBlock() instanceof AbstractStatueBase statueBase ? statueBase.getEntity() : null;
	}

	public boolean matchesEntity(LivingEntity livingEntity) {
		return getEntity() != null && getEntity() == livingEntity.getType() && livingEntity.isBaby() == isBaby();
	}

	@Nullable
	@Override
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState state = super.getPlacementState(context);
		ItemStack stack = context.getItemInHand();
		if (state != null && state.getBlock() instanceof AbstractStatueBase) {
			if (stack.hasTag()) {
				CompoundTag compoundtag = stack.getTagElement("BlockEntityTag");
				if (compoundtag == null) {
					compoundtag = stack.getTag();
				}

				if (compoundtag != null && compoundtag.getBoolean(Reference.UPGRADED)) {
					state = state.setValue(AbstractStatueBase.INTERACTIVE, true);
				}
			}
		}
		return state;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded("curios")) {
			return com.shynieke.statues.compat.curios.CuriosCompat.getCapability(stack);
		}
		return super.initCapabilities(stack, nbt);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, level, components, tooltipFlag);
		CompoundTag blockData = stack.getTagElement("BlockEntityTag");
		if (blockData != null) {
			if (blockData.contains(Reference.LEVEL))
				components.add(Component.translatable("statues.info.level").withStyle(ChatFormatting.GOLD)
						.append(" ").append(
								Component.literal(String.valueOf(blockData.getInt(Reference.LEVEL))).withStyle(ChatFormatting.YELLOW)
						)
				);
			if (blockData.contains(Reference.KILL_COUNT))
				components.add(Component.translatable("statues.info.kills").withStyle(ChatFormatting.GOLD)
						.append(" ").append(
								Component.literal(String.valueOf(blockData.getInt(Reference.KILL_COUNT))).withStyle(ChatFormatting.YELLOW)
						)
				);
			if (blockData.contains(Reference.UPGRADE_SLOTS))
				components.add(Component.translatable("statues.info.upgrade_slots").withStyle(ChatFormatting.GOLD)
						.append(" ").append(
								Component.literal(String.valueOf(blockData.getInt(Reference.UPGRADE_SLOTS))).withStyle(ChatFormatting.YELLOW)
						)
				);

			Map<String, Short> upgradeMap = UpgradeHelper.loadUpgradeMap(blockData);
			if (!upgradeMap.isEmpty()) {
				for (Map.Entry<String, Short> entry : upgradeMap.entrySet()) {
					components.add(UpgradeHelper.getUpgradeName(entry.getKey(), (int) entry.getValue()));
				}
			}
		}
	}
}
