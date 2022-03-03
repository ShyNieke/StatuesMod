package com.shynieke.statues.items;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class PlayerStatueSpawnItem extends Item {

	public PlayerStatueSpawnItem(Properties builder) {
		super(builder);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (!(level instanceof ServerLevel)) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack stack = context.getItemInHand();
			BlockPos pos = context.getClickedPos();
			Direction direction = context.getClickedFace();
			BlockState state = level.getBlockState(pos);

			BlockPos relativePos;
			if (state.getCollisionShape(level, pos).isEmpty()) {
				relativePos = pos;
			} else {
				relativePos = pos.relative(direction);
			}

			EntityType<?> type = StatueRegistry.PLAYER_STATUE_ENTITY.get();
			if (type.spawn((ServerLevel) level, stack, context.getPlayer(), relativePos, MobSpawnType.SPAWN_EGG, true, !Objects.equals(pos, relativePos) && direction == Direction.UP) instanceof PlayerStatue playerStatue) {
				applyRandomRotations(playerStatue, level.random);
				if (!stack.hasCustomHoverName()) {
					if (context.getPlayer() != null) {
						playerStatue.setGameProfile(context.getPlayer().getGameProfile());
					} else {
						playerStatue.setGameProfile(new GameProfile((UUID) null, "steve"));
					}
				}
				stack.shrink(1);
				level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
			}

			return InteractionResult.CONSUME;
		}
	}

	public static void applyRandomRotations(PlayerStatue playerStatueEntity, Random rand) {
		Rotations rotations = playerStatueEntity.getHeadRotation();
		float f = rand.nextFloat() * 5.0F;
		float f1 = rand.nextFloat() * 20.0F - 10.0F;
		Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
		playerStatueEntity.setHeadRotation(rotations1);
		rotations = playerStatueEntity.getBodyRotation();
		f = rand.nextFloat() * 10.0F - 5.0F;
		rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
		playerStatueEntity.setBodyRotation(rotations1);
	}
}
