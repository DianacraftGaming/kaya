package io.github.eggohito.kaya.provider.custom.number.floats;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.provider.KayaFloatProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record NegateFloatProvider(FloatProvider value) implements FloatProvider {

	public static final MapCodec<NegateFloatProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(FloatProvider.CODEC.fieldOf("value").forGetter(NegateFloatProvider::value))
		.apply(instance, NegateFloatProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, NegateFloatProvider> STREAM_CODEC = StreamCodec.composite(
		FloatProvider.STREAM_CODEC, NegateFloatProvider::value,
		NegateFloatProvider::new
	);

	@Override
	public @NotNull FloatProvider.Type<?> getType() {
		return KayaFloatProviderTypes.NEGATE;
	}

	@Override
	public Optional<Float> getValue(Context context) {
		return value().getValue(context.forChild(".value")).map(value -> -value);
	}

	@Override
	public void validate(ContextValidator validator) {
		FloatProvider.super.validate(validator);
		value().validate(validator.forChild(".value"));
	}

}
