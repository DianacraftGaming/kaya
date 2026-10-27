package io.github.eggohito.kaya.provider.custom.number.floats;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.FloatContextParameter;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaFloatProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextFloatProvider(FloatContextParameter parameter) implements FloatProvider {

	public static final MapCodec<ContextFloatProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.FLOAT.codec().fieldOf("parameter").forGetter(ContextFloatProvider::parameter))
		.apply(instance, ContextFloatProvider::new)
	);

	public static final Codec<ContextFloatProvider> INLINE_CODEC = KayaContextParameterTypes.FLOAT.codec().xmap(
		ContextFloatProvider::new,
		ContextFloatProvider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextFloatProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.FLOAT.streamCodec(), ContextFloatProvider::parameter,
		ContextFloatProvider::new
	);

	@Override
	public @NotNull FloatProvider.Type<?> getType() {
		return KayaFloatProviderTypes.CONTEXT;
	}

	@Override
	public Optional<Float> getValue(Context context) {

		if (!context.hasParameter(parameter())) {
			context.reportProblem("Parameter \"" + parameter().name() + "\" is not provided in the context!");
		}

		return context.getOptional(parameter());

	}

	@Override
	public Set<ContextParameter<?>> getRequiredParameters() {
		return Set.of(parameter());
	}

}
