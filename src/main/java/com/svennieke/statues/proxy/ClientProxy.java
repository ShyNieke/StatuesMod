package com.svennieke.statues.proxy;

import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesColor;

public class ClientProxy extends ServerProxy{

	@Override
	public void Preinit() {
		StatuesBlocks.registerRenders();
	}
	
	@Override
	public void Init() {
		StatuesColor.registerColourHandlers();
	}
}
