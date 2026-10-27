package io.github.eggohito.kaya.provider.custom.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.NbtContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaNbtProviderTypes;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextNbtProvider(NbtContextParameter parameter) implements NbtProvider {

	public static final MapCodec<ContextNbtProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.NBT.codec().fieldOf("parameter").forGetter(ContextNbtProvider::parameter))
		.apply(instance, ContextNbtProvider::new)
	);

	public static final Codec<ContextNbtProvider> INLINE_CODEC = KayaContextParameterTypes.NBT.codec().xmap(
		ContextNbtProvider::new,
		ContextNbtProvider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextNbtProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.NBT.streamCodec(), ContextNbtProvider::parameter,
		ContextNbtProvider::new
	);

	@Override
	public @NotNull NbtProvider.Type<?> getType() {
		return KayaNbtProviderTypes.CONTEXT;
	}

	@Override
	public Optional<Tag> getValue(Context context) {

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
