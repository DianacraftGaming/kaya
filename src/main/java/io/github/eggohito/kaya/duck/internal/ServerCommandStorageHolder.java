package io.github.eggohito.kaya.duck.internal;

import net.minecraft.server.level.ServerPlayer;

public interface ServerCommandStorageHolder extends CommandStorageHolder {

	default void kaya$sendAll(ServerPlayer recipient) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

}
