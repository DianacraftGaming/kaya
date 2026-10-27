package io.github.eggohito.kaya.provider.custom.number.floats;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.provider.ConditionalValueProvider;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.provider.KayaFloatProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record ConditionalFloatProvider(Condition condition, FloatProvider onTrue, FloatProvider onFalse) implements FloatProvider, ConditionalValueProvider<Float, FloatProvider> {

	public static final MapCodec<ConditionalFloatProvider> CODEC = ConditionalValueProvider.mapCodec(FloatProvider.CODEC, ConditionalFloatProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ConditionalFloatProvider> STREAM_CODEC = ConditionalValueProvider.streamCodec(FloatProvider.STREAM_CODEC, ConditionalFloatProvider::new);

	@Override
	public @NotNull FloatProvider.Type<?> getType() {
		return KayaFloatProviderTypes.CONDITIONAL;
	}

}
