package io.github.eggohito.neo_apoli.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.neo_apoli.provider.WeightedValueProvider;
import io.github.eggohito.neo_apoli.provider.custom.number.IntProvider;
import io.github.eggohito.neo_apoli.registry.provider.NeoApoliIntProviderTypes;
import io.github.eggohito.neo_apoli.util.MapCodecUtil;
import io.github.eggohito.neo_apoli.util.StreamCodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

public record WeightedIntProvider(WeightedList<IntProvider> entries) implements IntProvider, WeightedValueProvider<Integer, IntProvider> {

	public static final MapCodec<WeightedIntProvider> CODEC = MapCodecUtil.lazy(WeightedIntProvider.class.getSimpleName(), () -> WeightedValueProvider.mapCodec(IntProvider.CODEC, WeightedIntProvider::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedIntProvider> STREAM_CODEC = StreamCodecUtil.lazy(WeightedIntProvider.class.getSimpleName(), () -> WeightedValueProvider.streamCodec(IntProvider.STREAM_CODEC, WeightedIntProvider::new));

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return NeoApoliIntProviderTypes.WEIGHTED;
	}

}
