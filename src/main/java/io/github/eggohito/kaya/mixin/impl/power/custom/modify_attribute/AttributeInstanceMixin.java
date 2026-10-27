package io.github.eggohito.kaya.mixin.impl.power.custom.modify_attribute;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.eggohito.kaya.duck.internal.CustomClearable;
import io.github.eggohito.kaya.duck.internal.EntityCache;
import io.github.eggohito.kaya.power.custom.ModifyAttributePower;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttributeInstance.class)
public abstract class AttributeInstanceMixin implements EntityCache, CustomClearable {

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

	//  TODO:   Find a way to optimize this section. Ideally, the value should only be modified on calculation but
	//          that wouldn't really suffice when conditions and/or value providers are used.
	@ModifyReturnValue(method = "getValue", at = @At("RETURN"))
	double modifyAttribute(double original) {
		return ModifyAttributePower.modify((AttributeInstance) (Object) this, original);
	}

}
