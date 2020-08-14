package com.shynieke.statues.handlers;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpecialHandler {
    @SubscribeEvent
    public void playerTick(PlayerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START)
            return;

        if(!event.player.world.isRemote)
        {
            final PlayerEntity player = event.player;
            World world = player.world;
            BlockPos pos = player.getPosition();
            AxisAlignedBB hitbox = new AxisAlignedBB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f)
                    .expand(-3, -3, -3).expand(3, 3, 3);

            for(ItemEntity entity : player.world.getEntitiesWithinAABB(ItemEntity.class, hitbox)) {
                if(entity instanceof ItemEntity) {
                    ItemEntity itemE = (ItemEntity) entity;

                    if (itemE.getItem().getItem().equals(Items.DIAMOND)) {
                        AxisAlignedBB bb = itemE.getBoundingBox().contract(0.1, 0.1, 0.1);
                        BlockPos lavaPos = itemE.getPosition();

                        Boolean lavaFound = false;
                        Boolean requirementsFound = false;

                        if (world.getBlockState(itemE.getPosition()).getBlock() == Blocks.LAVA) {
                            lavaFound = true;
                            lavaPos = itemE.getPosition();
                        } else if (world.getBlockState(itemE.getPosition().down()).getBlock() == Blocks.LAVA) {
                            lavaFound = true;
                            lavaPos = itemE.getPosition().down();
                        } else {
                            lavaFound = false;
                        }

                        CampfireData data = properStatuesFound(world, lavaPos.up(), StatueRegistry.PLAYER_STATUE.get(), StatueRegistry.CREEPER_STATUE.get());

                        if (data.getBool() && lavaFound) {
                            requirementsFound = true;
                        } else {
                            requirementsFound = false;
                        }

                        if (requirementsFound) {
                            world.setBlockState(lavaPos, Blocks.AIR.getDefaultState());
                            world.setBlockState(data.getPos1(), Blocks.AIR.getDefaultState());
                            world.setBlockState(data.getPos2(), Blocks.AIR.getDefaultState());

                            world.setBlockState(lavaPos, StatueRegistry.CAMPFIRE_STATUE.get().getDefaultState().with(AbstractStatueBase.INTERACTIVE, false));
                            world.addParticle(ParticleTypes.FLAME, lavaPos.down().getX(), lavaPos.down().getY(), lavaPos.down().getZ(), 0.0D, 0.0D, 0.0D);
                            itemE.remove();
                        }
                    }
                }
            }
        }
    }

    public CampfireData properStatuesFound(World worldIn, BlockPos pos, Block block, Block block1)
    {
        CampfireData data = new CampfireData(false, pos, pos);

        BlockState northBlock = worldIn.getBlockState(pos.offset(Direction.NORTH));
        BlockState eastBlock = worldIn.getBlockState(pos.offset(Direction.EAST));
        BlockState southBlock = worldIn.getBlockState(pos.offset(Direction.SOUTH));
        BlockState westBlock = worldIn.getBlockState(pos.offset(Direction.WEST));

        if(isValidBlock(northBlock, block) && isValidBlock(southBlock, block1))
        {
            data = new CampfireData(true, pos.offset(Direction.NORTH), pos.offset(Direction.SOUTH));
        } else if(isValidBlock(northBlock, block1) && isValidBlock(southBlock, block))
        {
            data = new CampfireData(true, pos.offset(Direction.NORTH), pos.offset(Direction.SOUTH));
        } else if(isValidBlock(westBlock, block) && isValidBlock(eastBlock, block1))
        {
            data = new CampfireData(true, pos.offset(Direction.WEST), pos.offset(Direction.EAST));
        } else if(isValidBlock(westBlock, block1) && isValidBlock(eastBlock, block))
        {
            data = new CampfireData(true, pos.offset(Direction.WEST), pos.offset(Direction.EAST));
        }

        return data;
    }

    public boolean isValidBlock(BlockState state, Block block) {
        if(state.getBlock() == block && state.getBlock() instanceof AbstractStatueBase) {
            return !state.get(AbstractStatueBase.INTERACTIVE);
        } else {
            return state.getBlock() instanceof PlayerStatueBlock;
        }
    }

    public class CampfireData
    {
        private final Boolean bool;
        private final BlockPos pos1;
        private final BlockPos pos2;

        public CampfireData(boolean bool, BlockPos pos1, BlockPos pos2) {
            this.bool = bool;
            this.pos1 = pos1;
            this.pos2 = pos2;
        }

        public Boolean getBool() {
            return bool;
        }

        public BlockPos getPos1() {
            return pos1;
        }

        public BlockPos getPos2() {
            return pos2;
        }
    }
}
