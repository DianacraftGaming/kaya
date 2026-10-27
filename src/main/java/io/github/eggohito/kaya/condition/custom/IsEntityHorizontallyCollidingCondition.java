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

public record IsEntityHorizontallyCollidingCondition(EntityProvider entity) implements Condition {

	public static final MapCodec<IsEntityHorizontallyCollidingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(IsEntityHorizontallyCollidingCondition::entity))
		.apply(instance, IsEntityHorizontallyCollidingCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsEntityHorizontallyCollidingCondition> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, IsEntityHorizontallyCollidingCondition::entity,
		IsEntityHorizontallyCollidingCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_ENTITY_HORIZONTALLY_COLLIDING;
	}

	@Override
	public boolean test(Context context) {
		return entity().getValue(context.forChild(".entity"))
			.stream()
			.anyMatch(entity -> entity.horizontalCollision);
	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
	}

}
