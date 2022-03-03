package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StatueSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

	public static final RegistryObject<SoundEvent> WASTELAND_HELLO = SOUND_EVENTS.register("wasteland.hello", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "wasteland.hello")));
	public static final RegistryObject<SoundEvent> WASTELAND_ONWARDS = SOUND_EVENTS.register("wasteland.onwards", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "wasteland.onwards")));
	public static final RegistryObject<SoundEvent> WASTELAND_TEA = SOUND_EVENTS.register("wasteland.tea", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "wasteland.tea")));

	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO = SOUND_EVENTS.register("campfire.hello", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_2 = SOUND_EVENTS.register("campfire.hello2", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello2")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_3 = SOUND_EVENTS.register("campfire.hello3", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello3")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_4 = SOUND_EVENTS.register("campfire.hello4", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello4")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_5 = SOUND_EVENTS.register("campfire.hello5", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello5")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_6 = SOUND_EVENTS.register("campfire.hello6", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello6")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_7 = SOUND_EVENTS.register("campfire.hello7", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello7")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_8 = SOUND_EVENTS.register("campfire.hello8", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello8")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_9 = SOUND_EVENTS.register("campfire.hello9", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello9")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_HELLO_RANDOM = SOUND_EVENTS.register("campfire.hello.random", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.hello.random")));

	public static final RegistryObject<SoundEvent> CAMPFIRE_GREETINGS = SOUND_EVENTS.register("campfire.greetings", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.greetings")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_GREETINGS_2 = SOUND_EVENTS.register("campfire.greetings2", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.greetings2")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_GREETINGS_3 = SOUND_EVENTS.register("campfire.greetings3", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.greetings3")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_GREETINGS_RANDOM = SOUND_EVENTS.register("campfire.greetings.random", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.greetings.random")));

	public static final RegistryObject<SoundEvent> CAMPFIRE_BYE = SOUND_EVENTS.register("campfire.bye", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.bye")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_BYE_2 = SOUND_EVENTS.register("campfire.bye2", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.bye2")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_BYE_RANDOM = SOUND_EVENTS.register("campfire.bye.random", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.bye.random")));

	public static final RegistryObject<SoundEvent> CAMPFIRE_COLD = SOUND_EVENTS.register("campfire.cold", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.cold")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_COLD_2 = SOUND_EVENTS.register("campfire.cold2", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.cold2")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_COLD_3 = SOUND_EVENTS.register("campfire.cold3", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.cold3")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_COLD_RANDOM = SOUND_EVENTS.register("campfire.cold.random", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.cold.random")));

	public static final RegistryObject<SoundEvent> CAMPFIRE_SNACKS = SOUND_EVENTS.register("campfire.snacks", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.snacks")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_SNACKS_2 = SOUND_EVENTS.register("campfire.snacks2", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.snacks2")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_SNACKS_3 = SOUND_EVENTS.register("campfire.snacks3", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.snacks3")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_SNACKS_4 = SOUND_EVENTS.register("campfire.snacks4", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.snacks4")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_SNACKS_RANDOM = SOUND_EVENTS.register("campfire.snacks.random", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.snacks.random")));

	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW = SOUND_EVENTS.register("campfire.marshmallow", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_2 = SOUND_EVENTS.register("campfire.marshmallow2", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow2")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_3 = SOUND_EVENTS.register("campfire.marshmallow3", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow3")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_4 = SOUND_EVENTS.register("campfire.marshmallow4", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow4")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_5 = SOUND_EVENTS.register("campfire.marshmallow5", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow5")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_6 = SOUND_EVENTS.register("campfire.marshmallow6", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow6")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_7 = SOUND_EVENTS.register("campfire.marshmallow7", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow7")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_8 = SOUND_EVENTS.register("campfire.marshmallow8", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow8")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_9 = SOUND_EVENTS.register("campfire.marshmallow9", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow9")));
	public static final RegistryObject<SoundEvent> CAMPFIRE_MARSHMALLOW_RANDOM = SOUND_EVENTS.register("campfire.marshmallow.random", () ->
			new SoundEvent(new ResourceLocation(Reference.MOD_ID, "campfire.marshmallow.random")));
}
