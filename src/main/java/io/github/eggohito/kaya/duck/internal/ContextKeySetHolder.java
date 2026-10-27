package io.github.eggohito.kaya.duck.internal;

import net.minecraft.util.context.ContextKeySet;

public interface ContextKeySetHolder {

	default ContextKeySet kaya$getKeySet() {
		throw new AssertionError("Implemented via mixin");
	}

	default void kaya$setKeySet(ContextKeySet keySet) {
		throw new AssertionError("Implemented via mixin");
	}

}
