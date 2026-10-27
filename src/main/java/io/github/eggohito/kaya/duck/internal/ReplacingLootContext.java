package io.github.eggohito.kaya.duck.internal;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public interface ReplacingLootContext extends ContextKeySetHolder {

	default boolean kaya$isReplaced(ResourceKey<LootTable> key) {
		throw new AssertionError("Implemented via mixin");
	}

	default void kaya$setReplaced(ResourceKey<LootTable> key) {
		throw new AssertionError("Implemented via mixin");
	}

}
