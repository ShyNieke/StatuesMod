package com.svennieke.statues.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public class SkinUtil {
	public static final Map<String, ResourceLocation> SKIN_CACHE = new HashMap<>();
	
	public static ResourceLocation getSkinTexture(String username)
	{
		ResourceLocation skinlocation = SKIN_CACHE.get(username);

		if (skinlocation == null)
		{
			skinlocation = AbstractClientPlayer.getLocationSkin(username);

			try
			{
				AbstractClientPlayer.getDownloadImageSkin(skinlocation, username);
				SKIN_CACHE.put(username, skinlocation);
			}
			catch (Exception except)
			{
				except.printStackTrace();
			}
		}

		return skinlocation;
	}
}
