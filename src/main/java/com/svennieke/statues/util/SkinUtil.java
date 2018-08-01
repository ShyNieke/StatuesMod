package com.svennieke.statues.util;

import java.util.UUID;

public class SkinUtil {
	public static boolean isSlimSkin(UUID playerUUID)
    {
        return (playerUUID.hashCode() & 1) == 1;
    }
}
