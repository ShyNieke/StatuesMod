package com.shynieke.statues.datagen.client;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.blocks.CoreFlowerCropBlock;
import com.shynieke.statues.blocks.decorative.DisplayStandBlock;
import com.shynieke.statues.client.property.StatueCompassAngle;
import com.shynieke.statues.client.render.PlayerSpecialRenderer;
import com.shynieke.statues.items.PlayerCompassItem;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators.PlantType;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel.Entry;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StatueModelProvider extends ModelProvider {
	public StatueModelProvider(PackOutput output) {
		super(output, Reference.MOD_ID);
	}

	private static final ModelTemplate PLAYER_STATUE = ModelTemplates.create("statues:player_statue", TextureSlot.PARTICLE).extend()
			.guiLight(UnbakedModel.GuiLight.FRONT)
			.transform(ItemDisplayContext.GUI, t -> {
				t.rotation(30.0F, 225.0F, 0.0F);
				t.translation(0.0F, 2.0F, 0.0F);
				t.scale(1.2F);
			})
			.transform(ItemDisplayContext.HEAD, t -> {
				t.translation(0.0F, 6.2608F, 0.0F);
			})
			.build();

	@Override
	protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		for (DeferredHolder<Block, ? extends Block> registryObject : StatueRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() == StatueRegistry.PLAYER_STATUE.get()) {
				blockModels.createParticleOnlyBlock(registryObject.get(), Blocks.SOUL_SAND);
				Item item = registryObject.get().asItem();
				Identifier identifier = PLAYER_STATUE.create(item, TextureMapping.particle(registryObject.get()), blockModels.modelOutput);
				ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.specialModel(identifier, new PlayerSpecialRenderer.Unbaked());
				itemModels.itemModelOutput.accept(item, itemmodel$unbaked);
			} else if (registryObject.get() instanceof AbstractBaseBlock) {
				makeStatue(blockModels, registryObject.get());
			} else if (registryObject.get() == StatueRegistry.CORE_FLOWER_CROP.get()) {
				blockModels.createCropBlock(registryObject.get(), CoreFlowerCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7, 8);
			} else if (registryObject.get() instanceof FlowerBlock) {
				blockModels.registerSimpleItemModel(registryObject.get(), PlantType.NOT_TINTED.createItemModel(blockModels, registryObject.get()));
				blockModels.createCrossBlock(registryObject.get(), PlantType.NOT_TINTED);
			} else if (registryObject.get() instanceof DisplayStandBlock) {
				blockModels.createNonTemplateModelBlock(registryObject.get());
			} else {
				TextureMapping texturemapping = TextureMapping.cube(registryObject.get());
				MultiVariant multivariant = BlockModelGenerators.plainVariant(
						ModelTemplates.CUBE_ALL.create(registryObject.get(), texturemapping, blockModels.modelOutput)
				);
				blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(registryObject.get(), multivariant));
			}
		}

		for (DeferredHolder<Item, ? extends Item> registryObject : StatueRegistry.ITEMS.getEntries()) {
			if (registryObject.get() instanceof BlockItem) {
				continue; //Ignore block items as they are handled by the block model
			}

			if (registryObject.get() instanceof PlayerCompassItem) {
				generateStatueCompass(itemModels, registryObject.get());
			} else {
				itemModels.generateFlatItem(registryObject.get(), ModelTemplates.FLAT_ITEM);
			}
		}
	}

	public void generateStatueCompass(ItemModelGenerators itemModels, Item item) {
		List<Entry> list = itemModels.createCompassModels(item);
		itemModels.itemModelOutput
				.accept(
						item,
						ItemModelUtils.rangeSelect(new StatueCompassAngle(false), 32.0F, list)
				);
	}

	public void makeStatue(BlockModelGenerators generators, Block statueBlock) {
		MultiVariant multivariant = BlockModelGenerators.plainVariant(BuiltInRegistries.BLOCK.getKey(statueBlock).withPrefix("block/"));
		generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(statueBlock, multivariant).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
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
