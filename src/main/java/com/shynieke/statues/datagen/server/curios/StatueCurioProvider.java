package com.shynieke.statues.datagen.server.curios;

import com.shynieke.statues.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class StatueCurioProvider extends CuriosDataProvider {
	public StatueCurioProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(Reference.MOD_ID, output, registries);
	}

	@Override
	public void generate(HolderLookup.Provider registries) {
		createSlot("statue").size(1).icon(Reference.modLoc("slot/core"));
		createEntities("add_slot_statue")
				.addPlayer()
				.addSlots("statue");
	}
}
