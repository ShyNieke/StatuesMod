package com.shynieke.statues.blocks.decorative;

import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SombreroBlock extends AbstractBaseBlock {

    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);

    public SombreroBlock(Properties properties) {
        super(properties.sound(SoundType.STONE));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Block block = worldIn.getBlockState(pos.below()).getBlock();
        if (block == Blocks.CACTUS && placer != null) {
            BlockPos downPos = pos.below();
            worldIn.addParticle(ParticleTypes.EXPLOSION, downPos.getX(), downPos.getY(), downPos.getZ(), 1.0D, 0.0D, 0.0D);
            worldIn.setBlockAndUpdate(pos.below(), StatueRegistry.BUMBO_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
            worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
        if(!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
            if (canPlaySound(worldIn, pos, state)) {
                worldIn.playSound(null, pos, SoundEvents.ANVIL_LAND, SoundCategory.NEUTRAL, 1F, getPitch());
            }
        }
        return ActionResultType.SUCCESS;
    }

    public float getPitch() {
        return (this.RANDOM.nextFloat() - this.RANDOM.nextFloat()) * 0.2F + 1.0F;
    }

    public boolean canPlaySound(World worldIn, BlockPos pos, BlockState state) {
        return worldIn.getBlockState(pos.below()).getBlock() instanceof NoteBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
