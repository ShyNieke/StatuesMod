package com.shynieke.statues.init;

import com.google.common.collect.Lists;
import com.shynieke.statues.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatueSounds {
    private static List<SoundEvent> sounds = Lists.newArrayList();

    public static final SoundEvent wasteland_hello = createSound("wasteland.hello");
    public static final SoundEvent wasteland_onwards = createSound("wasteland.onwards");
    public static final SoundEvent wasteland_tea = createSound("wasteland.tea");

    public static final SoundEvent campfire_hello = createSound("campfire.hello");
    public static final SoundEvent campfire_hello2 = createSound("campfire.hello2");
    public static final SoundEvent campfire_hello3 = createSound("campfire.hello3");
    public static final SoundEvent campfire_hello4 = createSound("campfire.hello4");
    public static final SoundEvent campfire_hello5 = createSound("campfire.hello5");
    public static final SoundEvent campfire_hello6 = createSound("campfire.hello6");
    public static final SoundEvent campfire_hello7 = createSound("campfire.hello7");
    public static final SoundEvent campfire_hello8 = createSound("campfire.hello8");
    public static final SoundEvent campfire_hello9 = createSound("campfire.hello9");
    public static final SoundEvent campfire_hello_random = createSound("campfire.hello.random");
    public static final SoundEvent campfire_greetings = createSound("campfire.greetings");
    public static final SoundEvent campfire_greetings2 = createSound("campfire.greetings2");
    public static final SoundEvent campfire_greetings3 = createSound("campfire.greetings3");
    public static final SoundEvent campfire_greetings_random = createSound("campfire.greetings.random");
    public static final SoundEvent campfire_bye = createSound("campfire.bye");
    public static final SoundEvent campfire_bye2 = createSound("campfire.bye2");
    public static final SoundEvent campfire_bye_random = createSound("campfire.bye.random");
    public static final SoundEvent campfire_cold = createSound("campfire.cold");
    public static final SoundEvent campfire_cold2 = createSound("campfire.cold2");
    public static final SoundEvent campfire_cold3 = createSound("campfire.cold3");
    public static final SoundEvent campfire_cold_random = createSound("campfire.cold.random");
    public static final SoundEvent campfire_snacks = createSound("campfire.snacks");
    public static final SoundEvent campfire_snacks2 = createSound("campfire.snacks2");
    public static final SoundEvent campfire_snacks3 = createSound("campfire.snacks3");
    public static final SoundEvent campfire_snacks4 = createSound("campfire.snacks4");
    public static final SoundEvent campfire_snacks_random = createSound("campfire.snacks.random");
    public static final SoundEvent campfire_marshmallow = createSound("campfire.marshmallow");
    public static final SoundEvent campfire_marshmallow2 = createSound("campfire.marshmallow2");
    public static final SoundEvent campfire_marshmallow3 = createSound("campfire.marshmallow3");
    public static final SoundEvent campfire_marshmallow4 = createSound("campfire.marshmallow4");
    public static final SoundEvent campfire_marshmallow5 = createSound("campfire.marshmallow5");
    public static final SoundEvent campfire_marshmallow6 = createSound("campfire.marshmallow6");
    public static final SoundEvent campfire_marshmallow7 = createSound("campfire.marshmallow7");
    public static final SoundEvent campfire_marshmallow8 = createSound("campfire.marshmallow8");
    public static final SoundEvent campfire_marshmallow9 = createSound("campfire.marshmallow9");
    public static final SoundEvent campfire_marshmallow_random = createSound("campfire.marshmallow.random");

    private static SoundEvent createSound(String soundName)
    {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, soundName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        sounds.add(event);
        return event;
    }

    @SubscribeEvent
    public static void createSound(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(sounds.toArray(new SoundEvent[0]));
    }
}
