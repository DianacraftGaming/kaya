package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.WeightedValueProvider;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

public record WeightedIntProvider(WeightedList<IntProvider> entries) implements IntProvider, WeightedValueProvider<Integer, IntProvider> {

	public static final MapCodec<WeightedIntProvider> CODEC = WeightedValueProvider.mapCodec(IntProvider.CODEC, WeightedIntProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedIntProvider> STREAM_CODEC = WeightedValueProvider.streamCodec(IntProvider.STREAM_CODEC, WeightedIntProvider::new);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.WEIGHTED;
	}

}
