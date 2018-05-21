package com.svennieke.statues.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.authlib.GameProfile;
import com.svennieke.statues.Statues;

import net.minecraft.tileentity.TileEntitySkull;

public class SkinUtil {
    public static BiMap<String, UUID> UUID_CACHE = HashBiMap.<String, UUID> create();
    
    public static UUID getUUIDFromUsername(String username)
	{
    	if (UUID_CACHE.containsKey(username)) 
            return UUID_CACHE.get(username);

    	UUID uuid = null;

		try
		{
			URL u = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			URLConnection conn = u.openConnection();
			InputStream in = conn.getInputStream();
			byte[] id = new byte[7];
			in.read(id);
			byte[] uuidBuf = new byte[32];
			in.read(uuidBuf);
			in.close();
			UUID properUUID = fixUUID(new String(uuidBuf));
			UUID_CACHE.put(username, properUUID);
			return properUUID;
		} 
		catch(Throwable err)
		{
        	Statues.logger.info("Failed to get UUID", err);
		}
		
		return UUID.randomUUID();
	}

    public static UUID fixUUID (String uuidString) {

        return uuidString.length() != 32 ? null : UUID.fromString(uuidString.substring(0, 8) + "-" + uuidString.substring(8, 12) + "-" + uuidString.substring(12, 16) + "-" + uuidString.substring(16, 20) + "-" + uuidString.substring(20, 32));
    }
    
    public static boolean isSlimSkin(UUID playerUUID)
    {
        return (playerUUID.hashCode() & 1) == 1;
    }
    
	private static final Map<UUID, GameProfile> GAMEPROFILE_CACHE = new HashMap<>();
	
	public static GameProfile getProfileFromUsername(String username)
	{
		UUID uid = getUUIDFromUsername(username);
		
		if(GAMEPROFILE_CACHE.get(uid) != null)
			return GAMEPROFILE_CACHE.get(uid);
		
		GameProfile profile = TileEntitySkull.updateGameprofile(new GameProfile(uid, username));
		GAMEPROFILE_CACHE.put(uid, profile);
		return profile;
	}
}
