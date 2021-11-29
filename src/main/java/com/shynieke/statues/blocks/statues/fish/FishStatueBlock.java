package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.tiles.StatueTile;
import com.shynieke.statues.tiles.TropicalFishTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (size == 1) {
            return SHAPE_BIG;
        }
        return SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
        ItemStack stack = playerIn.getItemInHand(Hand.MAIN_HAND);
        if(stack.getItem() == Items.WET_SPONGE) {
            TropicalFishTile tile = getFishTE(worldIn, pos);
            tile.scrambleColors();
            worldIn.sendBlockUpdated(pos, state, state, 6);
            worldIn.playSound(null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundCategory.NEUTRAL, 1F, 1.0F);
            if(!playerIn.abilities.instabuild) {
                stack.shrink(1);
                if(!playerIn.inventory.add(new ItemStack(Items.SPONGE))) {
                    ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY() + 0.5, pos.getZ());
                    itemEntity.setItem(stack);
                    worldIn.addFreshEntity(itemEntity);
                }
            }
        }
        return super.use(state, worldIn, pos, playerIn, handIn, result);
    }

    @Override
    public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
        //TODO: Fish stuff? What should it do.
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TropicalFishTile();
    }

    @Override
    public EntityType<?> getEntity() {
        return EntityType.TROPICAL_FISH;
    }

    @Override
    public SoundEvent getSound(BlockState state) {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    public TropicalFishTile getFishTE(IBlockReader world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof TropicalFishTile ? (TropicalFishTile) world.getBlockEntity(pos): null;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        TropicalFishTile tile = getFishTE(worldIn, pos);
        if(tile != null) {
            tile.scrambleColors();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static int getColor(BlockState state, IBlockReader world, BlockPos pos, int tintIndex) {
        if(pos != null) {
            TileEntity tile = world.getBlockEntity(pos);
            if(tile instanceof TropicalFishTile) {
                TropicalFishTile fishTile = (TropicalFishTile) tile;
                return tintIndex == 1 ? fromColor(fishTile.getMainColor()) : tintIndex == 2 ? fromColor(fishTile.getSecondaryColor()) : -1;
            }
        }
        return -1;
    }

    @OnlyIn(Dist.CLIENT)
    public static int fromColor(int color) {
        return DyeColor.byId(color).getColorValue();
    }
}
