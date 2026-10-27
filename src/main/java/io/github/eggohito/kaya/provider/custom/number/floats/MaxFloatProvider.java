package io.github.eggohito.kaya.provider.custom.number.floats;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.provider.custom.number.MultiFloatProvider;
import io.github.eggohito.kaya.registry.provider.KayaFloatProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record MaxFloatProvider(List<FloatProvider> values) implements MultiFloatProvider {

	public static final MapCodec<MaxFloatProvider> CODEC = MultiFloatProvider.mapCodec(MaxFloatProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, MaxFloatProvider> STREAM_CODEC = MultiFloatProvider.streamCodec(MaxFloatProvider::new);

	@Override
	public @NotNull FloatProvider.Type<?> getType() {
		return KayaFloatProviderTypes.MAX;
	}

	@Override
	public Optional<Float> getValue(Context context) {
		return this.iterateAndProcess(context, Math::max);
	}

}
