package com.svennieke.statues.init;

import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.svennieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.svennieke.statues.blocks.Statues.BlockBumbo_Statue;
import com.svennieke.statues.blocks.Statues.BlockChickenJockey_Statue;
import com.svennieke.statues.blocks.Statues.BlockChicken_Statue;
import com.svennieke.statues.blocks.Statues.BlockCow_Statue;
import com.svennieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.svennieke.statues.blocks.Statues.BlockEnderman_Statue;
import com.svennieke.statues.blocks.Statues.BlockEndermite_Statue;
import com.svennieke.statues.blocks.Statues.BlockEtho_Statue;
import com.svennieke.statues.blocks.Statues.BlockEvoker_Statue;
import com.svennieke.statues.blocks.Statues.BlockFlood_Statue;
import com.svennieke.statues.blocks.Statues.BlockGhast_Statue;
import com.svennieke.statues.blocks.Statues.BlockGuardian_Statue;
import com.svennieke.statues.blocks.Statues.BlockHusk_Statue;
import com.svennieke.statues.blocks.Statues.BlockInfo_Statue;
import com.svennieke.statues.blocks.Statues.BlockKingCluck_Statue;
import com.svennieke.statues.blocks.Statues.BlockMagmaSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockMooshroom_Statue;
import com.svennieke.statues.blocks.Statues.BlockPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.blocks.Statues.BlockPufferfish_Statue;
import com.svennieke.statues.blocks.Statues.BlockRabbit_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheepShaven_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheep_Statue;
import com.svennieke.statues.blocks.Statues.BlockShulker_Statue;
import com.svennieke.statues.blocks.Statues.BlockSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockSnowGolem_Statue;
import com.svennieke.statues.blocks.Statues.BlockSombrero_Statue;
import com.svennieke.statues.blocks.Statues.BlockSpider_Statue;
import com.svennieke.statues.blocks.Statues.BlockSquid_Statue;
import com.svennieke.statues.blocks.Statues.BlockTotemOfUndying_Statue;
import com.svennieke.statues.blocks.Statues.BlockVillager_Statue;
import com.svennieke.statues.blocks.Statues.BlockWastelandPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockWitch_Statue;
import com.svennieke.statues.blocks.Statues.BlockZombie_Statue;
import com.svennieke.statues.blocks.decorative.BlockDisplayStand;
import com.svennieke.statues.blocks.decorative.BlockPebble;
import com.svennieke.statues.items.ItemBlockStatue;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@EventBusSubscriber
public class StatuesBlocks {

	private static final int MAX_TIERS = 5;
	//Regular Statues
	public static Block[] baby_zombie_statue;
	public static Block[] blaze_statue;
	public static Block[] chicken_statue;
	public static Block[] cow_statue;
	public static Block[] creeper_statue;
	public static Block[] flood_statue;
	public static Block[] kingcluck_statue;
	public static Block[] mooshroom_statue;
	public static Block[] pig_statue;
	public static Block[] rabbit_bs_statue;
	public static Block[] rabbit_br_statue;
	public static Block[] rabbit_bw_statue;
	public static Block[] rabbit_ws_statue;
	public static Block[] rabbit_wh_statue;
	public static Block[] rabbit_go_statue;
	public static Block[] sheepshaven_statue;
	public static Block[] slime_statue;
	public static Block[] snowgolem_statue;
	public static Block[] villager_br_statue;
	public static Block[] villager_gr_statue;
	public static Block[] villager_pu_statue;
	public static Block[] villager_wh_statue;
	public static Block[] squid_statue;
	public static Block[] witch_statue;
	public static Block[] zombie_statue;
	public static Block[] husk_statue;
	public static Block[] shulker_statue;
	public static Block[] chicken_jockey_statue;
	public static Block[] magma_statue;
	public static Block[] ghast_statue;
	public static Block[] guardian_statue;
	public static Block[] wasteland_statue;
	public static Block[] enderman_statue;
	public static Block[] pufferfish_statue;
	public static Block[] evoker_statue;
	public static Block[] spider_statue;
	public static Block[] campfire_statue;
	public static Block[] drowned_statue;
	public static Block[] turtle_statue;

	//Sheep Statues
	public static Block[] sheep_statue_white;
	public static Block[] sheep_statue_orange;
	public static Block[] sheep_statue_magenta;
	public static Block[] sheep_statue_lightblue;
	public static Block[] sheep_statue_yellow;
	public static Block[] sheep_statue_lime;
	public static Block[] sheep_statue_pink;
	public static Block[] sheep_statue_gray;
	public static Block[] sheep_statue_lightgray;
	public static Block[] sheep_statue_cyan;
	public static Block[] sheep_statue_purple;
	public static Block[] sheep_statue_blue;
	public static Block[] sheep_statue_brown;
	public static Block[] sheep_statue_green;
	public static Block[] sheep_statue_red;
	public static Block[] sheep_statue_black;

	//Other Statues
	public static Block info_statue;
	public static Block player_statue;
	public static Block endermite_statue;
	public static Block totemofundying_statue;
	public static Block pebble;
	public static Block display_stand;
	public static Block sombrero;
	public static Block bumbo_statue;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();
		
		baby_zombie_statue = registerStatue(new BlockBabyZombie_Statue("babyzombiestatue"), MAX_TIERS);
		blaze_statue = registerStatue(new BlockBlaze_Statue("blazestatue"), MAX_TIERS);
		chicken_statue = registerStatue(new BlockChicken_Statue("chickenstatue"), MAX_TIERS);
		cow_statue = registerStatue(new BlockCow_Statue("cowstatue"), MAX_TIERS);
		creeper_statue = registerStatue(new BlockCreeper_Statue("creeperstatue"), MAX_TIERS);
		flood_statue = registerStatue(new BlockFlood_Statue("floodstatue"), MAX_TIERS);
		kingcluck_statue = registerStatue(new BlockKingCluck_Statue("kingcluckstatue"), MAX_TIERS);
		mooshroom_statue = registerStatue(new BlockMooshroom_Statue("mooshroomstatue"), MAX_TIERS);
		pig_statue = registerStatue(new BlockPig_Statue("pigstatue"), MAX_TIERS);

		rabbit_bs_statue = registerStatue(new BlockRabbit_Statue("rabbitblackspotstatue"), MAX_TIERS);
		rabbit_br_statue = registerStatue(new BlockRabbit_Statue("rabbitbrownstatue"), MAX_TIERS);
		rabbit_bw_statue = registerStatue(new BlockRabbit_Statue("rabbitstatue"), MAX_TIERS);
		rabbit_ws_statue = registerStatue(new BlockRabbit_Statue("rabbitwhitespotstatue"), MAX_TIERS);
		rabbit_wh_statue = registerStatue(new BlockRabbit_Statue("rabbitwhitestatue"), MAX_TIERS);
		rabbit_go_statue = registerStatue(new BlockRabbit_Statue("rabbityellowstatue"), MAX_TIERS);
		
		sheepshaven_statue = registerStatue(new BlockSheepShaven_Statue("sheepshavenstatue"), MAX_TIERS);
		sheep_statue_white = registerStatue(new BlockSheep_Statue("sheepstatue").setColor(EnumDyeColor.WHITE), MAX_TIERS);
		sheep_statue_orange = registerStatue(new BlockSheep_Statue("sheepstatueorange").setColor(EnumDyeColor.ORANGE), MAX_TIERS);
		sheep_statue_magenta = registerStatue(new BlockSheep_Statue("sheepstatuemagenta").setColor(EnumDyeColor.MAGENTA), MAX_TIERS);
		sheep_statue_lightblue = registerStatue(new BlockSheep_Statue("sheepstatuelightblue").setColor(EnumDyeColor.LIGHT_BLUE), MAX_TIERS);
		sheep_statue_yellow = registerStatue(new BlockSheep_Statue("sheepstatueyellow").setColor(EnumDyeColor.YELLOW), MAX_TIERS);
		sheep_statue_lime = registerStatue(new BlockSheep_Statue("sheepstatuelime").setColor(EnumDyeColor.LIME), MAX_TIERS);
		sheep_statue_pink = registerStatue(new BlockSheep_Statue("sheepstatuepink").setColor(EnumDyeColor.PINK), MAX_TIERS);
		sheep_statue_gray = registerStatue(new BlockSheep_Statue("sheepstatuegray").setColor(EnumDyeColor.GRAY), MAX_TIERS);
		sheep_statue_lightgray = registerStatue(new BlockSheep_Statue("sheepstatuelightgray").setColor(EnumDyeColor.SILVER), MAX_TIERS);
		sheep_statue_cyan = registerStatue(new BlockSheep_Statue("sheepstatuecyan").setColor(EnumDyeColor.CYAN), MAX_TIERS);
		sheep_statue_purple = registerStatue(new BlockSheep_Statue("sheepstatuepurple").setColor(EnumDyeColor.PURPLE), MAX_TIERS);
		sheep_statue_blue = registerStatue(new BlockSheep_Statue("sheepstatueblue").setColor(EnumDyeColor.BLUE), MAX_TIERS);
		sheep_statue_brown = registerStatue(new BlockSheep_Statue("sheepstatuebrown").setColor(EnumDyeColor.BROWN), MAX_TIERS);
		sheep_statue_green = registerStatue(new BlockSheep_Statue("sheepstatuegreen").setColor(EnumDyeColor.GREEN), MAX_TIERS);
		sheep_statue_red = registerStatue(new BlockSheep_Statue("sheepstatuered").setColor(EnumDyeColor.RED), MAX_TIERS);
		sheep_statue_black = registerStatue(new BlockSheep_Statue("sheepstatueblack").setColor(EnumDyeColor.BLACK), MAX_TIERS);

		slime_statue = registerStatue(new BlockSlime_Statue("slimestatue"), MAX_TIERS);
		snowgolem_statue = registerStatue(new BlockSnowGolem_Statue("snowgolemstatue"), MAX_TIERS);
		squid_statue = registerStatue(new BlockSquid_Statue("squidstatue"), MAX_TIERS);

		villager_br_statue = registerStatue(new BlockVillager_Statue("villagerbrowncoatstatue"), MAX_TIERS);
		villager_pu_statue = registerStatue(new BlockVillager_Statue("villagerpurplecoatstatue"), MAX_TIERS);
		villager_gr_statue = registerStatue(new BlockVillager_Statue("villagerstatue"), MAX_TIERS);
		villager_wh_statue = registerStatue(new BlockVillager_Statue("villagerwhitecoatstatue"), MAX_TIERS);

		witch_statue = registerStatue(new BlockWitch_Statue("witchstatue"), MAX_TIERS);
		zombie_statue = registerStatue(new BlockZombie_Statue("zombiestatue"), MAX_TIERS);
		husk_statue = registerStatue(new BlockHusk_Statue("huskstatue"), MAX_TIERS);
		shulker_statue = registerStatue(new BlockShulker_Statue("shulkerstatue"), MAX_TIERS);
		chicken_jockey_statue = registerStatue(new BlockChickenJockey_Statue("chickenjockeystatue"), MAX_TIERS);
		magma_statue = registerStatue(new BlockMagmaSlime_Statue("magmastatue"), MAX_TIERS);
		ghast_statue = registerStatue(new BlockGhast_Statue("ghaststatue"), MAX_TIERS);
		guardian_statue = registerStatue(new BlockGuardian_Statue("guardianstatue"), MAX_TIERS);
		enderman_statue = registerStatue(new BlockEnderman_Statue("endermanstatue"), MAX_TIERS);
		pufferfish_statue = registerStatue(new BlockPufferfish_Statue("pufferfishstatue"), MAX_TIERS);
		wasteland_statue = registerStatue(new BlockWastelandPig_Statue("wastelandpigstatue"), MAX_TIERS);
		evoker_statue = registerStatue(new BlockEvoker_Statue("evokerstatue"), MAX_TIERS);
		spider_statue = registerStatue(new BlockSpider_Statue("spiderstatue"), MAX_TIERS);
		
		info_statue = registerBlock(new BlockInfo_Statue("infostatue", "blockinfostatue", 1));
		player_statue = registerBlock(new BlockPlayer_Statue("playerstatue", "blockplayerstatue", ""));
		
		endermite_statue = registerBlock(new BlockEndermite_Statue("endermitestatue", "blockendermitestatue"));

		pebble = registerBlock(new BlockPebble("pebble", "blockpebble"));

		display_stand = registerBlock(new BlockDisplayStand("displaystand", "blockdisplaystand"));
		sombrero = registerBlock(new BlockSombrero_Statue("sombrero", "blocksombrero"));
		bumbo_statue = registerBlock(new BlockBumbo_Statue("bumbostatue", "blockbumbostatue"));
		totemofundying_statue = registerBlock(new BlockTotemOfUndying_Statue("totemofundyingstatue", "blocktotemofundyingstatue"));
		
		campfire_statue = registerStatue(new BlockEtho_Statue("campfirestatue"), MAX_TIERS);
		drowned_statue = registerStatue(new BlockEtho_Statue("drownedstatue"), MAX_TIERS);
		turtle_statue = registerStatue(new BlockEtho_Statue("turtlestatue"), 1);

		registry.registerAll(BLOCKS.toArray(new Block[0]));
	}
    
	public static <T extends Block> Block[] registerStatue(T block, int maxTiers)
	{
	    Block[] allBlocks = new Block[maxTiers];

	    for(int i = 0; i < maxTiers; i++)
	    {
	        IStatue statue;
	        try {
	            statue = ((IStatue)block.getClass().getConstructor(String.class).newInstance(block.getTranslationKey().replace("tile.", "")));
	            statue.setTier(i+1);
	            statue.setColor(statue.getColor());
	            allBlocks[i] = registerBlock((Block)statue, new ItemBlockStatue((Block)statue));
	        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
	                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
	            e.printStackTrace();
	        }
	    }

	    return allBlocks;
	}
	
	public static <T extends Block> T registerBlock(T block)
    {
        return registerBlock(block, new ItemBlockStatue(block));
    }
    
    public static <T extends Block> T registerBlock(T block, ItemBlock item)
    {
    	item.setRegistryName(block.getRegistryName());
    	StatuesItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
}