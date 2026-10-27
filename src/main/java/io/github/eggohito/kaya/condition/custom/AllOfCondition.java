package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidatable;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.ListIterator;

public record AllOfCondition(List<Condition> conditions) implements Condition {

	public static final MapCodec<AllOfCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(Condition.CODEC.listOf().fieldOf("conditions").forGetter(AllOfCondition::conditions))
		.apply(instance, AllOfCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, AllOfCondition> STREAM_CODEC = StreamCodec.composite(
		Condition.STREAM_CODEC.apply(ByteBufCodecs.list()), AllOfCondition::conditions,
		AllOfCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.ALL_OF;
	}

	@Override
	public boolean test(Context context) {

		ListIterator<Condition> listIterator = conditions().listIterator();

		while (listIterator.hasNext()) {

			Context conditionContext = context.forChild(".conditions[" + listIterator.nextIndex() + "]");
			Condition condition = listIterator.next();

			if (!condition.test(conditionContext)) {
				return false;
			}

		}

		return true;

	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		ContextValidatable.validate(conditions(), validator, index -> ".conditions[" + index + "]");
	}

}
