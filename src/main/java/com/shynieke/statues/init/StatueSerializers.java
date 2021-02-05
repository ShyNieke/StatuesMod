package com.shynieke.statues.init;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;

import javax.annotation.Nonnull;
import java.util.Optional;

public class StatueSerializers {
    public static final IDataSerializer<Optional<GameProfile>> OPTIONAL_GAME_PROFILE = new IDataSerializer<Optional<GameProfile>>() {
        @Override
        public void write(@Nonnull PacketBuffer packetBuffer, @Nonnull Optional<GameProfile> gameProfile) {
            if (gameProfile.isPresent()) {
                packetBuffer.writeBoolean(true);
                packetBuffer.writeCompoundTag(NBTUtil.writeGameProfile(new CompoundNBT(), gameProfile.get()));
            }
            else {
                packetBuffer.writeBoolean(false);
            }
        }

        @Override
        @Nonnull
        public Optional<GameProfile> read(@Nonnull PacketBuffer packetBuffer) {
            return packetBuffer.readBoolean() ? Optional.of(NBTUtil.readGameProfile(packetBuffer.readCompoundTag())) : Optional.empty();
        }

        @Override
        @Nonnull
        public Optional<GameProfile> copyValue(@Nonnull Optional<GameProfile> gameProfile) {
            return gameProfile;
        }
    };
}
