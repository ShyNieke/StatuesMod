package com.shynieke.statues.items;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class CustomSpawnEggItem extends Item {
    private static final Map<Supplier<EntityType<?>>, CustomSpawnEggItem> EGGS = Maps.newIdentityHashMap();
    private final Supplier<EntityType<?>> typeIn;
    private final int primaryColor;
    private final int secondaryColor;

    public CustomSpawnEggItem(Supplier<EntityType<?>> type, int primary, int secondary, Properties properties) {
        super(properties);
        this.typeIn = type;
        this.primaryColor = primary;
        this.secondaryColor = secondary;
        EGGS.put(type, this);
    }

    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack stack = context.getItemInHand();
            BlockPos pos = context.getClickedPos();
            Direction dir = context.getClickedFace();
            BlockState state = worldIn.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.SPAWNER) {
                TileEntity tile = worldIn.getBlockEntity(pos);
                if (tile instanceof MobSpawnerTileEntity) {
                    AbstractSpawner spawner = ((MobSpawnerTileEntity)tile).getSpawner();
                    EntityType<?> type = this.getType(stack.getTag());
                    spawner.setEntityId(type);
                    tile.setChanged();
                    worldIn.sendBlockUpdated(pos, state, state, 3);
                    stack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }

            BlockPos pos2;
            if (state.getBlockSupportShape(worldIn, pos).isEmpty()) {
                pos2 = pos;
            } else {
                pos2 = pos.relative(dir);
            }

            EntityType<?> type = this.getType(stack.getTag());
            if (!worldIn.isClientSide && type.spawn((ServerWorld)worldIn, stack, context.getPlayer(), pos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(pos, pos2) && dir == Direction.UP) != null) {
                stack.shrink(1);
            }

            return ActionResultType.SUCCESS;
        }
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else if (worldIn.isClientSide) {
            return ActionResult.success(itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(itemstack);
            } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemstack)) {
                EntityType<?> entitytype = this.getType(itemstack.getTag());
                if (!worldIn.isClientSide && entitytype.spawn((ServerWorld)worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false) == null) {
                    return ActionResult.pass(itemstack);
                } else {
                    if (!playerIn.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.success(itemstack);
                }
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int p_195983_1_) {
        return p_195983_1_ == 0 ? this.primaryColor : this.secondaryColor;
    }

    public static Iterable<CustomSpawnEggItem> getEggs() {
        return Iterables.unmodifiableIterable(EGGS.values());
    }

    public EntityType<?> getType(@Nullable CompoundNBT compound) {
        if (compound != null && compound.contains("EntityTag", 10)) {
            CompoundNBT lvt_2_1_ = compound.getCompound("EntityTag");
            if (lvt_2_1_.contains("id", 8)) {
                return (EntityType)EntityType.byString(lvt_2_1_.getString("id")).orElse(this.typeIn.get());
            }
        }

        return this.typeIn.get();
    }
}