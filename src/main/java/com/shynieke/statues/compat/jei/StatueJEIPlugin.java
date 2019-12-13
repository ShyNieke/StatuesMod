package com.shynieke.statues.compat.jei;

import com.shynieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.shynieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.shynieke.statues.blocks.Statues.BlockChickenJockey_Statue;
import com.shynieke.statues.blocks.Statues.BlockChicken_Statue;
import com.shynieke.statues.blocks.Statues.BlockCow_Statue;
import com.shynieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.shynieke.statues.blocks.Statues.BlockEnderman_Statue;
import com.shynieke.statues.blocks.Statues.BlockEtho_Statue;
import com.shynieke.statues.blocks.Statues.BlockEvoker_Statue;
import com.shynieke.statues.blocks.Statues.BlockFlood_Statue;
import com.shynieke.statues.blocks.Statues.BlockGhast_Statue;
import com.shynieke.statues.blocks.Statues.BlockGuardian_Statue;
import com.shynieke.statues.blocks.Statues.BlockHusk_Statue;
import com.shynieke.statues.blocks.Statues.BlockKingCluck_Statue;
import com.shynieke.statues.blocks.Statues.BlockMagmaSlime_Statue;
import com.shynieke.statues.blocks.Statues.BlockMooshroom_Statue;
import com.shynieke.statues.blocks.Statues.BlockPig_Statue;
import com.shynieke.statues.blocks.Statues.BlockPufferfish_Statue;
import com.shynieke.statues.blocks.Statues.BlockRabbit_Statue;
import com.shynieke.statues.blocks.Statues.BlockSheepShaven_Statue;
import com.shynieke.statues.blocks.Statues.BlockSheep_Statue;
import com.shynieke.statues.blocks.Statues.BlockSlime_Statue;
import com.shynieke.statues.blocks.Statues.BlockSnowGolem_Statue;
import com.shynieke.statues.blocks.Statues.BlockSpider_Statue;
import com.shynieke.statues.blocks.Statues.BlockSquid_Statue;
import com.shynieke.statues.blocks.Statues.BlockVillager_Statue;
import com.shynieke.statues.blocks.Statues.BlockWastelandPig_Statue;
import com.shynieke.statues.blocks.Statues.BlockWitch_Statue;
import com.shynieke.statues.blocks.Statues.BlockZombie_Statue;
import com.shynieke.statues.compat.jei.filling.StatueFillingCategory;
import com.shynieke.statues.compat.jei.filling.StatueFillingHandler;
import com.shynieke.statues.compat.jei.filling.StatueFillingWrapper;
import com.shynieke.statues.compat.jei.statueloot.StatueLootCategory;
import com.shynieke.statues.compat.jei.statueloot.StatueLootHandler;
import com.shynieke.statues.compat.jei.statueloot.StatueLootWrapper;
import com.shynieke.statues.compat.list.StatueLootList;
import com.shynieke.statues.config.StatuesConfigGen;
import com.shynieke.statues.init.StatuesBlocks;
import com.shynieke.statues.init.StatuesItems;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class StatueJEIPlugin implements IModPlugin{
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		if(!StatuesConfigGen.general.nonFunctional) {
	        registry.addRecipeCategories(new StatueLootCategory(jeiHelpers.getGuiHelper()));
	        registry.addRecipeCategories(new StatueFillingCategory(jeiHelpers.getGuiHelper()));
		}
	}
	
	@Override
	public void register(IModRegistry registry) {
		if(!StatuesConfigGen.general.nonFunctional) {
			registry.addRecipeHandlers(new StatueLootHandler());
			registry.addRecipeHandlers(new StatueFillingHandler());
			
			registry.addRecipes(getStatueRecipes(), StatueLootCategory.UID);
			registry.addRecipeCatalyst(new ItemStack(StatuesItems.core), StatueLootCategory.UID);
			
			registry.addRecipes(getFillingRecipes(), StatueFillingCategory.UID);
			registry.addRecipeCatalyst(new ItemStack(StatuesItems.core), StatueFillingCategory.UID);
		}
	}
	
	private List<StatueLootWrapper> getStatueRecipes() {
        List<StatueLootWrapper> result = new ArrayList<StatueLootWrapper>();
        
        for(Block block : StatuesBlocks.BLOCKS)
        {	
        	String blockName = block.getTranslationKey();
        	boolean tierFlag = (blockName.contains("t3") || blockName.contains("t4") || blockName.contains("t5"));
        	if(blockName.contains("statue") && tierFlag)
        	{
        		if(block instanceof BlockBabyZombie_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("baby_zombie"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockBlaze_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("blaze"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockChicken_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("chicken"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockChickenJockey_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("chicken_jockey"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockCow_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("cow"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockCreeper_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("creeper"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockEnderman_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("enderman"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockFlood_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("flood"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockGhast_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("ghast"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockGuardian_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("guardian"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockHusk_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("husk"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockKingCluck_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("king_cluck"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockMagmaSlime_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("magma_slime"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockMooshroom_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("mooshroom"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockPig_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("pig"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockRabbit_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("rabbit"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockSheep_Statue)
        		{
        			BlockSheep_Statue sheep= (BlockSheep_Statue)block;
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep"));
        			result.add(new StatueLootWrapper(new ItemStack(block), sheep.getWool(), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockSheepShaven_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("sheep_shaven"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockSlime_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("slime"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockSnowGolem_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("snowgolem"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockSquid_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("squid"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockVillager_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("villager"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockWastelandPig_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("wasteland_pig"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockWitch_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("witch"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockZombie_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("zombie"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockPufferfish_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("pufferfish"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockEvoker_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("evoker"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockSpider_Statue)
        		{
        			ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("spider"));
        			result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
        		}
        		if(block instanceof BlockEtho_Statue)
            	{
            		ArrayList<ItemStack> stackList = new ArrayList<>(StatueLootList.getStacksForStatue("etho"));
            		result.add(new StatueLootWrapper(new ItemStack(block), stackList.get(0), stackList.get(1), stackList.get(2)));
            	}
        	}
        }
        return result;
	}
	
	private List<StatueFillingWrapper> getFillingRecipes() {
        List<StatueFillingWrapper> result = new ArrayList<StatueFillingWrapper>();
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.cow_statue[2]), new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.cow_statue[3]), new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET)));
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.mooshroom_statue[2]), new ItemStack(Items.BOWL), new ItemStack(StatuesItems.soup)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.mooshroom_statue[3]), new ItemStack(Items.BOWL), new ItemStack(StatuesItems.soup)));
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.flood_statue[2]), new ItemStack(Items.BUCKET), StatueLootList.getFloodBucket()));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.flood_statue[3]), new ItemStack(Items.BUCKET), StatueLootList.getFloodBucket()));
        
        return result;
	}
}
