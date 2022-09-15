package com.shynieke.statues.blocks.statues;

import com.google.common.collect.ImmutableList;
import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueSounds;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.util.ListHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.Supplier;

public class CampfireStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 7.0D, 16.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.box(0.0D, 0.0D, 5.0D, 16.0D, 7.0D, 11.0D);

	public CampfireStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE).lightLevel((state) -> 12));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity blockEntity, BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		blockEntity.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		blockEntity.summonMob(getGeneral(level));
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return getRandomCampfire();
	}

	public Creeper getGeneral(Level level) {
		Creeper general = new Creeper(EntityType.CREEPER, level);
		general.setCustomName(Component.literal("General Spazz"));
		CompoundTag tag = new CompoundTag();
		tag.putByte("ExplosionRadius", (byte) 0);
		general.addAdditionalSaveData(tag);

		return general;
	}

	public static List<Supplier<SoundEvent>> campfire_sounds = ImmutableList.of(
			StatueSounds.CAMPFIRE_BYE_RANDOM,
			StatueSounds.CAMPFIRE_COLD_RANDOM,
			StatueSounds.CAMPFIRE_GREETINGS_RANDOM,
			StatueSounds.CAMPFIRE_HELLO_RANDOM,
			StatueSounds.CAMPFIRE_SNACKS_RANDOM);

	public static SoundEvent getRandomCampfire() {
		return ListHelper.getRandomFromList(campfire_sounds).get();
	}

	@Override
	public String getLootName() {
		return "etho";
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}
}
