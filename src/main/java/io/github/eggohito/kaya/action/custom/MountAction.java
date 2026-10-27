package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.provider.custom.bool.ConstantBooleanProvider;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import java.util.Set;

public record MountAction(EntityProvider vehicle, EntityProvider passenger, BooleanProvider force) implements Action {

	public static final MapCodec<MountAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		EntityProvider.CODEC.fieldOf("vehicle").forGetter(MountAction::vehicle),
		EntityProvider.CODEC.fieldOf("passenger").forGetter(MountAction::passenger),
		BooleanProvider.CODEC.optionalFieldOf("force", new ConstantBooleanProvider(false)).forGetter(MountAction::force)
	).apply(instance, MountAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, MountAction> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, MountAction::vehicle,
		EntityProvider.STREAM_CODEC, MountAction::passenger,
		BooleanProvider.STREAM_CODEC, MountAction::force,
		MountAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.MOUNT;
	}

	@Override
	public void execute(Context context) {

		if (context.level().isClientSide()) {
			return;
		}

		Entity vehicle = vehicle().getValue(context.forChild(".vehicle")).orElse(null);
		Entity passenger = passenger().getValue(context.forChild(".passenger")).orElse(null);

		if (vehicle != null && passenger != null && passenger.getSelfAndPassengers().noneMatch(entity -> entity == vehicle)) {

			if (vehicle.level() != passenger.level()) {
				passenger.teleportTo((ServerLevel) vehicle.level(), vehicle.getX(), vehicle.getY(), vehicle.getZ(), Set.of(), 0.0F, 0.0F, true);
			}

			passenger.startRiding(vehicle, force().getBoolean(context.forChild(".force")));

		}

	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		vehicle().validate(validator.forChild(".vehicle"));
		passenger().validate(validator.forChild(".passenger"));
		force().validate(validator.forChild(".force"));
	}

}
