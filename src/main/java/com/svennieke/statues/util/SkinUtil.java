package com.svennieke.statues.util;

import java.io.BufferedReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.Resources;
import com.google.gson.stream.JsonReader;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.svennieke.statues.Statues;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkinUtil {
    public static BiMap<String, UUID> PROFILE_CACHE = HashBiMap.<String, UUID> create();
    
    public static UUID getUUIDFromName (String username) {

        if (PROFILE_CACHE.containsKey(username)) {
            return PROFILE_CACHE.get(username);
        }

        UUID uuid = null;

        try {

            final BufferedReader reader = Resources.asCharSource(new URL("https://api.mojang.com/users/profiles/minecraft/" + username), StandardCharsets.UTF_8).openBufferedStream();
            final JsonReader json = new JsonReader(reader);

            json.beginObject();

            while (json.hasNext()) {
                if ("id".equals(json.nextName())) {
                    uuid = fixUUID(json.nextString());
                }
                else {
                    json.skipValue();
                }
            }

            json.endObject();
            json.close();
            reader.close();
        }

        catch (final Exception exception) {
        	Statues.logger.info("Could not get name for {}", username);
        }

        return uuid;
    }

    public static UUID fixUUID (String uuidString) {

        return uuidString.length() != 32 ? null : UUID.fromString(uuidString.substring(0, 8) + "-" + uuidString.substring(8, 12) + "-" + uuidString.substring(12, 16) + "-" + uuidString.substring(16, 20) + "-" + uuidString.substring(20, 32));
    }
    
    public static boolean isSlimSkin(UUID playerUUID)
    {
        return (playerUUID.hashCode() & 1) == 1;
    }
    
    public static final Map<String, ResourceLocation> SKIN_CACHE = new HashMap<>();

	public static ResourceLocation getSkin(GameProfile profile) {
		ResourceLocation resourcelocation = SKIN_CACHE.get(profile.getName());
		
		if(resourcelocation == null)
		{
			Minecraft minecraft = Minecraft.getMinecraft();
	        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

	        if (map.containsKey(Type.SKIN))
	        {
	            resourcelocation = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
				SKIN_CACHE.put(profile.getName(), resourcelocation);
	        }
	        else
	        {
	            UUID uuid = EntityPlayer.getUUID(profile);
	            resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
	        }
		}
        
        return resourcelocation;
	}
}
