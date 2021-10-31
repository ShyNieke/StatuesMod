package com.shynieke.statues.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinUtil {

    public static class SkinRenderData {
        public ResourceLocation skinLocation;
        public boolean isSlim = false;

        public SkinRenderData(GameProfile playerProfile) {
            UUID tempUUID = Player.createPlayerUUID(playerProfile);
            skinLocation = DefaultPlayerSkin.getDefaultSkin(tempUUID);
            isSlim = DefaultPlayerSkin.getSkinModelName(tempUUID).equals("slim");
        }
    }

    private static final Map<UUID, SkinRenderData> SKINRENDER_CACHE = new HashMap<>();

    /**
     * Will return defaults until data is retrieved. And will create a request if none has been done before.
     *
     * As it is a reference for things like entities and tile entities you can keep a copy.
     *
     * @param playerProfile
     * @return
     */
    public static SkinRenderData getSkinRenderData(GameProfile playerProfile) {
        UUID playerId = playerProfile.getId();
        if(!SKINRENDER_CACHE.containsKey(playerId)) {
            SkinRenderData newRenderData = new SkinRenderData(playerProfile);
            SKINRENDER_CACHE.put(playerId, newRenderData);
            Minecraft.getInstance().getSkinManager().registerSkins(playerProfile, (textureType, textureLocation, profileTexture) -> {
                if (textureType.equals(MinecraftProfileTexture.Type.SKIN))  {
                    String metadata = profileTexture.getMetadata("model");
                    newRenderData.skinLocation = textureLocation;
                    newRenderData.isSlim = metadata != null && metadata.equals("slim");
                }
            }, true);
            return newRenderData;
        } else {
            return SKINRENDER_CACHE.get(playerId);
        }
    }

    public static boolean isSlimSkin(UUID playerUUID) {
        return (playerUUID.hashCode() & 1) == 1;
    }
}
