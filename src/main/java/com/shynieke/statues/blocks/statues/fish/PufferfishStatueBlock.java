package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PufferfishStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
	private static final VoxelShape SHAPE_MEDIUM = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D);
	private static final VoxelShape SHAPE_BIG = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10D, 13.0D);
	private final int size;

	public PufferfishStatueBlock(Properties builder, int size) {
		super(builder.sound(SoundType.STONE));
		this.size = size;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return switch (size) {
			default -> SHAPE;
			case 1 -> SHAPE_MEDIUM;
			case 2 -> SHAPE_BIG;
		};
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		tile.giveEffect(pos, playerIn, MobEffects.POISON);
	}

	@Override
	public String getLootName() {
		return "pufferfish";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.PUFFERFISH;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.PUFFER_FISH_BLOW_UP;
	}
}
