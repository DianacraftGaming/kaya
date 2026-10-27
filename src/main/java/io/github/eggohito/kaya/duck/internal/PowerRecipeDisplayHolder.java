package io.github.eggohito.kaya.duck.internal;

import io.github.eggohito.kaya.power.PowerIdentifier;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.level.ServerPlayer;

public interface PowerRecipeDisplayHolder {

	default Int2ObjectMap<PowerIdentifier> kaya$getPowerIdsByIndex() {
		return new Int2ObjectOpenHashMap<>();
	}

	default void kaya$setPowerIdsByIndex(Int2ObjectMap<PowerIdentifier> powerIdsByIndex) {

	}

	default void kaya$sendAll(ServerPlayer recipient) {

	}

}
