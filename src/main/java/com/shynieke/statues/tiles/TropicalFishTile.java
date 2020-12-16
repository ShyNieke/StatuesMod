package com.shynieke.statues.tiles;

import com.shynieke.statues.init.StatueTiles;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class TropicalFishTile extends StatueTile {
    private int MAIN_COLOR;
    private int SECONDARY_COLOR;

    public TropicalFishTile() {
        super(StatueTiles.TROPICAL_TILE);
        this.MAIN_COLOR = 0;
        this.SECONDARY_COLOR = 0;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.MAIN_COLOR = nbt.getInt("MainColor");
        this.SECONDARY_COLOR = nbt.getInt("SecondaryColor");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("MainColor", MAIN_COLOR);
        compound.putInt("SecondaryColor", SECONDARY_COLOR);

        return super.write(compound);
    }

    public void scrambleColors() {
        this.MAIN_COLOR = world.rand.nextInt(16);
        this.SECONDARY_COLOR = world.rand.nextInt(16);
        markDirty();
    }

    public int getMainColor() {
        return MAIN_COLOR;
    }

    public int getSecondaryColor() {
        return SECONDARY_COLOR;
    }
}
