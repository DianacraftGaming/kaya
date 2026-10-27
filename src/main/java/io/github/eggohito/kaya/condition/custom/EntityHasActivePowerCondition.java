package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.power.PowerIdentifier;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record EntityHasActivePowerCondition(PowerIdentifier power, EntityProvider entity) implements Condition {

	public static final MapCodec<EntityHasActivePowerCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		PowerIdentifier.CODEC.fieldOf("power").forGetter(EntityHasActivePowerCondition::power),
		EntityProvider.CODEC.fieldOf("entity").forGetter(EntityHasActivePowerCondition::entity)
	).apply(instance, EntityHasActivePowerCondition::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, EntityHasActivePowerCondition> STREAM_CODEC = StreamCodec.composite(
		PowerIdentifier.STREAM_CODEC, EntityHasActivePowerCondition::power,
		EntityProvider.STREAM_CODEC, EntityHasActivePowerCondition::entity,
		EntityHasActivePowerCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.ENTITY_HAS_ACTIVE_POWER;
	}

	@Override
	public boolean test(Context context) {

		try {
			return context.visitor().push(this) && entity().getValue(context.forChild(".entity"))
				.flatMap(Powers::getOptional)
				.flatMap(powers -> powers.getOptionalInstance(power()))
				.filter(instance -> instance.isActive(context.forChild(".power")))
				.isPresent();
		}

		finally {
			context.visitor().pop(this);
		}

	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		power().validate(validator.forChild(".power"));
		entity().validate(validator.forChild(".entity"));
	}

}
