package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record AbsoluteIntProvider(IntProvider value) implements IntProvider {

	public static final MapCodec<AbsoluteIntProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(IntProvider.CODEC.fieldOf("value").forGetter(AbsoluteIntProvider::value))
		.apply(instance, AbsoluteIntProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, AbsoluteIntProvider> STREAM_CODEC = StreamCodec.composite(
		IntProvider.STREAM_CODEC, AbsoluteIntProvider::value,
		AbsoluteIntProvider::new
	);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.ABSOLUTE;
	}

	@Override
	public Optional<Integer> getValue(Context context) {
		return value().getValue(context.forChild(".value")).map(Math::abs);
	}

	@Override
	public void validate(ContextValidator validator) {
		IntProvider.super.validate(validator);
		value().validate(validator.forChild(".value"));
	}

}
