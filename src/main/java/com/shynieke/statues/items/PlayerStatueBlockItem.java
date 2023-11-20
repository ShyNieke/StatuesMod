package com.shynieke.statues.items;

import com.shynieke.statues.client.render.PlayerBEWLR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class PlayerStatueBlockItem extends StatueBlockItem {

	public PlayerStatueBlockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new PlayerBEWLR(new BlockEntityRendererProvider.Context(
						Minecraft.getInstance().getBlockEntityRenderDispatcher(),
						Minecraft.getInstance().getBlockRenderer(),
						Minecraft.getInstance().getItemRenderer(),
						Minecraft.getInstance().getEntityRenderDispatcher(),
						Minecraft.getInstance().getEntityModels(),
						Minecraft.getInstance().font
				));
			}
		});
	}
}
