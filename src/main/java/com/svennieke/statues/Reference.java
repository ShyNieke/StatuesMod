package com.svennieke.statues;

public class Reference {
	public static final String MOD_ID = "statues";
	public static final String MOD_NAME = "Statues";
	public static final String VERSION = "0.7.1";
	public static final String ACCEPTED_VERSIONS = "[1.10.2]";
	public static final String DEPENDENCIES = "after:Baubles;";
			
	public static final String CLIENT_PROXY_CLASS = "com.svennieke.statues.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.svennieke.statues.proxy.ServerProxy";
	
	public static enum StatuesBlocks {
		SLIMESTATUE("slimestatue", "BlockSlimeStatue"),
		BLAZESTATUE("blazestatue", "BlockBlazeStatue"),
		COWSTATUE("cowstatue", "BlockCowStatue"),
		CHICKENSTATUE("chickenstatue", "BlockChickenStatue"),
		KINGCLUCKSTATUE("kingcluckstatue", "BlockKingCluckStatue"),
		MOOSHROOMSTATUE("mooshroomstatue", "BlockMooshroomStatue"),
		CREEPERSTATUE("creeperstatue", "BlockCreeperStatue"),
		SNOWGOLEMSTATUE("snowgolemstatue", "BlockSnowGolemStatue"),
		PIGSTATUE("pigstatue", "BlockpigStatue"),
		RABBITSTATUE("rabbitstatue", "BlockrabbitStatue"),
		SHEEPSTATUE("sheepstatue", "BlocksheepStatue"),
		SHEEPSHAVENSTATUE("sheepshavenstatue", "BlocksheepshavenStatue"),
		BABYZOMBIESTATUE("babyzombiestatue", "BlockBabyZombieStatue"),
		FLOODSTATUE("floodstatue", "BlockFloodStatue"),
		
		SLIMESTATUET2("slimestatuet2", "BlockSlimeStatuet2"),
		BLAZESTATUET2("blazestatuet2", "BlockBlazeStatuet2"),
		COWSTATUET2("cowstatuet2", "BlockCowStatuet2"),
		CHICKENSTATUET2("chickenstatuet2", "BlockChickenStatuet2"),
		KINGCLUCKSTATUET2("kingcluckstatuet2", "BlockKingCluckStatuet2"),
		MOOSHROOMSTATUET2("mooshroomstatuet2", "BlockMooshroomStatuet2"),
		CREEPERSTATUET2("creeperstatuet2", "BlockCreeperStatuet2"),
		SNOWGOLEMSTATUET2("snowgolemstatuet2", "BlockSnowGolemStatuet2"),
		PIGSTATUET2("pigstatuet2", "BlockpigStatuet2"),
		RABBITSTATUET2("rabbitstatuet2", "BlockrabbitStatuet2"),
		SHEEPSTATUET2("sheepstatuet2", "BlocksheepStatuet2"),
		SHEEPSHAVENSTATUET2("sheepshavenstatuet2", "BlocksheepshavenStatuet2"),
		BABYZOMBIESTATUET2("babyzombiestatuet2", "BlockBabyZombieStatuet2"),
		FLOODSTATUET2("floodstatuet2", "BlockFloodStatuet2"),
		
		SLIMESTATUET3("slimestatuet3", "BlockSlimeStatuet3"),
		BLAZESTATUET3("blazestatuet3", "BlockBlazeStatuet3"),
		COWSTATUET3("cowstatuet3", "BlockCowStatuet3"),
		CHICKENSTATUET3("chickenstatuet3", "BlockChickenStatuet3"),
		KINGCLUCKSTATUET3("kingcluckstatuet3", "BlockKingCluckStatuet3"),
		MOOSHROOMSTATUET3("mooshroomstatuet3", "BlockMooshroomStatuet3"),
		CREEPERSTATUET3("creeperstatuet3", "BlockCreeperStatuet3"),
		SNOWGOLEMSTATUET3("snowgolemstatuet3", "BlockSnowGolemStatuet3"),
		PIGSTATUET3("pigstatuet3", "BlockpigStatuet3"),
		RABBITSTATUET3("rabbitstatuet3", "BlockrabbitStatuet3"),
		SHEEPSTATUET3("sheepstatuet3", "BlocksheepStatuet3"),
		SHEEPSHAVENSTATUET3("sheepshavenstatuet3", "BlocksheepshavenStatuet3"),
		BABYZOMBIESTATUET3("babyzombiestatuet3", "BlockBabyZombieStatuet3"),
		FLOODSTATUET3("floodstatuet3", "BlockFloodStatuet3"),
		
		SLIMESTATUET4("slimestatuet4", "BlockSlimeStatuet4"),
		BLAZESTATUET4("blazestatuet4", "BlockBlazeStatuet4"),
		COWSTATUET4("cowstatuet4", "BlockCowStatuet4"),
		CHICKENSTATUET4("chickenstatuet4", "BlockChickenStatuet4"),
		KINGCLUCKSTATUET4("kingcluckstatuet4", "BlockKingCluckStatuet4"),
		MOOSHROOMSTATUET4("mooshroomstatuet4", "BlockMooshroomStatuet4"),
		CREEPERSTATUET4("creeperstatuet4", "BlockCreeperStatuet4"),
		SNOWGOLEMSTATUET4("snowgolemstatuet4", "BlockSnowGolemStatuet4"),
		PIGSTATUET4("pigstatuet4", "BlockpigStatuet4"),
		RABBITSTATUET4("rabbitstatuet4", "BlockrabbitStatuet4"),
		SHEEPSTATUET4("sheepstatuet4", "BlocksheepStatuet4"),
		SHEEPSHAVENSTATUET4("sheepshavenstatuet4", "BlocksheepshavenStatuet4"),
		BABYZOMBIESTATUET4("babyzombiestatuet4", "BlockBabyZombieStatuet4"),
		FLOODSTATUET4("floodstatuet4", "BlockFloodStatuet4");
		
		private String unlocalisedName;
		private String registryName;
		
		StatuesBlocks(String unlocalisedName, String registryName) {
			this.unlocalisedName = unlocalisedName;
			this.registryName = registryName;
		}
		
		public String getUnlocalisedName() {
			return unlocalisedName;
		}
		
		public String getRegistryName() {
			return registryName;
		}
	}
	
	public static enum StatuesItems {
		
		ROYALNUGGET("royalnugget", "itemroyalnugget"),
		STATUECORE("statuecore", "itemstatuecore");
		
		private String unlocalisedName;
		private String registryName;
		
		StatuesItems(String unlocalisedName, String registryName) {
			this.unlocalisedName = unlocalisedName;
			this.registryName = registryName;
		}
		
		public String getUnlocalisedName() {
			return unlocalisedName;
		}
		
		public String getRegistryName() {
			return registryName;
		}
	}
}
