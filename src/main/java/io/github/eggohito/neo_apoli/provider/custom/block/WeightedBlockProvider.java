package io.github.eggohito.neo_apoli.provider.custom.block;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.neo_apoli.provider.WeightedValueProvider;
import io.github.eggohito.neo_apoli.registry.provider.NeoApoliBlockProviderTypes;
import io.github.eggohito.neo_apoli.util.CachedBlock;
import io.github.eggohito.neo_apoli.util.MapCodecUtil;
import io.github.eggohito.neo_apoli.util.StreamCodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

public record WeightedBlockProvider(WeightedList<BlockProvider> entries) implements BlockProvider, WeightedValueProvider<CachedBlock, BlockProvider> {

	public static final MapCodec<WeightedBlockProvider> CODEC = MapCodecUtil.lazy(WeightedBlockProvider.class.getSimpleName(), () -> WeightedValueProvider.mapCodec(BlockProvider.CODEC, WeightedBlockProvider::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedBlockProvider> STREAM_CODEC = StreamCodecUtil.lazy(WeightedBlockProvider.class.getSimpleName(), () -> WeightedValueProvider.streamCodec(BlockProvider.STREAM_CODEC, WeightedBlockProvider::new));

	@Override
	public BlockProvider.@NotNull Type<?> getType() {
		return NeoApoliBlockProviderTypes.WEIGHTED;
	}

}
