package io.github.eggohito.kaya.mixin.impl.misc.moving;

import io.github.eggohito.kaya.duck.MovingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements MovingEntity {

	@Shadow
	public abstract Vec3 position();

	@Shadow
	public abstract double getX();

	@Shadow
	public abstract double getY();

	@Shadow
	public abstract double getZ();

	@Unique
	private Vec3 kaya$prevPos;

	@Unique
	private Vec3 kaya$velocity = Vec3.ZERO;

	@Override
	public Vec3 kaya$getVelocity() {
		return kaya$velocity;
	}

	@Override
	public void kaya$setVelocity(Vec3 velocity) {
		this.kaya$velocity = velocity;
	}

	@Inject(method = "baseTick", at = @At("TAIL"))
	private void updateVelocity(CallbackInfo ci) {

		if (kaya$prevPos != null) {

			double dx = this.getX() - this.kaya$prevPos.x;
			double dy = this.getY() - this.kaya$prevPos.y;
			double dz = this.getZ() - this.kaya$prevPos.z;

			this.kaya$setVelocity(dx, dy, dz);

		}

		this.kaya$prevPos = this.position();

	}

}
