package com.shynieke.statues.items;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Objects;

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
			if (type.spawn((ServerLevel) level, stack, context.getPlayer(), relativePos, EntitySpawnReason.SPAWN_ITEM_USE, true, !Objects.equals(pos, relativePos) && direction == Direction.UP) instanceof PlayerStatue playerStatue) {
				applyRandomRotations(playerStatue, level.random);
				if (!stack.has(DataComponents.CUSTOM_NAME)) {
					if (context.getPlayer() != null) {
						playerStatue.setGameProfile(new ResolvableProfile(context.getPlayer().getGameProfile()));
					} else {
						playerStatue.setGameProfile(new ResolvableProfile(new GameProfile(Util.NIL_UUID, "steve")));
					}
				}
				stack.shrink(1);
				level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
			}

			return InteractionResult.CONSUME;
		}
	}

	public static void applyRandomRotations(PlayerStatue playerStatueEntity, RandomSource rand) {
		Rotations rotations = playerStatueEntity.getHeadPose();
		float f = rand.nextFloat() * 5.0F;
		float f1 = rand.nextFloat() * 20.0F - 10.0F;
		Rotations rotations1 = new Rotations(rotations.x() + f, rotations.y() + f1, rotations.z());
		playerStatueEntity.setHeadPose(rotations1);
		rotations = playerStatueEntity.getBodyPose();
		f = rand.nextFloat() * 10.0F - 5.0F;
		rotations1 = new Rotations(rotations.x(), rotations.y() + f, rotations.z());
		playerStatueEntity.setBodyPose(rotations1);
	}
}
