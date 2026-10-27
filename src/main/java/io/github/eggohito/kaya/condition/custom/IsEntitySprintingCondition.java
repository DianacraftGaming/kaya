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
import net.minecraft.world.entity.Entity;

public record IsEntitySprintingCondition(EntityProvider entity) implements Condition {

	public static final MapCodec<IsEntitySprintingCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(IsEntitySprintingCondition::entity))
		.apply(instance, IsEntitySprintingCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsEntitySprintingCondition> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, IsEntitySprintingCondition::entity,
		IsEntitySprintingCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_ENTITY_SPRINTING;
	}

	@Override
	public boolean test(Context context) {

		try {
			return entity().getValue(context.forChild(".entity"))
				.stream()
				.filter(entity -> context.visitor().push(this))
				.anyMatch(Entity::isSprinting);
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
