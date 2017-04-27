package com.svennieke.statues;

public class Reference {
	public static final String MOD_ID = "statues";
	public static final String MOD_NAME = "Statues";
	public static final String MOD_PREFIX = "statues:";
	public static final String VERSION = "0.6.1";
	public static final String ACCEPTED_VERSIONS = "[1.11.2]";
	public static final String DEPENDENCIES = "after:Baubles;";
			
	public static final String CLIENT_PROXY_CLASS = "com.svennieke.statues.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.svennieke.statues.proxy.ServerProxy";
	
	public static enum StatuesBlocks {
		SLIMESTATUE("slimestatue", "blockslimestatue"),
		BLAZESTATUE("blazestatue", "blockblazestatue"),
		COWSTATUE("cowstatue", "blockcowstatue"),
		CHICKENSTATUE("chickenstatue", "blockchickenstatue"),
		KINGCLUCKSTATUE("kingcluckstatue", "blockkingcluckstatue"),
		MOOSHROOMSTATUE("mooshroomstatue", "blockmooshroomstatue"),
		CREEPERSTATUE("creeperstatue", "blockcreeperstatue"),
		SNOWGOLEMSTATUE("snowgolemstatue", "blocksnowgolemstatue"),
		PIGSTATUE("pigstatue", "blockpigstatue"),
		RABBITSTATUE("rabbitstatue", "blockrabbitstatue"),
		SHEEPSTATUE("sheepstatue", "blocksheepstatue"),
		SHEEPSHAVENSTATUE("sheepshavenstatue", "blocksheepshavenstatue"),
		BABYZOMBIESTATUE("babyzombiestatue", "blockbabyzombiestatue"),
		FLOODSTATUE("floodstatue", "blockfloodstatue"),
		
		SLIMESTATUET2("slimestatuet2", "blockslimestatuet2"),
		BLAZESTATUET2("blazestatuet2", "blockblazestatuet2"),
		COWSTATUET2("cowstatuet2", "blockcowstatuet2"),
		CHICKENSTATUET2("chickenstatuet2", "blockchickenstatuet2"),
		KINGCLUCKSTATUET2("kingcluckstatuet2", "blockkingcluckstatuet2"),
		MOOSHROOMSTATUET2("mooshroomstatuet2", "blockmooshroomstatuet2"),
		CREEPERSTATUET2("creeperstatuet2", "blockcreeperstatuet2"),
		SNOWGOLEMSTATUET2("snowgolemstatuet2", "blocksnowgolemstatuet2"),
		PIGSTATUET2("pigstatuet2", "blockpigstatuet2"),
		RABBITSTATUET2("rabbitstatuet2", "blockrabbitstatuet2"),
		SHEEPSTATUET2("sheepstatuet2", "blocksheepstatuet2"),
		SHEEPSHAVENSTATUET2("sheepshavenstatuet2", "blocksheepshavenstatuet2"),
		BABYZOMBIESTATUET2("babyzombiestatuet2", "blockbabyzombiestatuet2"),
		FLOODSTATUET2("floodstatuet2", "blockfloodstatuet2"),
		
		SLIMESTATUET3("slimestatuet3", "blockslimestatuet3"),
		BLAZESTATUET3("blazestatuet3", "blockblazestatuet3"),
		COWSTATUET3("cowstatuet3", "blockcowstatuet3"),
		CHICKENSTATUET3("chickenstatuet3", "blockchickenstatuet3"),
		KINGCLUCKSTATUET3("kingcluckstatuet3", "blockkingcluckstatuet3"),
		MOOSHROOMSTATUET3("mooshroomstatuet3", "blockmooshroomstatuet3"),
		CREEPERSTATUET3("creeperstatuet3", "blockcreeperstatuet3"),
		SNOWGOLEMSTATUET3("snowgolemstatuet3", "blocksnowgolemstatuet3"),
		PIGSTATUET3("pigstatuet3", "blockpigstatuet3"),
		RABBITSTATUET3("rabbitstatuet3", "blockrabbitstatuet3"),
		SHEEPSTATUET3("sheepstatuet3", "blocksheepstatuet3"),
		SHEEPSHAVENSTATUET3("sheepshavenstatuet3", "blocksheepshavenstatuet3"),
		BABYZOMBIESTATUET3("babyzombiestatuet3", "blockbabyzombiestatuet3"),
		FLOODSTATUET3("floodstatuet3", "blockfloodstatuet3"),
		
		SLIMESTATUET4("slimestatuet4", "blockslimestatuet4"),
		BLAZESTATUET4("blazestatuet4", "blockblazestatuet4"),
		COWSTATUET4("cowstatuet4", "blockcowstatuet4"),
		CHICKENSTATUET4("chickenstatuet4", "blockchickenstatuet4"),
		KINGCLUCKSTATUET4("kingcluckstatuet4", "blockkingcluckstatuet4"),
		MOOSHROOMSTATUET4("mooshroomstatuet4", "blockmooshroomstatuet4"),
		CREEPERSTATUET4("creeperstatuet4", "blockcreeperstatuet4"),
		SNOWGOLEMSTATUET4("snowgolemstatuet4", "blocksnowgolemstatuet4"),
		PIGSTATUET4("pigstatuet4", "blockpigstatuet4"),
		RABBITSTATUET4("rabbitstatuet4", "blockrabbitstatuet4"),
		SHEEPSTATUET4("sheepstatuet4", "blocksheepstatuet4"),
		SHEEPSHAVENSTATUET4("sheepshavenstatuet4", "blocksheepshavenstatuet4"),
		BABYZOMBIESTATUET4("babyzombiestatuet4", "blockbabyzombiestatuet4"),
		FLOODSTATUET4("floodstatuet4", "blockfloodstatuet4");
		
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