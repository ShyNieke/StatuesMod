package com.svennieke.statues;

public class Reference {
	public static final String MOD_ID = "statues";
	public static final String MOD_NAME = "Statues";
	public static final String VERSION = "0.7.2";
	public static final String ACCEPTED_VERSIONS = "[1.10.2]";
	public static final String DEPENDENCIES = "after:Baubles;";
			
	public static final String CLIENT_PROXY_CLASS = "com.svennieke.statues.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.svennieke.statues.proxy.ServerProxy";
	
	public static enum StatuesItems {
		
		ROYALNUGGET("royalnugget", "itemroyalnugget"),
		STATUECORE("statuecore", "itemstatuecore"),
		MOOSHROOMSOUP("mooshroomsoup", "itemmooshroomsoup");
		
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
