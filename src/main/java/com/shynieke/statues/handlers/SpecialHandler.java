package com.shynieke.statues.handlers;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpecialHandler {
	@SubscribeEvent
	public void onCrafted(ItemCraftedEvent event) {
		ItemStack resultStack = event.getCrafting();
		if (resultStack.getItem() == StatueRegistry.PLAYER_STATUE.get().asItem()) {
			Player player = event.getPlayer();
			if (player == null || player instanceof FakePlayer) {
				event.setCanceled(true);
			}

			player.hurt(DamageSource.MAGIC, player.getMaxHealth() / 2);
			resultStack.setHoverName(player.getName());
		}
	}

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START)
			return;

		if (!event.player.level.isClientSide) {
			final Player player = event.player;
			Level level = player.level;
			BlockPos pos = player.blockPosition();
			AABB hitbox = new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f)
					.expandTowards(-3, -3, -3).expandTowards(3, 3, 3);

			for (ItemEntity itemE : player.level.getEntitiesOfClass(ItemEntity.class, hitbox)) {
				if (itemE != null) {
					if (itemE.getItem().getItem().equals(Items.DIAMOND)) {
						AABB bb = itemE.getBoundingBox().contract(0.1, 0.1, 0.1);
						BlockPos lavaPos = itemE.blockPosition();

						boolean lavaFound;
						boolean requirementsFound;

						if (level.getFluidState(itemE.blockPosition()).getType() == Fluids.LAVA) {
							lavaFound = true;
							lavaPos = itemE.blockPosition();
						} else if (level.getBlockState(itemE.blockPosition().below()).getBlock() == Blocks.LAVA) {
							lavaFound = true;
							lavaPos = itemE.blockPosition().below();
						} else {
							lavaFound = false;
						}

						CampfireData data = properStatuesFound(level, lavaPos.above(), StatueRegistry.PLAYER_STATUE.get(), StatueRegistry.CREEPER_STATUE.get());

						requirementsFound = data.getBool() && lavaFound;

						if (requirementsFound) {
							level.setBlockAndUpdate(lavaPos, Blocks.AIR.defaultBlockState());
							level.setBlockAndUpdate(data.getPos1(), Blocks.AIR.defaultBlockState());
							level.setBlockAndUpdate(data.getPos2(), Blocks.AIR.defaultBlockState());

							level.setBlockAndUpdate(lavaPos, StatueRegistry.CAMPFIRE_STATUE.get().defaultBlockState().setValue(AbstractStatueBase.INTERACTIVE, false));
							level.addParticle(ParticleTypes.FLAME, lavaPos.below().getX(), lavaPos.below().getY(), lavaPos.below().getZ(), 0.0D, 0.0D, 0.0D);
							itemE.remove(RemovalReason.DISCARDED);
						}
					}
				}
			}
		}
	}

	public CampfireData properStatuesFound(Level level, BlockPos pos, Block block, Block block1) {
		CampfireData data = new CampfireData(false, pos, pos);

		BlockState northBlock = level.getBlockState(pos.relative(Direction.NORTH));
		BlockState eastBlock = level.getBlockState(pos.relative(Direction.EAST));
		BlockState southBlock = level.getBlockState(pos.relative(Direction.SOUTH));
		BlockState westBlock = level.getBlockState(pos.relative(Direction.WEST));

		if (isValidBlock(northBlock, block) && isValidBlock(southBlock, block1)) {
			data = new CampfireData(true, pos.relative(Direction.NORTH), pos.relative(Direction.SOUTH));
		} else if (isValidBlock(northBlock, block1) && isValidBlock(southBlock, block)) {
			data = new CampfireData(true, pos.relative(Direction.NORTH), pos.relative(Direction.SOUTH));
		} else if (isValidBlock(westBlock, block) && isValidBlock(eastBlock, block1)) {
			data = new CampfireData(true, pos.relative(Direction.WEST), pos.relative(Direction.EAST));
		} else if (isValidBlock(westBlock, block1) && isValidBlock(eastBlock, block)) {
			data = new CampfireData(true, pos.relative(Direction.WEST), pos.relative(Direction.EAST));
		}

		return data;
	}

	public boolean isValidBlock(BlockState state, Block block) {
		if (state.getBlock() == block && state.getBlock() instanceof AbstractStatueBase) {
			return !state.getValue(AbstractStatueBase.INTERACTIVE);
		} else {
			return state.getBlock() instanceof PlayerStatueBlock;
		}
	}

	public static class CampfireData {
		private final boolean bool;
		private final BlockPos pos1;
		private final BlockPos pos2;

		public CampfireData(boolean bool, BlockPos pos1, BlockPos pos2) {
			this.bool = bool;
			this.pos1 = pos1;
			this.pos2 = pos2;
		}

		public Boolean getBool() {
			return bool;
		}

		public BlockPos getPos1() {
			return pos1;
		}

		public BlockPos getPos2() {
			return pos2;
		}
	}
}
