package io.github.eggohito.kaya.duck.internal;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.level.storage.loot.LootTable;

public interface KeyableLootTable {

	default ResourceKey<LootTable> kaya$getKey() {
		throw new AssertionError("Implemented via mixin");
	}

	default void kaya$setup(ResourceKey<LootTable> key, ReloadableServerRegistries.Holder holder) {
		throw new AssertionError("Implemented via mixin");
	}

}
