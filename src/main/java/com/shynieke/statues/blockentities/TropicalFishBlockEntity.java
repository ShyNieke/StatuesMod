package com.shynieke.statues.blockentities;

import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class TropicalFishBlockEntity extends StatueBlockEntity {
	private int MAIN_COLOR;
	private int SECONDARY_COLOR;

	public TropicalFishBlockEntity(BlockPos pos, BlockState state) {
		super(StatueBlockEntities.TROPICAL_FISH.get(), pos, state);
		this.MAIN_COLOR = 0;
		this.SECONDARY_COLOR = 0;
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.MAIN_COLOR = input.getIntOr("MainColor", 0);
		this.SECONDARY_COLOR = input.getIntOr("SecondaryColor", 0);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		output.putInt("MainColor", MAIN_COLOR);
		output.putInt("SecondaryColor", SECONDARY_COLOR);
	}

	public void scrambleColors() {
		if (level != null) {
			this.MAIN_COLOR = level.getRandom().nextInt(16);
			this.SECONDARY_COLOR = level.getRandom().nextInt(16);
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
