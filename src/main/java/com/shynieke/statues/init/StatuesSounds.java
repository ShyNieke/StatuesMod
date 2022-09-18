package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StatuesSounds {

	public static SoundEvent wasteland_hello;
	public static SoundEvent wasteland_onwards;
	public static SoundEvent wasteland_tea;
	public static SoundEvent campfire_hello, campfire_hello2, campfire_hello3, campfire_hello4, campfire_hello5, campfire_hello6, campfire_hello7, campfire_hello8, campfire_hello9, campfire_hello_random;
	public static SoundEvent campfire_greetings, campfire_greetings2, campfire_greetings3, campfire_greetings_random;
	public static SoundEvent campfire_bye, campfire_bye2, campfire_bye_random;
	public static SoundEvent campfire_cold, campfire_cold2, campfire_cold3, campfire_cold_random;
	public static SoundEvent campfire_snacks, campfire_snacks2, campfire_snacks3, campfire_snacks4, campfire_snacks_random;
	public static SoundEvent campfire_marshmallow, campfire_marshmallow2, campfire_marshmallow3, campfire_marshmallow4, campfire_marshmallow5, campfire_marshmallow6, campfire_marshmallow7, campfire_marshmallow8, campfire_marshmallow9, campfire_marshmallow_random;


	public static void registerSounds() {
		wasteland_hello = registerSound("wasteland.hello");
		wasteland_onwards = registerSound("wasteland.onwards");
		wasteland_tea = registerSound("wasteland.tea");

		campfire_hello = registerSound("campfire.hello");
		campfire_hello2 = registerSound("campfire.hello2");
		campfire_hello3 = registerSound("campfire.hello3");
		campfire_hello4 = registerSound("campfire.hello4");
		campfire_hello5 = registerSound("campfire.hello5");
		campfire_hello6 = registerSound("campfire.hello6");
		campfire_hello7 = registerSound("campfire.hello7");
		campfire_hello8 = registerSound("campfire.hello8");
		campfire_hello9 = registerSound("campfire.hello9");
		campfire_hello_random = registerSound("campfire.hello.random");
		campfire_greetings = registerSound("campfire.greetings");
		campfire_greetings2 = registerSound("campfire.greetings2");
		campfire_greetings3 = registerSound("campfire.greetings3");
		campfire_greetings_random = registerSound("campfire.greetings.random");
		campfire_bye = registerSound("campfire.bye");
		campfire_bye2 = registerSound("campfire.bye2");
		campfire_bye_random = registerSound("campfire.bye.random");
		campfire_cold = registerSound("campfire.cold");
		campfire_cold2 = registerSound("campfire.cold2");
		campfire_cold3 = registerSound("campfire.cold3");
		campfire_cold_random = registerSound("campfire.cold.random");
		campfire_snacks = registerSound("campfire.snacks");
		campfire_snacks2 = registerSound("campfire.snacks2");
		campfire_snacks3 = registerSound("campfire.snacks3");
		campfire_snacks4 = registerSound("campfire.snacks4");
		campfire_snacks_random = registerSound("campfire.snacks.random");
		campfire_marshmallow = registerSound("campfire.marshmallow");
		campfire_marshmallow2 = registerSound("campfire.marshmallow2");
		campfire_marshmallow3 = registerSound("campfire.marshmallow3");
		campfire_marshmallow4 = registerSound("campfire.marshmallow4");
		campfire_marshmallow5 = registerSound("campfire.marshmallow5");
		campfire_marshmallow6 = registerSound("campfire.marshmallow6");
		campfire_marshmallow7 = registerSound("campfire.marshmallow7");
		campfire_marshmallow8 = registerSound("campfire.marshmallow8");
		campfire_marshmallow9 = registerSound("campfire.marshmallow9");
		campfire_marshmallow_random = registerSound("campfire.marshmallow.random");
	}

	private static SoundEvent registerSound(String soundName) {
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, soundName);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(location);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
