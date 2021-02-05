package com.shynieke.statues.items;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.entity.PlayerStatueEntity;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.UUID;

public class PlayerStatueItem extends Item {

    public PlayerStatueItem(Properties builder) {
        super(builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        Direction direction = context.getFace();
        if (direction == Direction.DOWN) {
            return ActionResultType.FAIL;
        } else {
            World world = context.getWorld();
            BlockItemUseContext blockitemusecontext = new BlockItemUseContext(context);
            BlockPos blockpos = blockitemusecontext.getPos();
            ItemStack itemstack = context.getItem();
            Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockpos);
            AxisAlignedBB axisalignedbb = StatueRegistry.PLAYER_STATUE_ENTITY.get().getSize().func_242285_a(vector3d.getX(), vector3d.getY(), vector3d.getZ());
            if (world.hasNoCollisions((Entity)null, axisalignedbb, (entity) -> {
                return true;
            }) && world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb).isEmpty()) {
                 if (world instanceof ServerWorld) {
                    ServerWorld serverworld = (ServerWorld)world;
                    PlayerStatueEntity playerStatueEntity = StatueRegistry.PLAYER_STATUE_ENTITY.get().create(serverworld, itemstack.getTag(), context.getPlayer() != null ? context.getPlayer().getName() : new StringTextComponent("player statue"), context.getPlayer(), blockpos, SpawnReason.SPAWN_EGG, true, true);
                    if (playerStatueEntity == null) {
                        return ActionResultType.FAIL;
                    }

                    serverworld.func_242417_l(playerStatueEntity);
                    float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlacementYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    playerStatueEntity.setLocationAndAngles(playerStatueEntity.getPosX(), playerStatueEntity.getPosY(), playerStatueEntity.getPosZ(), f, 0.0F);
                    this.applyRandomRotations(playerStatueEntity, world.rand);
                    if(context.getPlayer() != null) {
                        playerStatueEntity.setGameProfile(context.getPlayer().getGameProfile());
                    } else {
                        playerStatueEntity.setGameProfile(new GameProfile((UUID)null, "steve"));
                    }
                    world.addEntity(playerStatueEntity);
                    world.playSound((PlayerEntity)null, playerStatueEntity.getPosX(), playerStatueEntity.getPosY(), playerStatueEntity.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
                }

                itemstack.shrink(1);
                return ActionResultType.func_233537_a_(world.isRemote);
            } else {
                return ActionResultType.FAIL;
            }
        }
    }

    public static void applyRandomRotations(PlayerStatueEntity playerStatueEntity, Random rand) {
        Rotations rotations = playerStatueEntity.getHeadRotation();
        float f = rand.nextFloat() * 5.0F;
        float f1 = rand.nextFloat() * 20.0F - 10.0F;
        Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
        playerStatueEntity.setHeadRotation(rotations1);
        rotations = playerStatueEntity.getBodyRotation();
        f = rand.nextFloat() * 10.0F - 5.0F;
        rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
        playerStatueEntity.setBodyRotation(rotations1);
    }
}
