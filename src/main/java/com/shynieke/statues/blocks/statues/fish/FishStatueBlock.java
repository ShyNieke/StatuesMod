package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blockentities.TropicalFishBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class FishStatueBlock extends AbstractStatueBase {
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
    private static final VoxelShape SHAPE_BIG = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D);
    private final int size;

    public FishStatueBlock(Properties builder, int size) {
        super(builder.sound(SoundType.STONE));
        this.size = size;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (size == 1) {
            return SHAPE_BIG;
        }
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
        ItemStack stack = playerIn.getItemInHand(InteractionHand.MAIN_HAND);
        if(stack.getItem() == Items.WET_SPONGE) {
            TropicalFishBlockEntity tile = getFishTE(level, pos);
            tile.scrambleColors();
            level.sendBlockUpdated(pos, state, state, 6);
            level.playSound(null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.NEUTRAL, 1F, 1.0F);
            if(!playerIn.getAbilities().instabuild) {
                stack.shrink(1);
                if(!playerIn.getInventory().add(new ItemStack(Items.SPONGE))) {
                    ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), stack);
                    level.addFreshEntity(itemEntity);
                }
            }
        }
        return super.use(state, level, pos, playerIn, handIn, result);
    }

    @Override
    public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
        //TODO: Fish stuff? What should it do.
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return hasTileEntity(state) ? new TropicalFishBlockEntity(pos, state) : null;
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public EntityType<?> getEntity() {
        return EntityType.TROPICAL_FISH;
    }

    @Override
    public SoundEvent getSound(BlockState state) {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    public TropicalFishBlockEntity getFishTE(BlockGetter world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof TropicalFishBlockEntity ? (TropicalFishBlockEntity) world.getBlockEntity(pos): null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        TropicalFishBlockEntity tile = getFishTE(worldIn, pos);
        if(tile != null) {
            tile.scrambleColors();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static int getColor(BlockState state, BlockGetter world, BlockPos pos, int tintIndex) {
        if(pos != null) {
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof TropicalFishBlockEntity fishTile) {
                return tintIndex == 1 ? fromColor(fishTile.getMainColor()) : tintIndex == 2 ? fromColor(fishTile.getSecondaryColor()) : -1;
            }
        }
        return -1;
    }

    @OnlyIn(Dist.CLIENT)
    public static int fromColor(int color) {
        return DyeColor.byId(color).getTextColor();
    }
}
