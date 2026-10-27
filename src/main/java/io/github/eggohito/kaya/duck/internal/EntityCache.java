package io.github.eggohito.kaya.duck.internal;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EntityCache {

	@Nullable
	default Entity kaya$getEntity() {
		return null;
	}

	default void kaya$setEntity(@Nullable Entity entity) {

	}

}
