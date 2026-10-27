package io.github.eggohito.kaya.provider.custom.direction;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.DirectionContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaDirectionProviderTypes;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextDirectionProvider(DirectionContextParameter parameter) implements DirectionProvider {

	public static final MapCodec<ContextDirectionProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.DIRECTION.codec().fieldOf("parameter").forGetter(ContextDirectionProvider::parameter))
		.apply(instance, ContextDirectionProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextDirectionProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.DIRECTION.streamCodec(), ContextDirectionProvider::parameter,
		ContextDirectionProvider::new
	);

	@Override
	public DirectionProvider.@NotNull Type<?> getType() {
		return KayaDirectionProviderTypes.CONTEXT;
	}

	@Override
	public Optional<Direction> getValue(Context context) {

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
