package com.svennieke.statues.init;

import com.svennieke.statues.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StatuesSounds {

	public static SoundEvent wasteland_hello;
	public static SoundEvent wasteland_onwards;
	public static SoundEvent wasteland_tea;
	
	public static void registerSounds() 
	{
		wasteland_hello = registerSound("wasteland.hello");
		wasteland_onwards = registerSound("wasteland.onwards");
		wasteland_tea = registerSound("wasteland.tea");
	}
	
	private static SoundEvent registerSound(String soundName)
	{
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, soundName);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(location);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
