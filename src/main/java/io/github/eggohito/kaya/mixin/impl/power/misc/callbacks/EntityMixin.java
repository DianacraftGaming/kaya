package io.github.eggohito.kaya.mixin.impl.power.misc.callbacks;

import io.github.eggohito.kaya.attachment.entity.PowersAttachment;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.registry.attachment.KayaEntityAttachments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnstableApiUsage")
@Mixin(Entity.class)
public abstract class EntityMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	void onPowersAttachmentChanged(EntityType<?> entityType, Level level, CallbackInfo ci) {
		Entity thisAsEntity = (Entity) (Object) this;
		thisAsEntity.onAttachedSet(KayaEntityAttachments.POWERS).register(Powers.ID, (oldValue, newValue) -> PowersAttachment.onChanged(thisAsEntity, oldValue, newValue));
	}

	@Inject(method = "baseTick", at = @At("TAIL"))
	void onPowersTick(CallbackInfo ci) {

		Entity thisAsEntity = (Entity) (Object) this;
		PowersAttachment attachment = thisAsEntity.getAttached(KayaEntityAttachments.POWERS);

		if (attachment == null) {
			return;
		}

		for (var instance : attachment.instances().values()) {

			if (instance.shouldTick(thisAsEntity)) {
				instance.onTick(thisAsEntity);
			}

		}

	}

}
