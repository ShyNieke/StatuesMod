package com.shynieke.statues.fakeplayer;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Reference;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.function.Function;

public class StatueFakePlayer extends FakePlayer {
	public StatueFakePlayer(ServerLevel serverLevel, GameProfile profile) {
		super(serverLevel, profile);
	}

	private static WeakReference<StatueFakePlayer> INSTANCE;

	public static <R> R useFakePlayer(ServerLevel serverLevel, Function<StatueFakePlayer, R> fakePlayerConsumer) {
		StatueFakePlayer actual = INSTANCE == null ? null : INSTANCE.get();
		if (actual == null) {
			actual = new StatueFakePlayer(serverLevel, Reference.GAME_PROFILE);
			INSTANCE = new WeakReference<>(actual);
		}
		StatueFakePlayer player = actual;
		player.setServerLevel(serverLevel);
		R result = fakePlayerConsumer.apply(player);

		//don't keep reference to the World, note we set it to the overworld to avoid any potential null pointers
		player.setServerLevel(serverLevel.getServer().overworld());
		return result;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		return false;
	}

	public static void unload(ServerLevel serverLevel) {
		StatueFakePlayer actual = INSTANCE == null ? null : INSTANCE.get();
		if (actual != null && actual.level() == serverLevel) {
			//don't keep reference to the World, note we set it to the overworld to avoid any potential null pointers
			actual.setServerLevel(serverLevel.getServer().overworld());
		}
	}

	@Override
	public boolean isAlliedTo(Team team) {
		return false;
	}

	@Nullable
	@Override
	public Team getTeam() {
		return null;
	}
}
