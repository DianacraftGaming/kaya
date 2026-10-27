package io.github.eggohito.kaya.comparison.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.comparison.Comparator;
import io.github.eggohito.kaya.comparison.Comparison;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.KayaComparisonTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record FloatComparison(Comparator comparator, FloatProvider first, FloatProvider second) implements Comparison {

	public static final MapCodec<FloatComparison> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Comparator.CODEC.fieldOf("comparator").forGetter(FloatComparison::comparator),
		FloatProvider.CODEC.fieldOf("first").forGetter(FloatComparison::first),
		FloatProvider.CODEC.fieldOf("second").forGetter(FloatComparison::second)
	).apply(instance, FloatComparison::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, FloatComparison> STREAM_CODEC = StreamCodec.composite(
		Comparator.STREAM_CODEC, FloatComparison::comparator,
		FloatProvider.STREAM_CODEC, FloatComparison::first,
		FloatProvider.STREAM_CODEC, FloatComparison::second,
		FloatComparison::new
	);

	@Override
	public Type<?> type() {
		return KayaComparisonTypes.FLOAT;
	}

	@Override
	public boolean compare(Context context) {
		return comparator().compare(
			first().getFloat(context.forChild(".first")),
			second().getFloat(context.forChild(".second"))
		);
	}

	@Override
	public void validate(ContextValidator validator) {
		Comparison.super.validate(validator);
		first().validate(validator.forChild(".first"));
		second().validate(validator.forChild(".second"));
	}

}
