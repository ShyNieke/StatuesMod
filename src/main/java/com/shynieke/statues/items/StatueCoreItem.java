package com.shynieke.statues.items;

import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class StatueCoreItem extends RecordItem {

	private final String entityTag = "locked_entity";
	private boolean isLocked = false;

	public StatueCoreItem(Item.Properties builder) {
		super(0, () -> SoundEvents.MUSIC_CREDITS, builder, 12060);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
		CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
		if (tag != null && !tag.getString(entityTag).isEmpty()) {
			tooltips.add(new TranslatableComponent("statues.core.info,", tag.getString(entityTag)).withStyle(ChatFormatting.GOLD));
		}
		super.appendHoverText(stack, level, tooltips, flag);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity entityIn, InteractionHand handIn) {
		if (!(entityIn instanceof Player) && !isLocked) {
			CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
			if (tag != null) {
				if (!tag.getString(entityTag).isEmpty()) {
					this.isLocked = true;
					return InteractionResult.FAIL;
				} else {
					if (entityIn.isAlive()) {
						if (entityIn instanceof Mob) {
							tag.putString(entityTag, String.valueOf(ForgeRegistries.ENTITIES.getKey(entityIn.getType())));
							stack.setTag(tag);
						}
					}
				}
			}

			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.FAIL;
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (!player.isSecondaryUseActive()) {
			Level world = context.getLevel();
			BlockPos blockpos = context.getClickedPos();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() instanceof JukeboxBlock jukeboxBlock && !blockstate.getValue(JukeboxBlock.HAS_RECORD)) {
				ItemStack itemstack = context.getItemInHand();
				if (!world.isClientSide) {
					jukeboxBlock.setRecord(player, world, blockpos, blockstate, itemstack.split(1));
					world.levelEvent(1010, blockpos, Item.getId(StatueRegistry.STATUE_CORE.get()));

					player.awardStat(Stats.PLAY_RECORD);
				}
				return InteractionResult.sidedSuccess(world.isClientSide);
			}
		}
		return super.useOn(context);
	}
}
