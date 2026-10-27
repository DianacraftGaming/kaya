package io.github.eggohito.kaya.mixin.impl.power.custom.modify_entity_type_tag;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.eggohito.kaya.duck.internal.EntityCache;
import io.github.eggohito.kaya.power.custom.ModifyEntityTypeTagPower;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.lang.ref.WeakReference;
import java.util.Optional;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin implements EntityCache {

	@Unique
	final ThreadLocal<WeakReference<Entity>> kaya$currentEntity = new ThreadLocal<>();

	@Override
	public @Nullable Entity kaya$getEntity() {
		return Optional.ofNullable(kaya$currentEntity.get())
			.flatMap(reference -> Optional.ofNullable(reference.get()))
			.orElse(null);
	}

	@Override
	public void kaya$setEntity(Entity entity) {

		if (entity == null) {
			this.kaya$currentEntity.remove();
		}

		else {
			this.kaya$currentEntity.set(new WeakReference<>(entity));
		}

	}

	@ModifyReturnValue(method = "is(Lnet/minecraft/tags/TagKey;)Z", at = @At("RETURN"))
	boolean tagProxy(boolean original, TagKey<EntityType<?>> tag) {

		try (ModifyEntityTypeTagPower.VISITOR) {
			return original || ModifyEntityTypeTagPower.modify(this.kaya$getEntity(), tag);
		}

	}

	@ModifyReturnValue(method = "is(Lnet/minecraft/core/HolderSet;)Z", at = @At("RETURN"))
	boolean directTagProxy(boolean original, HolderSet<EntityType<?>> directTag) {

		try (ModifyEntityTypeTagPower.VISITOR) {
			return original || ModifyEntityTypeTagPower.modify(this.kaya$getEntity(), directTag);
		}

	}

}
