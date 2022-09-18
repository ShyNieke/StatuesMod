package com.shynieke.statues.renderer;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Statues;
import com.shynieke.statues.util.SkinUtil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class PlayerInventoryRender extends TileEntityItemStackRenderer {
	public static final ModelPlayer model = new ModelPlayer(0.03125F, false);
	protected TileEntityRendererDispatcher rendererDispatcher;

	@Override
	public void renderByItem(ItemStack stack) {
		GameProfile gameprofile = null;
		String stackName = stack.getDisplayName().toLowerCase(Locale.ROOT);
		boolean spaceFlag = stackName.contains(" ");
		boolean emptyFlag = stackName.isEmpty();
		if (!spaceFlag && !emptyFlag) {
			if (Statues.GAMEPROFILE_CACHE.containsKey(stackName))
				gameprofile = Statues.GAMEPROFILE_CACHE.get(stackName);

			if (stack.hasTagCompound() && gameprofile == null) {
				NBTTagCompound tagCompound = stack.getTagCompound();

				if (tagCompound.hasKey("PlayerProfile", 10)) {
					gameprofile = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag("PlayerProfile"));
				} else if (tagCompound.hasKey("PlayerProfile", 8) && !StringUtils.isBlank(tagCompound.getString("PlayerProfile"))) {
					GameProfile gameprofile1 = new GameProfile((UUID) null, tagCompound.getString("PlayerProfile"));
					gameprofile = TileEntitySkull.updateGameProfile(gameprofile1);
					tagCompound.removeTag("PlayerProfile");
					tagCompound.setTag("PlayerProfile", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
					Statues.GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
				}
			}

			if (gameprofile == null) {
				GameProfile gameprofile1 = new GameProfile((UUID) null, stackName);
				gameprofile = TileEntitySkull.updateGameProfile(gameprofile1);
				Statues.GAMEPROFILE_CACHE.put(gameprofile.getName(), gameprofile);
			}
		}

		if (TileEntitySkullRenderer.instance != null) {
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			boolean isSlim = gameprofile != null && gameprofile.isComplete() && SkinUtil.isSlimSkin(gameprofile.getId());
			PlayerStatueRenderer.instance.renderPlayer(0, 0, 0, gameprofile, isSlim, -1, EnumFacing.UP);
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
	}
}
