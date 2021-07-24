package com.shynieke.statues.items;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.UUID;

public class PlayerStatueSpawnItem extends Item {

    public PlayerStatueSpawnItem(Properties builder) {
        super(builder);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Direction direction = context.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level world = context.getLevel();
            BlockHitResult blockitemusecontext = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
            BlockPos blockpos = blockitemusecontext.getBlockPos();
            ItemStack itemstack = context.getItemInHand();
            Vec3 vector3d = Vec3.atBottomCenterOf(blockpos);
            AABB axisalignedbb = StatueRegistry.PLAYER_STATUE_ENTITY.get().getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
            if (world.noCollision((Entity)null, axisalignedbb, (entity) -> true) &&
                    world.getEntities((Entity)null, axisalignedbb).isEmpty()) {
                 if (world instanceof ServerLevel) {
                    ServerLevel serverworld = (ServerLevel)world;
                    PlayerStatue playerStatueEntity = StatueRegistry.PLAYER_STATUE_ENTITY.get().create(serverworld, itemstack.getTag(), context.getPlayer() != null ?
                            context.getPlayer().getName() : new TextComponent("player statue"), context.getPlayer(), blockpos, MobSpawnType.SPAWN_EGG,
                            true, true);
                    if (playerStatueEntity == null) {
                        return InteractionResult.FAIL;
                    }

                    serverworld.addFreshEntityWithPassengers(playerStatueEntity);
                    float f = (float) Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    playerStatueEntity.moveTo(playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), f, 0.0F);
                    applyRandomRotations(playerStatueEntity, world.random);
                    if(context.getPlayer() != null) {
                        playerStatueEntity.setGameProfile(context.getPlayer().getGameProfile());
                    } else {
                        playerStatueEntity.setGameProfile(new GameProfile((UUID)null, "steve"));
                    }
                    world.addFreshEntity(playerStatueEntity);
                    world.playSound((Player) null, playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), SoundEvents.ARMOR_STAND_PLACE,
                            SoundSource.BLOCKS, 0.75F, 0.8F);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

    public static void applyRandomRotations(PlayerStatue playerStatueEntity, Random rand) {
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
