package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.PlayerCompassItem;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class StatueItemModelProvider extends ItemModelProvider {
	public StatueItemModelProvider(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		for (RegistryObject<Item> registryObject : StatueRegistry.ITEMS.getEntries()) {
			if (registryObject.get() instanceof BlockItem) {
				withBlockParent(registryObject.getId());
			} else {
				if (registryObject.get() instanceof PlayerCompassItem) {
					generatedItem(registryObject.getId(), mcLoc("item/compass_16"));
				} else if (registryObject.get() instanceof SpawnEggItem) {
					withExistingParent(registryObject.getId().getPath(), new ResourceLocation("item/template_spawn_egg"));
				} else {
					generatedItem(registryObject.getId());
				}
			}
		}
	}

	private void withBlockParent(ResourceLocation location) {
		withExistingParent(location.getPath(), modLoc("block/" + location.getPath()));
	}

	private void generatedItem(ResourceLocation location) {
		singleTexture(location.getPath(), new ResourceLocation("item/generated"),
				"layer0", new ResourceLocation(Reference.MOD_ID, "item/" + location.getPath()));
	}

	private void generatedItem(ResourceLocation location, ResourceLocation textureLocation) {
		singleTexture(location.getPath(), new ResourceLocation("item/generated"),
				"layer0", textureLocation);
	}
}
