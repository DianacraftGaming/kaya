package io.github.eggohito.kaya.mixin.impl.power.custom.modify_invisibility;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.eggohito.kaya.power.Power;
import io.github.eggohito.kaya.power.custom.ModifyInvisibilityPower;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract Level level();

	@Shadow
	public abstract Vec3 position();

	@Unique
	private Entity kaya$thisAsEntity() {
		return (Entity) (Object) this;
	}

	@ModifyReturnValue(method = "isInvisible", at = @At("RETURN"))
	boolean invisibleProxy(boolean original) {

		try (ModifyInvisibilityPower.VISITOR) {
			return original || ModifyInvisibilityPower.modify(kaya$thisAsEntity(), null, Power.Instance::isActive, () -> false);
		}

	}

	@WrapOperation(method = "isInvisibleTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInvisible()Z"))
	boolean invisibleToProxy(Entity entity, Operation<Boolean> original, Player viewer) {

		try (ModifyInvisibilityPower.VISITOR) {

			if (viewer == null) {
				return original.call(entity);
			}

			else {
				return ModifyInvisibilityPower.modify(kaya$thisAsEntity(), viewer, ModifyInvisibilityPower.Instance::isInvisibleTo, () -> original.call(entity));
			}

		}

	}

}
