package com.svennieke.statues.init;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
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

	@SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
    {
		Statues.LOGGER.debug("Registering Statues Sounds");
        wasteland_hello = registerSound(evt.getRegistry(), "wasteland.hello");
        wasteland_onwards = registerSound(evt.getRegistry(), "wasteland.onwards");
        wasteland_tea = registerSound(evt.getRegistry(), "wasteland.tea");

        campfire_hello = registerSound(evt.getRegistry(), "campfire.hello");
        campfire_hello2 = registerSound(evt.getRegistry(), "campfire.hello2");
        campfire_hello3 = registerSound(evt.getRegistry(), "campfire.hello3");
        campfire_hello4 = registerSound(evt.getRegistry(), "campfire.hello4");
        campfire_hello5 = registerSound(evt.getRegistry(), "campfire.hello5");
        campfire_hello6 = registerSound(evt.getRegistry(), "campfire.hello6");
        campfire_hello7 = registerSound(evt.getRegistry(), "campfire.hello7");
        campfire_hello8 = registerSound(evt.getRegistry(), "campfire.hello8");
        campfire_hello9 = registerSound(evt.getRegistry(), "campfire.hello9");
        campfire_hello_random = registerSound(evt.getRegistry(), "campfire.hello.random");
        campfire_greetings = registerSound(evt.getRegistry(), "campfire.greetings");
        campfire_greetings2 = registerSound(evt.getRegistry(), "campfire.greetings2");
        campfire_greetings3 = registerSound(evt.getRegistry(), "campfire.greetings3");
        campfire_greetings_random = registerSound(evt.getRegistry(), "campfire.greetings.random");
        campfire_bye = registerSound(evt.getRegistry(), "campfire.bye");
        campfire_bye2 = registerSound(evt.getRegistry(), "campfire.bye2");
        campfire_bye_random = registerSound(evt.getRegistry(), "campfire.bye.random");
        campfire_cold = registerSound(evt.getRegistry(), "campfire.cold");
        campfire_cold2 = registerSound(evt.getRegistry(), "campfire.cold2");
        campfire_cold3 = registerSound(evt.getRegistry(), "campfire.cold3");
        campfire_cold_random = registerSound(evt.getRegistry(), "campfire.cold.random");
        campfire_snacks = registerSound(evt.getRegistry(), "campfire.snacks");
        campfire_snacks2 = registerSound(evt.getRegistry(), "campfire.snacks2");
        campfire_snacks3 = registerSound(evt.getRegistry(), "campfire.snacks3");
        campfire_snacks4 = registerSound(evt.getRegistry(), "campfire.snacks4");
        campfire_snacks_random = registerSound(evt.getRegistry(), "campfire.snacks.random");
        campfire_marshmallow = registerSound(evt.getRegistry(), "campfire.marshmallow");
        campfire_marshmallow2 = registerSound(evt.getRegistry(), "campfire.marshmallow2");
        campfire_marshmallow3 = registerSound(evt.getRegistry(), "campfire.marshmallow3");
        campfire_marshmallow4 = registerSound(evt.getRegistry(), "campfire.marshmallow4");
        campfire_marshmallow5 = registerSound(evt.getRegistry(), "campfire.marshmallow5");
        campfire_marshmallow6 = registerSound(evt.getRegistry(), "campfire.marshmallow6");
        campfire_marshmallow7 = registerSound(evt.getRegistry(), "campfire.marshmallow7");
        campfire_marshmallow8 = registerSound(evt.getRegistry(), "campfire.marshmallow8");
        campfire_marshmallow9 = registerSound(evt.getRegistry(), "campfire.marshmallow9");
        campfire_marshmallow_random = registerSound(evt.getRegistry(), "campfire.marshmallow.random");
    }

    private static SoundEvent registerSound(IForgeRegistry<SoundEvent> registry, String soundName)
    {
        ResourceLocation name = new ResourceLocation(Reference.MOD_ID, soundName);
        SoundEvent sound = new SoundEvent(name).setRegistryName(name);
        registry.register(sound);
        return sound;
    }
}
