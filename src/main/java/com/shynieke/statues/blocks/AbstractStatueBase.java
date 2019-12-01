package com.shynieke.statues.blocks;

import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class AbstractStatueBase extends AbstractBaseBlock {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	public static final BooleanProperty INTERACTIVE = BooleanProperty.create("interactive");

	public AbstractStatueBase(Block.Properties builder) {
		super(builder.hardnessAndResistance(0.6F)); // Resistance 0.6F
		this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)).with(INTERACTIVE, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		if (state.get(INTERACTIVE).booleanValue()) {
			if (!worldIn.isRemote && (getTE(worldIn, pos) != null)) {
				executeStatueBehavior(getTE(worldIn, pos), state, worldIn, pos, playerIn, handIn, result);
			}
			return true;
		} else
			return false;
	}

	public StatueTile getTE(World world, BlockPos pos) {
		return world.getTileEntity(pos) instanceof StatueTile ? (StatueTile) world.getTileEntity(pos): null;
	}

	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {

	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		if (state.get(INTERACTIVE).booleanValue()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		if (state.get(INTERACTIVE).booleanValue()) {
			return new StatueTile();
		} else {
			return null;
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof StatueTile) {
			StatueTile statueTile = (StatueTile)tileentity;
			if (!worldIn.isRemote) {
				ItemStack itemstack = new ItemStack(this);
				CompoundNBT compoundnbt = statueTile.saveToNbt(new CompoundNBT());
				if (!compoundnbt.isEmpty()) {
					itemstack.setTagInfo("BlockEntityTag", compoundnbt);
				}

				ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
				itementity.setDefaultPickupDelay();
				worldIn.addEntity(itementity);
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		ItemStack itemstack = super.getItem(worldIn, pos, state);
		if(state.get(INTERACTIVE)) {
			StatueTile statueTile = (StatueTile)worldIn.getTileEntity(pos);
			CompoundNBT compoundnbt = statueTile.saveToNbt(new CompoundNBT());
			if (!compoundnbt.isEmpty()) {
				itemstack.setTagInfo("BlockEntityTag", compoundnbt);
			}
		}

		return itemstack;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED, INTERACTIVE);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			super.onReplaced(state, worldIn, pos, newState, isMoving);
			worldIn.removeTileEntity(pos);
		}
	}

	public boolean canBeUpgraded() { return true; }

	public boolean isHiddenStatue() {
		return false;
	}

	public boolean isBaby() {
		return false;
	}

	public EntityType<?> getEntity() {
		return EntityType.BAT;
	}

	public String getLootName() {
		return "baby_zombie";
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.SHAPE;
	}
}
