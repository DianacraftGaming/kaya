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

public record IsEntityFallFlyingCondition(EntityProvider entity) implements Condition {

	public static final MapCodec<IsEntityFallFlyingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(IsEntityFallFlyingCondition::entity))
		.apply(instance, IsEntityFallFlyingCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsEntityFallFlyingCondition> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, IsEntityFallFlyingCondition::entity,
		IsEntityFallFlyingCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_ENTITY_FALL_FLYING;
	}

	@Override
	public boolean test(Context context) {
		return entity().getValue(context.forChild(".entity"))
			.filter(LivingEntity.class::isInstance)
			.map(LivingEntity.class::cast)
			.stream()
			.anyMatch(LivingEntity::isFallFlying);
	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
	}

}
