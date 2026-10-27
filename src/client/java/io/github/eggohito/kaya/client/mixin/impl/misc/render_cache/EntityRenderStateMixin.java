package io.github.eggohito.kaya.client.mixin.impl.misc.render_cache;

import io.github.eggohito.kaya.client.duck.EntityRenderCache;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.ref.WeakReference;
import java.util.Optional;

@Mixin(EntityRenderState.class)
public abstract class EntityRenderStateMixin implements EntityRenderCache {

	@Unique
	protected WeakReference<Integer> kaya$color = new WeakReference<>(null);

	@Unique
	protected WeakReference<Entity> kaya$entity = new WeakReference<>(null);

	@Override
	public int kaya$getColor() {
		return Optional.ofNullable(kaya$color.get()).orElse(-1);
	}

	@Override
	public void kaya$setColor(int color) {

		if (color == -1) {
			this.kaya$color.clear();
		}

		else {
			this.kaya$color = new WeakReference<>(color);
		}

	}

	@Override
	public Entity kaya$getEntity() {
		return kaya$entity.get();
	}

	@Override
	public void kaya$setEntity(@Nullable Entity entity) {

		if (entity == null) {
			this.kaya$entity.clear();
		}

		else {
			this.kaya$entity = new WeakReference<>(entity);
		}

	}

}
