package com.shynieke.statues.registry;

import com.mojang.serialization.Codec;
import com.shynieke.statues.Reference;
import com.shynieke.statues.datacomponent.PlayerCompassData;
import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.datacomponent.StatueUpgrades;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StatueDataComponents {
	public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Reference.MOD_ID);

	public static final Supplier<DataComponentType<PlayerCompassData>> PLAYER_COMPASS_DATA = DATA_COMPONENT_TYPES.registerComponentType("player_compass_data", builder ->
			builder
					.persistent(PlayerCompassData.CODEC)
					.networkSynchronized(PlayerCompassData.STREAM_CODEC)
	);

	public static final Supplier<DataComponentType<Boolean>> UPGRADED = DATA_COMPONENT_TYPES.registerComponentType("upgraded", builder ->
			builder
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
	);

	public static final Supplier<DataComponentType<StatueStats>> STATS = DATA_COMPONENT_TYPES.registerComponentType("stats", builder ->
			builder
					.persistent(StatueStats.CODEC)
					.networkSynchronized(StatueStats.STREAM_CODEC)
					.cacheEncoding()
	);

	public static final Supplier<DataComponentType<StatueUpgrades>> UPGRADES = DATA_COMPONENT_TYPES.registerComponentType("upgrades", builder ->
			builder
					.persistent(StatueUpgrades.CODEC)
					.networkSynchronized(StatueUpgrades.STREAM_CODEC)
					.cacheEncoding()
	);

}
