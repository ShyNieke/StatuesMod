package com.svennieke.statues.handlers;

import com.svennieke.statues.init.StatueBlocks;
import net.minecraft.block.Block;
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
                    .expand(-5, -5, -5).expand(5, 5, 5);

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

                        CampfireData data = properStatuesFound(world, lavaPos.up(), StatueBlocks.player_statue, StatueBlocks.creeper_statue);

                        if (data.getBool() && lavaFound) {
                            requirementsFound = true;
                        } else {
                            requirementsFound = false;
                        }

                        if (requirementsFound) {
                            world.setBlockState(lavaPos, Blocks.AIR.getDefaultState());
                            world.setBlockState(data.getPos1(), Blocks.AIR.getDefaultState());
                            world.setBlockState(data.getPos2(), Blocks.AIR.getDefaultState());

                            world.setBlockState(lavaPos, StatueBlocks.campfire_statue.getDefaultState());
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

        if(worldIn.getBlockState(pos.offset(Direction.NORTH)).getBlock() == block && worldIn.getBlockState(pos.offset(Direction.SOUTH)).getBlock() == block1)
        {
            data = new CampfireData(true, pos.offset(Direction.NORTH), pos.offset(Direction.SOUTH));
        }
        if(worldIn.getBlockState(pos.offset(Direction.NORTH)).getBlock() == block1 && worldIn.getBlockState(pos.offset(Direction.SOUTH)).getBlock() == block)
        {
            data = new CampfireData(true, pos.offset(Direction.NORTH), pos.offset(Direction.SOUTH));
        }
        if(worldIn.getBlockState(pos.offset(Direction.WEST)).getBlock() == block && worldIn.getBlockState(pos.offset(Direction.EAST)).getBlock() == block1)
        {
            data = new CampfireData(true, pos.offset(Direction.WEST), pos.offset(Direction.EAST));
        }
        if(worldIn.getBlockState(pos.offset(Direction.WEST)).getBlock() == block1 && worldIn.getBlockState(pos.offset(Direction.EAST)).getBlock() == block)
        {
            data = new CampfireData(true, pos.offset(Direction.WEST), pos.offset(Direction.EAST));
        }

        return data;
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
