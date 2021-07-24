package com.shynieke.statues.init;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import javax.annotation.Nonnull;
import java.util.Optional;

public class StatueSerializers {
    public static final EntityDataSerializer<Optional<GameProfile>> OPTIONAL_GAME_PROFILE = new EntityDataSerializer<Optional<GameProfile>>() {
        @Override
        public void write(@Nonnull FriendlyByteBuf packetBuffer, @Nonnull Optional<GameProfile> gameProfile) {
            if (gameProfile.isPresent()) {
                packetBuffer.writeBoolean(true);
                packetBuffer.writeNbt(NbtUtils.writeGameProfile(new CompoundTag(), gameProfile.get()));
            }
            else {
                packetBuffer.writeBoolean(false);
            }
        }

        @Override
        @Nonnull
        public Optional<GameProfile> read(@Nonnull FriendlyByteBuf packetBuffer) {
            return packetBuffer.readBoolean() ? Optional.of(NbtUtils.readGameProfile(packetBuffer.readNbt())) : Optional.empty();
        }

        @Override
        @Nonnull
        public Optional<GameProfile> copy(@Nonnull Optional<GameProfile> gameProfile) {
            return gameProfile;
        }
    };
}
