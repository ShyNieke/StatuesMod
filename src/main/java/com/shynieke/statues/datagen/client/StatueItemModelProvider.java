package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.PlayerCompassItem;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
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
					generateStatueCompass(registryObject.getId(), mcLoc("item/compass_16"));
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

	private void generateStatueCompass(ResourceLocation location, ResourceLocation textureLocation) {
		ResourceLocation angle = new ResourceLocation("angle");
		singleTexture(location.getPath(), new ResourceLocation("item/generated"), "layer0", textureLocation)
				.override().predicate(angle, 0f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass"), existingFileHelper)).end()
				.override().predicate(angle, 0.015625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_17"), existingFileHelper)).end()
				.override().predicate(angle, 0.046875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_18"), existingFileHelper)).end()
				.override().predicate(angle, 0.078125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_19"), existingFileHelper)).end()
				.override().predicate(angle, 0.109375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_20"), existingFileHelper)).end()
				.override().predicate(angle, 0.140625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_21"), existingFileHelper)).end()
				.override().predicate(angle, 0.171875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_22"), existingFileHelper)).end()
				.override().predicate(angle, 0.203125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_23"), existingFileHelper)).end()
				.override().predicate(angle, 0.234375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_24"), existingFileHelper)).end()
				.override().predicate(angle, 0.265625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_25"), existingFileHelper)).end()
				.override().predicate(angle, 0.296875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_26"), existingFileHelper)).end()
				.override().predicate(angle, 0.328125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_27"), existingFileHelper)).end()
				.override().predicate(angle, 0.359375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_28"), existingFileHelper)).end()
				.override().predicate(angle, 0.390625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_29"), existingFileHelper)).end()
				.override().predicate(angle, 0.421875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_30"), existingFileHelper)).end()
				.override().predicate(angle, 0.453125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_31"), existingFileHelper)).end()
				.override().predicate(angle, 0.484375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_00"), existingFileHelper)).end()
				.override().predicate(angle, 0.515625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_01"), existingFileHelper)).end()
				.override().predicate(angle, 0.546875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_02"), existingFileHelper)).end()
				.override().predicate(angle, 0.578125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_03"), existingFileHelper)).end()
				.override().predicate(angle, 0.609375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_04"), existingFileHelper)).end()
				.override().predicate(angle, 0.640625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_05"), existingFileHelper)).end()
				.override().predicate(angle, 0.671875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_06"), existingFileHelper)).end()
				.override().predicate(angle, 0.703125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_07"), existingFileHelper)).end()
				.override().predicate(angle, 0.734375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_08"), existingFileHelper)).end()
				.override().predicate(angle, 0.765625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_09"), existingFileHelper)).end()
				.override().predicate(angle, 0.796875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_10"), existingFileHelper)).end()
				.override().predicate(angle, 0.828125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_11"), existingFileHelper)).end()
				.override().predicate(angle, 0.859375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_12"), existingFileHelper)).end()
				.override().predicate(angle, 0.890625f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_13"), existingFileHelper)).end()
				.override().predicate(angle, 0.921875f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_14"), existingFileHelper)).end()
				.override().predicate(angle, 0.953125f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass_15"), existingFileHelper)).end()
				.override().predicate(angle, 0.984375f).model(new ItemModelBuilder(new ResourceLocation(ITEM_FOLDER + "/compass"), existingFileHelper)).end();
	}
}
