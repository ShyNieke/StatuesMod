package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.JukeboxSong;

public class StatueJukeboxSongs {
	public static final ResourceKey<JukeboxSong> CREDITS = create("credits");

	private static ResourceKey<JukeboxSong> create(String path) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, Reference.modLoc(path));
	}

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, CREDITS, SoundEvents.MUSIC_CREDITS, 12060, 0);
	}

	private static void register(
			BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> songResourceKey,
			Holder.Reference<SoundEvent> soundEventReference, int p_350314_, int p_350919_
	) {
		context.register(
				songResourceKey,
				new JukeboxSong(soundEventReference, Component.translatable(Util.makeDescriptionId("jukebox_song", songResourceKey.location())), (float) p_350314_, p_350919_)
		);
	}
}