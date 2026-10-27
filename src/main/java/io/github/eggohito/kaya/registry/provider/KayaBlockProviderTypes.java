package io.github.eggohito.kaya.registry.provider;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.provider.custom.block.*;
import io.github.eggohito.kaya.registry.KayaRegistries;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public final class KayaBlockProviderTypes {

	public static final BlockProvider.Type<CompositeConditionalBlockProvider> COMPOSITE_CONDITIONAL = registerInternal("conditional/composite", CompositeConditionalBlockProvider.CODEC, CompositeConditionalBlockProvider.STREAM_CODEC);
	public static final BlockProvider.Type<ConditionalBlockProvider> CONDITIONAL = registerInternal("conditional", ConditionalBlockProvider.CODEC, ConditionalBlockProvider.STREAM_CODEC);
	public static final BlockProvider.Type<ContextBlockProvider> CONTEXT = registerInternal("context", ContextBlockProvider.CODEC, ContextBlockProvider.STREAM_CODEC);
	public static final BlockProvider.Type<WeightedBlockProvider> WEIGHTED = registerInternal("weighted", WeightedBlockProvider.CODEC, WeightedBlockProvider.STREAM_CODEC);

	public static final BlockProvider.Type<WorldBlockProvider> WORLD = registerInternal("world", WorldBlockProvider.CODEC, WorldBlockProvider.STREAM_CODEC);

	public static void registerAll() {

	}

	public static <P extends BlockProvider> BlockProvider.Type<P> register(ResourceLocation id, MapCodec<P> mapCodec, StreamCodec<RegistryFriendlyByteBuf, P> streamCodec) {
		return Registry.register(KayaRegistries.BLOCK_PROVIDER_TYPE, id, new BlockProvider.Type<>(mapCodec, streamCodec));
	}

	private static <P extends BlockProvider> BlockProvider.Type<P> registerInternal(String path, MapCodec<P> mapCodec, StreamCodec<RegistryFriendlyByteBuf, P> streamCodec) {
		return register(Kaya.id(path), mapCodec, streamCodec);
	}

}
