package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;

public record IsEntityClimbingCondition(EntityProvider entity) implements Condition {

	public static final MapCodec<IsEntityClimbingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(IsEntityClimbingCondition::entity))
		.apply(instance, IsEntityClimbingCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsEntityClimbingCondition> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, IsEntityClimbingCondition::entity,
		IsEntityClimbingCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_ENTITY_CLIMBING;
	}

	@Override
	public boolean test(Context context) {

		try {
			return entity().getValue(context.forChild(".entity"))
				.stream()
				.filter(LivingEntity.class::isInstance)
				.map(LivingEntity.class::cast)
				.filter(entity -> context.visitor().push(this))
				.anyMatch(LivingEntity::onClimbable);
		}

		finally {
			context.visitor().pop(this);
		}

	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
	}

}
