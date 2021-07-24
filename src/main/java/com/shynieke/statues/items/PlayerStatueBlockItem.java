package com.shynieke.statues.items;

import com.shynieke.statues.client.render.PlayerTileInventoryRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class PlayerStatueBlockItem extends StatueBlockItem {

	public PlayerStatueBlockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			final BlockEntityWithoutLevelRenderer renderer = new PlayerTileInventoryRenderer();

			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return renderer;
			}
		});
	}
}
