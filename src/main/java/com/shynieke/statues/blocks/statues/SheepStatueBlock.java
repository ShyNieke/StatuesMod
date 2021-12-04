package com.shynieke.statues.blocks.statues;

import com.google.common.collect.Maps;
import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SheepStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.box(4.0D, 0.0D, 3.5D, 12.0D, 8.5D, 12.5D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.box(3.5D, 0.0D, 4.0D, 12.5D, 8.5D, 12.0D);
	private static final Map<DyeColor, SheepStatueBlock> COLOR_DYE_STATUE_MAP = Maps.newEnumMap(DyeColor.class);
	private final DyeColor COLOR;

	public SheepStatueBlock(Properties builder, DyeColor color) {
		super(builder.sound(SoundType.STONE));
		this.COLOR = color;
		COLOR_DYE_STATUE_MAP.put(color, this);
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
	}

	@Override
	public String getLootName() {
		return "sheep_" + COLOR.getSerializedName();
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.SHEEP;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.SHEEP_AMBIENT;
	}

	public DyeColor getColor() {
		return COLOR;
	}

	public static SheepStatueBlock getStatue(DyeColor color) {
		return COLOR_DYE_STATUE_MAP.get(color);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}
}
