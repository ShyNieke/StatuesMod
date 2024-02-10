package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.blocks.CoreFlowerCropBlock;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class StatueBlockstateProvider extends BlockStateProvider {
	public StatueBlockstateProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		for (DeferredHolder<Block, ? extends Block> registryObject : StatueRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof AbstractBaseBlock) {
				makeStatue(registryObject);
			} else if (registryObject.get() instanceof CoreFlowerCropBlock) {
				buildCrop((CoreFlowerCropBlock) registryObject.get(), CoreFlowerCropBlock.AGE);
			} else if (registryObject.get() instanceof FlowerBlock) {
				crossBlock(registryObject);
			} else {
				ModelFile blockModel = models().getExistingFile(modLoc("block/" + registryObject.getId().getPath()));
				getVariantBuilder(registryObject.get())
						.partialState().modelForState().modelFile(blockModel).addModel();
			}
		}
	}

	protected void buildCrop(CropBlock block, IntegerProperty property) {
		VariantBlockStateBuilder builder = getVariantBuilder(block);
		for (int i = 0; i <= block.getMaxAge(); i++) {
			ModelFile file = models().crop(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + (i),
					new ResourceLocation(Reference.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_stage" + (i))).renderType(new ResourceLocation("cutout"));
			builder.partialState().with(property, i).modelForState().modelFile(file).addModel();
		}
	}

	private void crossBlock(DeferredHolder<Block, ? extends Block> block) {
		simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())));
	}

	private void makeStatue(DeferredHolder<Block, ? extends Block> registryObject) {
		ModelFile model = models().getExistingFile(modLoc("block/" + registryObject.getId().getPath()));
		getVariantBuilder(registryObject.get())
				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
				.modelForState().modelFile(model).addModel()
				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
				.modelForState().modelFile(model).rotationY(90).addModel()
				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
				.modelForState().modelFile(model).rotationY(180).addModel()
				.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
				.modelForState().modelFile(model).rotationY(270).addModel();
	}
}
