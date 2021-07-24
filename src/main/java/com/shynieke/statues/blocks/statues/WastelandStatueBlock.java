package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueSounds;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WastelandStatueBlock extends AbstractStatueBase {

	public WastelandStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		Pig pig = new Pig(EntityType.PIG, worldIn);
		pig.setCustomName(new TextComponent("Wasteland Pig"));
		tile.summonMob(pig);
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return RANDOM.nextBoolean() ? StatueSounds.WASTELAND_HELLO.get() : StatueSounds.WASTELAND_ONWARDS.get();
	}

	@Override
	public String getLootName() {
		return "wasteland_pig";
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}
}
