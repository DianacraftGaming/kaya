package io.github.eggohito.kaya.provider.custom.string;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.provider.KayaStringProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record FromIntStringProvider(IntProvider value) implements StringProvider {

	public static final MapCodec<FromIntStringProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(IntProvider.CODEC.fieldOf("value").forGetter(FromIntStringProvider::value))
		.apply(instance, FromIntStringProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, FromIntStringProvider> STREAM_CODEC = StreamCodec.composite(
		IntProvider.STREAM_CODEC, FromIntStringProvider::value,
		FromIntStringProvider::new
	);

	@Override
	public @NotNull StringProvider.Type<?> getType() {
		return KayaStringProviderTypes.FROM_INT;
	}

	@Override
	public Optional<String> getValue(Context context) {

		Context valueContext = context.forChild(".value");
		int value = value().getInt(valueContext);

		return valueContext.hasProblems()
			? Optional.empty()
			: Optional.of(Integer.toString(value));

	}

	@Override
	public void validate(ContextValidator validator) {
		StringProvider.super.validate(validator);
		value().validate(validator.forChild(".value"));
	}

}
