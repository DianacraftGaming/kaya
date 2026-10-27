package io.github.eggohito.kaya.provider.custom.block;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaBlockProviderTypes;
import io.github.eggohito.kaya.util.CachedBlock;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalBlockProvider(List<CompositeConditional.Entry<BlockProvider>> entries, BlockProvider defaultValue) implements BlockProvider, CompositeConditionalValueProvider<CachedBlock, BlockProvider> {

	public static final MapCodec<CompositeConditionalBlockProvider> CODEC = CompositeConditionalValueProvider.mapCodec(BlockProvider.CODEC, CompositeConditionalBlockProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalBlockProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(BlockProvider.STREAM_CODEC, CompositeConditionalBlockProvider::new);

	@Override
	public BlockProvider.@NotNull Type<?> getType() {
		return KayaBlockProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
