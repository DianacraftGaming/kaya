package io.github.eggohito.kaya.mixin.impl.power.custom.modify_attribute;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.eggohito.kaya.duck.internal.CustomClearable;
import io.github.eggohito.kaya.duck.internal.EntityCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttributeSupplier.class)
public abstract class AttributeSupplierMixin implements EntityCache, CustomClearable {

	@Unique
	private final ThreadLocal<Entity> kaya$entity = new ThreadLocal<>();

	@Override
	public @Nullable Entity kaya$getEntity() {
		return kaya$entity.get();
	}

	@Override
	public void kaya$setEntity(@Nullable Entity entity) {

		if (entity == null) {
			this.kaya$entity.remove();
		}

		else {
			this.kaya$entity.set(entity);
		}

	}

	@Override
	public void kaya$clear() {
		this.kaya$entity.remove();
	}

	@ModifyReturnValue(method = "getAttributeInstance", at = @At("RETURN"))
	AttributeInstance passEntityToAttribute(AttributeInstance original) {

		if (original instanceof EntityCache entityCache) {
			entityCache.kaya$setEntity(this.kaya$getEntity());
		}

		return original;

	}

}
