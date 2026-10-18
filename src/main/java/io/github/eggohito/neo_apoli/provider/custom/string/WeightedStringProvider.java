package io.github.eggohito.neo_apoli.provider.custom.string;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.neo_apoli.provider.WeightedValueProvider;
import io.github.eggohito.neo_apoli.registry.provider.NeoApoliStringProviderTypes;
import io.github.eggohito.neo_apoli.util.MapCodecUtil;
import io.github.eggohito.neo_apoli.util.StreamCodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

public record WeightedStringProvider(WeightedList<StringProvider> entries) implements StringProvider, WeightedValueProvider<String, StringProvider> {

	public static final MapCodec<WeightedStringProvider> CODEC = MapCodecUtil.lazy(WeightedStringProvider.class.getSimpleName(), () -> WeightedValueProvider.mapCodec(StringProvider.CODEC, WeightedStringProvider::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedStringProvider> STREAM_CODEC = StreamCodecUtil.lazy(WeightedStringProvider.class.getSimpleName(), () -> WeightedValueProvider.streamCodec(StringProvider.STREAM_CODEC, WeightedStringProvider::new));

	@Override
	public @NotNull StringProvider.Type<?> getType() {
		return NeoApoliStringProviderTypes.WEIGHTED;
	}

}
