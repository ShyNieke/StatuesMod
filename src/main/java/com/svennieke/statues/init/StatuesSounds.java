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
	
	@SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
    {
		Statues.LOGGER.debug("Registering Statues Sounds");
        wasteland_hello = registerSound(evt.getRegistry(), "wasteland.hello");
        wasteland_onwards = registerSound(evt.getRegistry(), "wasteland.onwards");
        wasteland_tea = registerSound(evt.getRegistry(), "wasteland.tea");
    }

    private static SoundEvent registerSound(IForgeRegistry<SoundEvent> registry, String soundName)
    {
        ResourceLocation name = new ResourceLocation(Reference.MOD_ID, soundName);
        SoundEvent sound = new SoundEvent(name).setRegistryName(name);
        registry.register(sound);
        return sound;
    }
}
