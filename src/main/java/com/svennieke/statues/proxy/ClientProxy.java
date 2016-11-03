package com.svennieke.statues.proxy;

import com.svennieke.statues.init.StatuesBlocks;

public class ClientProxy extends ServerProxy{

	@Override
	public void Preinit() {
		StatuesBlocks.registerRenders();
	}
}
