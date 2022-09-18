package com.shynieke.statues.util;

import javax.annotation.Nonnull;
import java.util.UUID;

public class SkinUtil {
	public static boolean isSlimSkin(@Nonnull UUID playerUUID) {
		return (playerUUID.hashCode() & 1) == 1;
	}
}
