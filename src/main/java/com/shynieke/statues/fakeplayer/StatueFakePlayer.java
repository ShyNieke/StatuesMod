package com.shynieke.statues.fakeplayer;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
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
	protected Vec3 pos = new Vec3(0, 0, 0);
	protected BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(0, 0, 0);

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

	public void setPos(double x, double y, double z) {
		if (pos.x != x || pos.y != y || pos.z != z) {
			pos = new Vec3(x, y, z);
			blockPos.set(Math.floor(x), Math.floor(y), Math.floor(z));
		}
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
	public Vec3 position() {
		return this.pos;
	}

	@Override
	public BlockPos blockPosition() {
		return this.blockPos;
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
