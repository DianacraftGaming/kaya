package io.github.eggohito.kaya.client.duck.internal;

import io.github.eggohito.kaya.duck.internal.CommandStorageHolder;

public interface ClientCommandStorageHolder extends CommandStorageHolder {

	default void kaya$clear() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

}
