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
import com.svennieke.statues.blocks.Statues.BlockFlood_Statue;
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
        	}
        }
        return result;
	}
	
	private List<StatueFillingWrapper> getFillingRecipes() {
        List<StatueFillingWrapper> result = new ArrayList<StatueFillingWrapper>();
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.cow_statuet3), new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.cow_statuet4), new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET)));
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.mooshroom_statuet3), new ItemStack(Items.BOWL), new ItemStack(StatuesItems.soup)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.mooshroom_statuet4), new ItemStack(Items.BOWL), new ItemStack(StatuesItems.soup)));
        
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.flood_statuet3), new ItemStack(Items.BUCKET), new ItemStack(Items.WATER_BUCKET)));
        result.add(new StatueFillingWrapper(new ItemStack(StatuesBlocks.flood_statuet3), new ItemStack(Items.BUCKET), new ItemStack(Items.WATER_BUCKET)));
        
        return result;
	}
}
