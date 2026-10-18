package io.github.eggohito.neo_apoli.provider.custom.box;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.neo_apoli.provider.WeightedValueProvider;
import io.github.eggohito.neo_apoli.registry.provider.NeoApoliBoxProviderTypes;
import io.github.eggohito.neo_apoli.util.MapCodecUtil;
import io.github.eggohito.neo_apoli.util.StreamCodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public record WeightedBoxProvider(WeightedList<BoxProvider> entries) implements BoxProvider, WeightedValueProvider<AABB, BoxProvider> {

	public static final MapCodec<WeightedBoxProvider> CODEC = MapCodecUtil.lazy(WeightedBoxProvider.class.getSimpleName(), () -> WeightedValueProvider.mapCodec(BoxProvider.CODEC, WeightedBoxProvider::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedBoxProvider> STREAM_CODEC = StreamCodecUtil.lazy(WeightedBoxProvider.class.getSimpleName(), () -> WeightedValueProvider.streamCodec(BoxProvider.STREAM_CODEC, WeightedBoxProvider::new));

	@Override
	public @NotNull BoxProvider.Type<?> getType() {
		return NeoApoliBoxProviderTypes.WEIGHTED;
	}

}
