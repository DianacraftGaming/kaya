package io.github.eggohito.kaya.duck;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public interface MovingEntity {

	Vec3 kaya$getVelocity();

	default void kaya$setVelocity(Vec3 velocity) {

	}

	default void kaya$setVelocity(double x, double y, double z) {
		this.kaya$setVelocity(new Vec3(x, y, z));
	}

	default double kaya$getVelocityMagnitudeSqr() {
		Vec3 velocity = this.kaya$getVelocity();
		return Mth.lengthSquared(velocity.x(), velocity.y(), velocity.z());
	}

	default double kaya$getHorizontalVelocityMagnitudeSqr() {
		Vec3 velocity = this.kaya$getVelocity();
		return velocity.x() * velocity.x() + velocity.z() * velocity.z();
	}

	default double kaya$getVerticalVelocityMagnitudeSqr() {
		Vec3 velocity = this.kaya$getVelocity();
		return velocity.y() * velocity.y();
	}

}
