package io.github.eggohito.kaya.provider.custom.vec3;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.Vec3ContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaVec3ProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextVec3Provider(Vec3ContextParameter parameter) implements Vec3Provider {

	public static final MapCodec<ContextVec3Provider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.VEC3.codec().fieldOf("parameter").forGetter(ContextVec3Provider::parameter))
		.apply(instance, ContextVec3Provider::new)
	);

	public static final Codec<ContextVec3Provider> INLINE_CODEC = KayaContextParameterTypes.VEC3.codec().xmap(
		ContextVec3Provider::new,
		ContextVec3Provider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextVec3Provider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.VEC3.streamCodec(), ContextVec3Provider::parameter,
		ContextVec3Provider::new
	);

	@Override
	public @NotNull Vec3Provider.Type<?> getType() {
		return KayaVec3ProviderTypes.CONTEXT;
	}

	@Override
	public Optional<Vec3> getValue(Context context) {

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
