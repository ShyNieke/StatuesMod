package com.svennieke.statues;

public class Reference {
	public static final String MOD_ID = "statues";
	public static final String MOD_NAME = "Statues";
	public static final String VERSION = "0.6";
	public static final String ACCEPTED_VERSIONS = "[1.11.2]";
			
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
		SNOWGOLEMSTATUE("snowgolemstatue", "blocksnowgolemstatue");
		
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
		
		ROYALNUGGET("royalnugget", "itemroyalnugget");
		
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
