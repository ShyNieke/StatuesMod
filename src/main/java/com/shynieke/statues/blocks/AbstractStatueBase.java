package com.shynieke.statues.blocks;

import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		if(!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
			if (canPlaySound(worldIn, pos, state)) {
				worldIn.playSound(null, pos, getSound(state), SoundCategory.NEUTRAL, 1F, getPitch());
			}
		}
		if (state.get(INTERACTIVE).booleanValue() && handIn == Hand.MAIN_HAND) {
			if (!worldIn.isRemote && (getTE(worldIn, pos) != null)) {
				executeStatueBehavior(getTE(worldIn, pos), state, worldIn, pos, playerIn, handIn, result);
			}
		}

		return ActionResultType.SUCCESS;
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

	public SoundEvent getSound(BlockState state) {
		return SoundEvents.BLOCK_ANVIL_LAND;
	}

	public float getPitch() {
		return this.isBaby() ? (this.RANDOM.nextFloat() - this.RANDOM.nextFloat()) * 0.2F + 1.5F : (this.RANDOM.nextFloat() - this.RANDOM.nextFloat()) * 0.2F + 1.0F;
	}

	public boolean canPlaySound(World worldIn, BlockPos pos, BlockState state) {
		boolean flag = state.get(INTERACTIVE) && ((StatueTile)worldIn.getTileEntity(pos)).makesSounds();
		boolean flag2 = worldIn.getBlockState(pos.down()).getBlock() instanceof NoteBlock;
		return flag || flag2;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.SHAPE;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isRemote) {
			if (canPlaySound(worldIn, pos, state) && worldIn.isBlockPowered(pos)) {
				worldIn.playSound(null, pos, getSound(state), SoundCategory.NEUTRAL, 1F, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F + 1.5F);
			}
		}
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
	}
}
