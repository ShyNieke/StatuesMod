package com.shynieke.statues.blocks;

import com.mojang.serialization.MapCodec;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoreFlowerCropBlock extends CropBlock {
	public static final MapCodec<TorchflowerCropBlock> CODEC = simpleCodec(TorchflowerCropBlock::new);
	public static final int MAX_AGE = 8;
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 8);
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
			Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)
	};

	@Override
	public MapCodec<TorchflowerCropBlock> codec() {
		return CODEC;
	}

	public CoreFlowerCropBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(AGE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state)];
	}

	@Override
	protected IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return Items.TORCHFLOWER_SEEDS;
	}

	@Override
	public BlockState getStateForAge(int age) {
		return age == 8 ? StatueRegistry.CORE_FLOWER.get().defaultBlockState() : super.getStateForAge(age);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextInt(3) != 0) {
			super.randomTick(state, level, pos, random);
		}
	}

	@Override
	protected int getBonemealAgeIncrease(Level level) {
		return 1;
	}
}
