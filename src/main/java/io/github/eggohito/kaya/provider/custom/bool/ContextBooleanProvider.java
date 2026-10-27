package io.github.eggohito.kaya.provider.custom.bool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.BooleanContextParameter;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaBooleanProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextBooleanProvider(BooleanContextParameter parameter) implements BooleanProvider {

	public static final MapCodec<ContextBooleanProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.BOOLEAN.codec().fieldOf("parameter").forGetter(ContextBooleanProvider::parameter))
		.apply(instance, ContextBooleanProvider::new)
	);

	public static final Codec<ContextBooleanProvider> INLINE_CODEC = KayaContextParameterTypes.BOOLEAN.codec().xmap(
		ContextBooleanProvider::new,
		ContextBooleanProvider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextBooleanProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.BOOLEAN.streamCodec(), ContextBooleanProvider::parameter,
		ContextBooleanProvider::new
	);

	@Override
	public @NotNull BooleanProvider.Type<?> getType() {
		return KayaBooleanProviderTypes.CONTEXT;
	}

	@Override
	public Optional<Boolean> getValue(Context context) {

		if (!context.hasParameter(parameter())) {
			context.reportProblem("Parameter \"" + parameter.name() + "\" is not provided in the context!");
		}

		return context.getOptional(parameter());

	}

	@Override
	public Set<ContextParameter<?>> getRequiredParameters() {
		return Set.of(parameter());
	}

}
