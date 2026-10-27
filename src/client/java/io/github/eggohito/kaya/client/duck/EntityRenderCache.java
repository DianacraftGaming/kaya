package io.github.eggohito.kaya.client.duck;

import io.github.eggohito.kaya.duck.internal.EntityCache;

/**
 *  An interface injected to {@link net.minecraft.client.renderer.entity.state.EntityRenderState} for storing a reference
 *  to the entity being rendered, and a packed color value (for overriding colors)
 */
public interface EntityRenderCache extends EntityCache {

	default int kaya$getColor() {
		return -1;
	}

	default void kaya$setColor(int color) {

	}

}
