package io.github.eggohito.kaya.provider.custom.string;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.provider.KayaStringProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record FromFloatStringProvider(FloatProvider value) implements StringProvider {

	public static final MapCodec<FromFloatStringProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(FloatProvider.CODEC.fieldOf("value").forGetter(FromFloatStringProvider::value))
		.apply(instance, FromFloatStringProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, FromFloatStringProvider> STREAM_CODEC = StreamCodec.composite(
		FloatProvider.STREAM_CODEC, FromFloatStringProvider::value,
		FromFloatStringProvider::new
	);

	@Override
	public @NotNull StringProvider.Type<?> getType() {
		return KayaStringProviderTypes.FROM_FLOAT;
	}

	@Override
	public Optional<String> getValue(Context context) {

		Context valueContext = context.forChild(".value");
		float value = value().getFloat(valueContext);

		return valueContext.hasProblems()
			? Optional.empty()
			: Optional.of(Float.toString(value));

	}

	@Override
	public void validate(ContextValidator validator) {
		StringProvider.super.validate(validator);
		value().validate(validator.forChild(".value"));
	}

}
