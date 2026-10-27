package io.github.eggohito.kaya.duck.internal;

import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.power.Power;
import net.minecraft.world.inventory.TransientCraftingContainer;

import java.util.Map;

public interface PowerCrafting extends EntityCache {

	default TransientCraftingContainer kaya$getInventory() {
		return null;
	}

	default void kaya$setInventory(TransientCraftingContainer inventory) {

	}

	default Map<? extends Power.Instance<?>, Context> kaya$getModifyingInstances() {
		return Map.of();
	}

	default void kaya$setModifyingInstances(Map<? extends Power.Instance<?>, Context> modifyingInstances) {

	}

}
