package com.svennieke.statues.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.svennieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.svennieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.svennieke.statues.blocks.Statues.BlockChickenJockey_Statue;
import com.svennieke.statues.blocks.Statues.BlockChicken_Statue;
import com.svennieke.statues.blocks.Statues.BlockCow_Statue;
import com.svennieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.svennieke.statues.blocks.Statues.BlockEnderman_Statue;
import com.svennieke.statues.blocks.Statues.BlockGhast_Statue;
import com.svennieke.statues.blocks.Statues.BlockGuardian_Statue;
import com.svennieke.statues.blocks.Statues.BlockHusk_Statue;
import com.svennieke.statues.blocks.Statues.BlockKingCluck_Statue;
import com.svennieke.statues.blocks.Statues.BlockMagmaSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockMooshroom_Statue;
import com.svennieke.statues.blocks.Statues.BlockPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockRabbit_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheepShaven_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheep_Statue;
import com.svennieke.statues.blocks.Statues.BlockSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockSnowGolem_Statue;
import com.svennieke.statues.blocks.Statues.BlockSquid_Statue;
import com.svennieke.statues.blocks.Statues.BlockVillager_Statue;
import com.svennieke.statues.blocks.Statues.BlockWastelandPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockWitch_Statue;
import com.svennieke.statues.blocks.Statues.BlockZombie_Statue;
import com.svennieke.statues.compat.jei.filling.StatueFillingCategory;
import com.svennieke.statues.compat.jei.filling.StatueFillingHandler;
import com.svennieke.statues.compat.jei.filling.StatueFillingWrapper;
import com.svennieke.statues.compat.jei.statueloot.StatueLootCategory;
import com.svennieke.statues.compat.jei.statueloot.StatueLootHandler;
import com.svennieke.statues.compat.jei.statueloot.StatueLootWrapper;
import com.svennieke.statues.compat.list.StatueLootList;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesItems;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class StatueJEIPlugin implements IModPlugin{
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new StatueLootCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new StatueFillingCategory(jeiHelpers.getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeHandlers(new StatueLootHandler());
		registry.addRecipeHandlers(new StatueFillingHandler());
		
		registry.addRecipes(getStatueRecipes(), StatueLootCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(StatuesItems.core), StatueLootCategory.UID);
		
		registry.addRecipes(getFillingRecipes(), StatueFillingCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(StatuesItems.core), StatueFillingCategory.UID);

	}
	
	private List<StatueLootWrapper> getStatueRecipes() {
        List<StatueLootWrapper> result = new ArrayList<StatueLootWrapper>();
        
        for(Block block : StatuesBlocks.BLOCKS)
        {	
        	String blockName = block.getUnlocalizedName();
        	boolean tierFlag = (blockName.contains("t3") || blockName.contains("t4"));
        	if(blockName.contains("statue") && tierFlag)
        	{
        		if(block instanceof BlockBabyZombie_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("baby_zombie"));

            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
                	
                	result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockBlaze_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("blaze"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockChicken_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("chicken"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockChickenJockey_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("chicken_jockey"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockCow_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("cow"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockCreeper_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("creeper"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockEnderman_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("enderman"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockGhast_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("ghast"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockGuardian_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("guardian"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockHusk_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("husk"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockKingCluck_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ItemStack stack1 = BlockKingCluck_Statue.getNugget();
        			ItemStack stack2 = null;
        			ItemStack stack3 = BlockKingCluck_Statue.getGold();
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockMagmaSlime_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("magma_slime"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockMooshroom_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("mooshroom"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockPig_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("pig"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockRabbit_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("rabbit"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockSheep_Statue)
        		{
        			BlockSheep_Statue sheep= (BlockSheep_Statue)block;
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep"));
        			ItemStack stack1 = sheep.getWool();
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockSheepShaven_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep_shaven"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockSlime_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("slime"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockSnowGolem_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("snowgolem"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockSquid_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("squid"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockVillager_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("villager"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockWastelandPig_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ItemStack stack1 = new ItemStack(StatuesItems.tea, 1);
        			ItemStack stack2 = new ItemStack(Blocks.SAND, 1).setStackDisplayName("Wasteland Block");;
        			ItemStack stack3 = null;
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockWitch_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("witch"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        		if(block instanceof BlockZombie_Statue)
        		{
        			ItemStack statue = new ItemStack(block);
        			
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("zombie"));
            		ItemStack stack1 = stackList.get(0);
                	ItemStack stack2 = stackList.get(1);
                	ItemStack stack3 = stackList.get(2);
        			
        			result.add(new StatueLootWrapper(statue, stack1, stack2, stack3));
        		}
        	}
        }
        return result;
	}
	
	private List<StatueFillingWrapper> getFillingRecipes() {
        List<StatueFillingWrapper> result = new ArrayList<StatueFillingWrapper>();
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.cow_statuet3), new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.cow_statuet4), new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET)));
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.mooshroom_statuet3), new ItemStack(Items.BOWL), new ItemStack(Items.MUSHROOM_STEW)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.mooshroom_statuet4), new ItemStack(Items.BOWL), new ItemStack(Items.MUSHROOM_STEW)));
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.flood_statuet3), new ItemStack(Items.BUCKET), new ItemStack(Items.WATER_BUCKET)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.flood_statuet3), new ItemStack(Items.BUCKET), new ItemStack(Items.WATER_BUCKET)));
        
        return result;
	}
}
