package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class StatueBlockstateProvider extends BlockStateProvider {
	public StatueBlockstateProvider(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		for (RegistryObject<Block> registryObject : StatueRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof AbstractBaseBlock) {
				makeStatue(registryObject);
			} else {
				ModelFile blockModel = models().getExistingFile(modLoc("block/" + registryObject.getId().getPath()));
				getVariantBuilder(registryObject.get())
						.partialState().modelForState().modelFile(blockModel).addModel();
			}
		}
	}

	private void makeStatue(RegistryObject<Block> registryObject) {
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
