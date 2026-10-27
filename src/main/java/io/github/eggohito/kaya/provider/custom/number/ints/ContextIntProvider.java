package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.IntContextParameter;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextIntProvider(IntContextParameter parameter) implements IntProvider {

	public static final MapCodec<ContextIntProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.INT.codec().fieldOf("parameter").forGetter(ContextIntProvider::parameter))
		.apply(instance, ContextIntProvider::new)
	);

	public static final Codec<ContextIntProvider> INLINE_CODEC = KayaContextParameterTypes.INT.codec().xmap(
		ContextIntProvider::new,
		ContextIntProvider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextIntProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.INT.streamCodec(), ContextIntProvider::parameter,
		ContextIntProvider::new
	);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.CONTEXT;
	}

	@Override
	public Optional<Integer> getValue(Context context) {

		if (!context.hasParameter(parameter())) {
			context.reportProblem("Parameter \"" + parameter() + "\" is not provided in the context!");
		}

		return context.getOptional(parameter());

	}

	@Override
	public Set<ContextParameter<?>> getRequiredParameters() {
		return Set.of(parameter());
	}

}
