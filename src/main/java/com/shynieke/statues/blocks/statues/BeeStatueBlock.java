package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BeeStatueBlock extends AngryBeeStatueBlock {
	public static final BooleanProperty TRANS = BooleanProperty.create("trans");

	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 11D, 12.0D);

	public BeeStatueBlock(Properties builder) {
		super(builder);
		this.setDefaultState(this.getDefaultState().with(TRANS, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if(context.getItem().getDisplayName().getUnformattedComponentText().equalsIgnoreCase("Trans Bee")) {
			return super.getStateForPlacement(context).with(TRANS, true);
		} else {
			return super.getStateForPlacement(context);
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
				itemstack.setDisplayName(new StringTextComponent("Trans Bee"));

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
		if(state.get(TRANS)) {
			itemstack.setDisplayName(new StringTextComponent("Trans Bee"));
		}

		return itemstack;
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.playSound(SoundEvents.ENTITY_BEE_LOOP, pos, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F + 1.5F);
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
//		tile.FakeMobs(new FakeZombie(worldIn), worldIn, pos, true);
	}

	@Override
	public String getLootName() {
		return "bee";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.BEE;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED, INTERACTIVE, TRANS);
	}
}
