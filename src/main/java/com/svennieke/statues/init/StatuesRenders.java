package com.svennieke.statues.init;

import com.svennieke.statues.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class StatuesRenders {

	@SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {
        for(Item item : StatuesItems.ITEMS)
        {
        	//System.out.println(ResourceTiersAreCool(item.getRegistryName().getResourcePath()));
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ResourceTiersAreCool(item.getRegistryName().getResourcePath()), "inventory"));
        }
        
        for(Block block : StatuesBlocks.BLOCKS)
        {
        	Item item = Item.getItemFromBlock(block);
        	if(isTiered(block.getRegistryName().getResourcePath()))
        	{
        		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
        			@Override
        			protected ModelResourceLocation getModelResourceLocation(final IBlockState p_178132_1_) {
        				return new ModelResourceLocation(ResourceTiersAreCool(block.getRegistryName().getResourcePath()), "inventory");
        			}
            	});
        	}
        	else
        	{
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ResourceTiersAreCool(block.getRegistryName().getResourcePath()), "inventory"));
        	}
        }
    }
	
	public static ResourceLocation ResourceTiersAreCool(String statue)
	{
		return new ResourceLocation(Reference.MOD_ID, statue.replace("t2", "").replace("t3", "").replace("t4", "").replace("t5", ""));
	}
	
	public static boolean isTiered(String statue)
	{
		if(statue.contains("t2") || statue.contains("t3") || statue.contains("t4") || statue.contains("t5"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
