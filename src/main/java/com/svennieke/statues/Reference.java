package com.svennieke.statues;

public class Reference {
	public static final String MOD_ID = "statues";
	public static final String MOD_NAME = "Statues";
	public static final String MOD_PREFIX = MOD_ID + ":";
	public static final String VERSION = "0.8.9";
	public static final String ACCEPTED_VERSIONS = "[1.12]";
	public static final String DEPENDENCIES = "before:crafttweaker;after:baubles;after:veinminer;after:theoneprobe;";
			
	public static final String CLIENT_PROXY_CLASS = "com.svennieke.statues.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.svennieke.statues.proxy.ServerProxy";
}