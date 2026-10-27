package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.power.Power;
import io.github.eggohito.kaya.power.PowerIdentifier;
import io.github.eggohito.kaya.power.custom.misc.CooldownPower;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;

public record TriggerPowerCooldownAction(PowerIdentifier power, EntityProvider entity) implements Action {

	public static final MapCodec<TriggerPowerCooldownAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		PowerIdentifier.CODEC.fieldOf("power").forGetter(TriggerPowerCooldownAction::power),
		EntityProvider.CODEC.fieldOf("entity").forGetter(TriggerPowerCooldownAction::entity)
	).apply(instance, TriggerPowerCooldownAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, TriggerPowerCooldownAction> STREAM_CODEC = StreamCodec.composite(
		PowerIdentifier.STREAM_CODEC, TriggerPowerCooldownAction::power,
		EntityProvider.STREAM_CODEC, TriggerPowerCooldownAction::entity,
		TriggerPowerCooldownAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.TRIGGER_POWER_COOLDOWN;
	}

	@Override
	public void execute(Context context) {

		Entity entity = entity().getValue(context.forChild(".entity")).orElse(null);
		Power.Instance<?> instance = Powers.getOptional(entity)
			.map(powers -> powers.getInstance(this.power()))
			.orElse(null);

		if (entity != null && instance instanceof CooldownPower.Instance<?> cooldownInstance) {
			cooldownInstance.trigger(entity);
		}

	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		power().validate(validator.forChild(".power"), CooldownPower.class, () -> power().asDisplayString() + " doesn't have a cooldown!");
		entity().validate(validator.forChild(".entity"));
	}

}
