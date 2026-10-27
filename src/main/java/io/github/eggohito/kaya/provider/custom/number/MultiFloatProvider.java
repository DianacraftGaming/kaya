package io.github.eggohito.kaya.provider.custom.number;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidatable;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.util.FloatBiFunction;
import io.github.eggohito.kaya.util.MapCodecUtil;
import io.github.eggohito.kaya.util.MiscUtil;
import io.github.eggohito.kaya.util.StreamCodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface MultiFloatProvider extends FloatProvider {

	List<FloatProvider> values();

	@Override
	default void validate(ContextValidator validator) {
		FloatProvider.super.validate(validator);
		ContextValidatable.validate(values(), validator, index -> ".values[" + index + "]");
	}

	default Optional<Float> iterateAndProcess(Context context, FloatBiFunction processor) {

		MutableFloat result = new MutableFloat();
		MutableBoolean init = new MutableBoolean(false);

		MiscUtil.iterateList(
			values(),
			(index, provider) -> {

				Context valueContext = context.forChild(".values[" + index + "]");
				float value = provider.getFloat(valueContext);

				if (!valueContext.hasProblems()) {

					if (init.isTrue()) {
						result.setValue(processor.apply(result.floatValue(), value));
					}

					else {
						result.setValue(value);
					}

					init.setTrue();

				}

			}
		);

		return init.isTrue()
			? Optional.of(result.floatValue())
			: Optional.empty();

	}

	static <M extends MultiFloatProvider> MapCodec<M> mapCodec(Function<List<FloatProvider>, M> constructor) {
		return MapCodecUtil.lazy(() -> RecordCodecBuilder.mapCodec(instance -> instance
			.group(FloatProvider.CODEC.listOf().fieldOf("values").forGetter(MultiFloatProvider::values))
			.apply(instance, constructor)
		));
	}

	static <M extends MultiFloatProvider> StreamCodec<RegistryFriendlyByteBuf, M> streamCodec(Function<List<FloatProvider>, M> constructor) {
		return StreamCodecUtil.lazy(() -> StreamCodec.composite(
			FloatProvider.STREAM_CODEC.apply(ByteBufCodecs.list()), MultiFloatProvider::values,
			constructor
		));
	}

}
