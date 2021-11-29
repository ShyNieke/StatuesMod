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
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.MAIN_COLOR = nbt.getInt("MainColor");
        this.SECONDARY_COLOR = nbt.getInt("SecondaryColor");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("MainColor", MAIN_COLOR);
        compound.putInt("SecondaryColor", SECONDARY_COLOR);

        return super.save(compound);
    }

    public void scrambleColors() {
        if(level != null) {
            this.MAIN_COLOR = level.random.nextInt(16);
            this.SECONDARY_COLOR = level.random.nextInt(16);
        }
        setChanged();
    }

    public int getMainColor() {
        return MAIN_COLOR;
    }

    public int getSecondaryColor() {
        return SECONDARY_COLOR;
    }
}
