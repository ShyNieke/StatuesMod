package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.blocks.CoreFlowerCropBlock;
import com.shynieke.statues.blocks.decorative.DisplayStandBlock;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators.PlantType;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class StatueModelProvider extends ModelProvider {
	public StatueModelProvider(PackOutput output) {
		super(output, Reference.MOD_ID);
	}

	@Override
	protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		for (DeferredHolder<Block, ? extends Block> registryObject : StatueRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof AbstractBaseBlock) {
				makeStatue(blockModels, registryObject.get());
			} else if (registryObject.get() == StatueRegistry.CORE_FLOWER_CROP.get()) {
				blockModels.createCropBlock(registryObject.get(), CoreFlowerCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7, 8);
			} else if (registryObject.get() instanceof FlowerBlock) {
				blockModels.registerSimpleItemModel(registryObject.get(), PlantType.NOT_TINTED.createItemModel(blockModels, registryObject.get()));
				blockModels.createCrossBlock(registryObject.get(), PlantType.NOT_TINTED);
			} else if (registryObject.get() instanceof DisplayStandBlock) {
				blockModels.createNonTemplateModelBlock(registryObject.get());
			} else {
				blockModels.createGenericCube(registryObject.get());
				blockModels.registerSimpleItemModel(registryObject.get(), registryObject.getId().withPrefix("block/"));
			}
		}

		for (DeferredHolder<Item, ? extends Item> registryObject : StatueRegistry.ITEMS.getEntries()) {
			if (registryObject.get() instanceof BlockItem) {
				continue; //Ignore block items as they are handled by the block model
			}
			itemModels.generateFlatItem(registryObject.get(), ModelTemplates.FLAT_ITEM); //TODO: Fix compass

//			if (registryObject.get() instanceof PlayerCompassItem) {
//				generateStatueCompass(registryObject.getId(), mcLoc("item/compass_16"));
//			} else if (registryObject.get() instanceof SpawnEggItem) {
//				withExistingParent(registryObject.getId().getPath(), ResourceLocation.withDefaultNamespace("item/template_spawn_egg"));
//			} else {
//				generatedItem(registryObject.getId());
//			}
		}
	}

	public void makeStatue(BlockModelGenerators generators, Block statueBlock) {
		generators.blockStateOutput
				.accept(
						MultiVariantGenerator.multiVariant(statueBlock, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(statueBlock)))
								.with(BlockModelGenerators.createHorizontalFacingDispatch())
				);
	}

//	private void makeStatue(DeferredHolder<Block, ? extends Block> registryObject) {
//		ModelFile model = models().getExistingFile(modLoc("block/" + registryObject.getId().getPath()));
//		getVariantBuilder(registryObject.get())
//				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
//				.modelForState().modelFile(model).addModel()
//				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
//				.modelForState().modelFile(model).rotationY(90).addModel()
//				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
//				.modelForState().modelFile(model).rotationY(180).addModel()
//				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
//				.modelForState().modelFile(model).rotationY(270).addModel();
//	}
}
