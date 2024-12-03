package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class StatueSoundProvider extends SoundDefinitionsProvider {

	public StatueSoundProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerSounds() {
		this.add(StatueSounds.WASTELAND_HELLO, definition()
				.subtitle(modSubtitle(StatueSounds.WASTELAND_HELLO.getId()))
				.with(sound(modLoc("wasteland/hello"))));
		this.add(StatueSounds.WASTELAND_ONWARDS, definition()
				.subtitle(modSubtitle(StatueSounds.WASTELAND_ONWARDS.getId()))
				.with(sound(modLoc("wasteland/onwards"))));
		this.add(StatueSounds.WASTELAND_TEA, definition()
				.subtitle(modSubtitle(StatueSounds.WASTELAND_TEA.getId()))
				.with(sound(modLoc("wasteland/tea"))));

		this.add(StatueSounds.CAMPFIRE_HELLO, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_2, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello2"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_3, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello3"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_4, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello4"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_5, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello5"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_6, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello6"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_7, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello7"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_8, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello8"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_9, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(sound(modLoc("campfire/hello9"))));
		this.add(StatueSounds.CAMPFIRE_HELLO_RANDOM, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_HELLO.getId()))
				.with(
						sound(modLoc("campfire/hello")),
						sound(modLoc("campfire/hello2")),
						sound(modLoc("campfire/hello3")),
						sound(modLoc("campfire/hello4")),
						sound(modLoc("campfire/hello5")),
						sound(modLoc("campfire/hello6")),
						sound(modLoc("campfire/hello7")),
						sound(modLoc("campfire/hello8")),
						sound(modLoc("campfire/hello9"))
				));
		this.add(StatueSounds.CAMPFIRE_GREETINGS, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_GREETINGS.getId()))
				.with(sound(modLoc("campfire/greetings"))));
		this.add(StatueSounds.CAMPFIRE_GREETINGS_2, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_GREETINGS.getId()))
				.with(sound(modLoc("campfire/greetings2"))));
		this.add(StatueSounds.CAMPFIRE_GREETINGS_3, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_GREETINGS.getId()))
				.with(sound(modLoc("campfire/greetings3"))));
		this.add(StatueSounds.CAMPFIRE_GREETINGS_RANDOM, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_GREETINGS.getId()))
				.with(
						sound(modLoc("campfire/greetings")),
						sound(modLoc("campfire/greetings2")),
						sound(modLoc("campfire/greetings3"))
				));
		this.add(StatueSounds.CAMPFIRE_BYE, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_BYE.getId()))
				.with(sound(modLoc("campfire/bye"))));
		this.add(StatueSounds.CAMPFIRE_BYE_2, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_BYE.getId()))
				.with(sound(modLoc("campfire/bye2"))));
		this.add(StatueSounds.CAMPFIRE_BYE_RANDOM, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_BYE.getId()))
				.with(
						sound(modLoc("campfire/bye")),
						sound(modLoc("campfire/bye2"))
				));
		this.add(StatueSounds.CAMPFIRE_COLD, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_COLD.getId()))
				.with(sound(modLoc("campfire/cold"))));
		this.add(StatueSounds.CAMPFIRE_COLD_2, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_COLD.getId()))
				.with(sound(modLoc("campfire/cold2"))));
		this.add(StatueSounds.CAMPFIRE_COLD_3, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_COLD.getId()))
				.with(sound(modLoc("campfire/cold3"))));
		this.add(StatueSounds.CAMPFIRE_COLD_RANDOM, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_COLD.getId()))
				.with(
						sound(modLoc("campfire/cold")),
						sound(modLoc("campfire/cold2")),
						sound(modLoc("campfire/cold3"))
				));
		this.add(StatueSounds.CAMPFIRE_SNACKS, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_SNACKS.getId()))
				.with(sound(modLoc("campfire/snacks"))));
		this.add(StatueSounds.CAMPFIRE_SNACKS_2, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_SNACKS.getId()))
				.with(sound(modLoc("campfire/snacks2"))));
		this.add(StatueSounds.CAMPFIRE_SNACKS_3, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_SNACKS.getId()))
				.with(sound(modLoc("campfire/snacks3"))));
		this.add(StatueSounds.CAMPFIRE_SNACKS_4, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_SNACKS.getId()))
				.with(sound(modLoc("campfire/snacks4"))));
		this.add(StatueSounds.CAMPFIRE_SNACKS_RANDOM, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_SNACKS.getId()))
				.with(
						sound(modLoc("campfire/snacks")),
						sound(modLoc("campfire/snacks2")),
						sound(modLoc("campfire/snacks3")),
						sound(modLoc("campfire/snacks4"))
				));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_2, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow2"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_3, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow3"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_4, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow4"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_5, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow5"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_6, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow6"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_7, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow7"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_8, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow8"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_9, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(sound(modLoc("campfire/marshmallow9"))));
		this.add(StatueSounds.CAMPFIRE_MARSHMALLOW_RANDOM, definition()
				.subtitle(modSubtitle(StatueSounds.CAMPFIRE_MARSHMALLOW.getId()))
				.with(
						sound(modLoc("campfire/marshmallow")),
						sound(modLoc("campfire/marshmallow2")),
						sound(modLoc("campfire/marshmallow3")),
						sound(modLoc("campfire/marshmallow4")),
						sound(modLoc("campfire/marshmallow5")),
						sound(modLoc("campfire/marshmallow6")),
						sound(modLoc("campfire/marshmallow7")),
						sound(modLoc("campfire/marshmallow8")),
						sound(modLoc("campfire/marshmallow9"))
				));
	}


	public String modSubtitle(ResourceLocation id) {
		return Reference.MOD_ID + ".subtitle." + id.getPath();
	}

	public ResourceLocation modLoc(String name) {
		return Reference.modLoc(name);
	}
}
