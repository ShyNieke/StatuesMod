package com.svennieke.statues.proxy;

import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesItems;

public class ClientProxy extends ServerProxy{

	@Override
	public void Preinit() {
		StatuesBlocks.registerRenders();
		StatuesItems.registerRenders();
	}
	
	@Override
	public void Init() {
	}
}
