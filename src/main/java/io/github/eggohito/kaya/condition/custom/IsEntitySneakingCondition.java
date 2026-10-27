package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.context.parameter.EntityContextParameter;
import io.github.eggohito.kaya.provider.custom.entity.ContextEntityProvider;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;

public record IsEntitySneakingCondition(EntityProvider entity) implements Condition {

	public static final MapCodec<IsEntitySneakingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(IsEntitySneakingCondition::entity))
		.apply(instance, IsEntitySneakingCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsEntitySneakingCondition> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, IsEntitySneakingCondition::entity,
		IsEntitySneakingCondition::new
	);

	public IsEntitySneakingCondition(EntityContextParameter entity) {
		this(new ContextEntityProvider(entity));
	}

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_ENTITY_SNEAKING;
	}

	@Override
	public boolean test(Context context) {

		try {
			return entity().getValue(context.forChild(".entity"))
				.stream()
				.filter(entity -> context.visitor().push(this))
				.anyMatch(Entity::isShiftKeyDown);
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
