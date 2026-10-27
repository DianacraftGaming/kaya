package io.github.eggohito.kaya.duck.internal;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

public interface ServerAccess {

	MinecraftServer kaya$getServer();

	@ApiStatus.Internal
	default void kaya$setServer(MinecraftServer server) {

	}

}
