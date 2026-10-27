package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record DynamicCondition(BooleanProvider value) implements Condition {

	public static final MapCodec<DynamicCondition> CODEC = BooleanProvider.CODEC.fieldOf("value").xmap(
		DynamicCondition::new,
		DynamicCondition::value
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, DynamicCondition> STREAM_CODEC = BooleanProvider.STREAM_CODEC.map(
		DynamicCondition::new,
		DynamicCondition::value
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.DYNAMIC;
	}

	@Override
	public boolean test(Context context) {
		return value().getBoolean(context.forChild(".value"));
	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		value().validate(validator.forChild(".value"));
	}

}
