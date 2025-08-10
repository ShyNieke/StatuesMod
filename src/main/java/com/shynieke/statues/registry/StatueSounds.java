package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StatueSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Reference.MOD_ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> WASTELAND_HELLO = SOUND_EVENTS.register("wasteland.hello", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("wasteland.hello")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WASTELAND_ONWARDS = SOUND_EVENTS.register("wasteland.onwards", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("wasteland.onwards")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WASTELAND_TEA = SOUND_EVENTS.register("wasteland.tea", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("wasteland.tea")));

	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO = SOUND_EVENTS.register("campfire.hello", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_2 = SOUND_EVENTS.register("campfire.hello2", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_3 = SOUND_EVENTS.register("campfire.hello3", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello3")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_4 = SOUND_EVENTS.register("campfire.hello4", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello4")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_5 = SOUND_EVENTS.register("campfire.hello5", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello5")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_6 = SOUND_EVENTS.register("campfire.hello6", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello6")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_7 = SOUND_EVENTS.register("campfire.hello7", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello7")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_8 = SOUND_EVENTS.register("campfire.hello8", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello8")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_9 = SOUND_EVENTS.register("campfire.hello9", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello9")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_HELLO_RANDOM = SOUND_EVENTS.register("campfire.hello.random", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.hello.random")));

	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_GREETINGS = SOUND_EVENTS.register("campfire.greetings", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.greetings")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_GREETINGS_2 = SOUND_EVENTS.register("campfire.greetings2", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.greetings2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_GREETINGS_3 = SOUND_EVENTS.register("campfire.greetings3", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.greetings3")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_GREETINGS_RANDOM = SOUND_EVENTS.register("campfire.greetings.random", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.greetings.random")));

	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_BYE = SOUND_EVENTS.register("campfire.bye", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.bye")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_BYE_2 = SOUND_EVENTS.register("campfire.bye2", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.bye2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_BYE_RANDOM = SOUND_EVENTS.register("campfire.bye.random", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.bye.random")));

	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_COLD = SOUND_EVENTS.register("campfire.cold", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.cold")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_COLD_2 = SOUND_EVENTS.register("campfire.cold2", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.cold2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_COLD_3 = SOUND_EVENTS.register("campfire.cold3", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.cold3")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_COLD_RANDOM = SOUND_EVENTS.register("campfire.cold.random", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.cold.random")));

	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_SNACKS = SOUND_EVENTS.register("campfire.snacks", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.snacks")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_SNACKS_2 = SOUND_EVENTS.register("campfire.snacks2", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.snacks2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_SNACKS_3 = SOUND_EVENTS.register("campfire.snacks3", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.snacks3")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_SNACKS_4 = SOUND_EVENTS.register("campfire.snacks4", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.snacks4")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_SNACKS_RANDOM = SOUND_EVENTS.register("campfire.snacks.random", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.snacks.random")));

	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW = SOUND_EVENTS.register("campfire.marshmallow", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_2 = SOUND_EVENTS.register("campfire.marshmallow2", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_3 = SOUND_EVENTS.register("campfire.marshmallow3", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow3")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_4 = SOUND_EVENTS.register("campfire.marshmallow4", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow4")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_5 = SOUND_EVENTS.register("campfire.marshmallow5", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow5")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_6 = SOUND_EVENTS.register("campfire.marshmallow6", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow6")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_7 = SOUND_EVENTS.register("campfire.marshmallow7", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow7")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_8 = SOUND_EVENTS.register("campfire.marshmallow8", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow8")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_9 = SOUND_EVENTS.register("campfire.marshmallow9", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow9")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAMPFIRE_MARSHMALLOW_RANDOM = SOUND_EVENTS.register("campfire.marshmallow.random", () ->
			SoundEvent.createVariableRangeEvent(Reference.modLoc("campfire.marshmallow.random")));
}
