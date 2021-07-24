package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ChickenStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);

	public ChickenStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if(this.isDecorative(state) && placer != null) {
			Block block = worldIn.getBlockState(pos.below()).getBlock();
			if (block == Blocks.GOLD_BLOCK) {
				BlockPos downPos = pos.below();
				worldIn.addParticle(ParticleTypes.EXPLOSION, downPos.getX(), downPos.getY(), downPos.getZ(), 1.0D, 0.0D, 0.0D);
				worldIn.setBlockAndUpdate(pos.below(), StatueRegistry.KING_CLUCK_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		}
		super.setPlacedBy(worldIn, pos, state, placer, stack);
	}

	public boolean isDecorative(BlockState state) {
		return !state.getValue(INTERACTIVE).booleanValue();
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		tile.summonMob(new Chicken(EntityType.CHICKEN, worldIn));
	}

	@Override
	public String getLootName() {
		return "chicken";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.CHICKEN;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.CHICKEN_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
