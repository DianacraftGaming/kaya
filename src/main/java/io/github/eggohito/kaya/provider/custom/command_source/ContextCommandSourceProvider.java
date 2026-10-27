package io.github.eggohito.kaya.provider.custom.command_source;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.CommandSourceContextParameter;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaCommandSourceProviderTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextCommandSourceProvider(CommandSourceContextParameter parameter) implements CommandSourceProvider {

	public static final MapCodec<ContextCommandSourceProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.COMMAND_SOURCE.codec().fieldOf("parameter").forGetter(ContextCommandSourceProvider::parameter))
		.apply(instance, ContextCommandSourceProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextCommandSourceProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.COMMAND_SOURCE.streamCodec(), ContextCommandSourceProvider::parameter,
		ContextCommandSourceProvider::new
	);

	@Override
	public CommandSourceProvider.@NotNull Type<?> getType() {
		return KayaCommandSourceProviderTypes.CONTEXT;
	}

	@Override
	public Optional<CommandSourceStack> getValue(Context context) {

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
