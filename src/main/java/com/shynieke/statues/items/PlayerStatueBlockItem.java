package com.shynieke.statues.items;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.client.render.PlayerBEWLR;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class PlayerStatueBlockItem extends StatueBlockItem {

	public PlayerStatueBlockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public void verifyTagAfterLoad(@NotNull CompoundTag tag) {
		super.verifyTagAfterLoad(tag);
		if (tag.contains("PlayerProfile", 8) && !Util.isBlank(tag.getString("PlayerProfile"))) {
			GameProfile gameprofile = new GameProfile((UUID) null, tag.getString("PlayerProfile"));
			SkinUtil.updateGameProfile(gameprofile, (profile) ->
					tag.put("PlayerProfile", NbtUtils.writeGameProfile(new CompoundTag(), profile)));
		}
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
