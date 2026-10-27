package io.github.eggohito.kaya.registry.provider;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.provider.custom.direction.*;
import io.github.eggohito.kaya.registry.KayaRegistries;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public final class KayaDirectionProviderTypes {

	public static final DirectionProvider.Type<CompositeConditionalDirectionProvider> COMPOSITE_CONDITIONAL = registerInternal("conditional/composite", CompositeConditionalDirectionProvider.CODEC, CompositeConditionalDirectionProvider.STREAM_CODEC);
	public static final DirectionProvider.Type<ConditionalDirectionProvider> CONDITIONAL = registerInternal("conditional", ConditionalDirectionProvider.CODEC, ConditionalDirectionProvider.STREAM_CODEC);
	public static final DirectionProvider.Type<ConstantDirectionProvider> CONSTANT = registerInternal("constant", ConstantDirectionProvider.CODEC, ConstantDirectionProvider.STREAM_CODEC);
	public static final DirectionProvider.Type<ContextDirectionProvider> CONTEXT = registerInternal("context", ContextDirectionProvider.CODEC, ContextDirectionProvider.STREAM_CODEC);
	public static final DirectionProvider.Type<OppositeDirectionProvider> OPPOSITE = registerInternal("opposite", OppositeDirectionProvider.CODEC, OppositeDirectionProvider.STREAM_CODEC);
	public static final DirectionProvider.Type<RotateDirectionProvider> ROTATE = registerInternal("rotate", RotateDirectionProvider.CODEC, RotateDirectionProvider.STREAM_CODEC);
	public static final DirectionProvider.Type<WeightedDirectionProvider> WEIGHTED = registerInternal("weighted", WeightedDirectionProvider.CODEC, WeightedDirectionProvider.STREAM_CODEC);

	public static void registerAll() {

	}

	public static <P extends DirectionProvider> DirectionProvider.Type<P> register(ResourceLocation id, MapCodec<P> mapCodec, StreamCodec<RegistryFriendlyByteBuf, P> streamCodec) {
		return Registry.register(KayaRegistries.DIRECTION_PROVIDER_TYPE, id, new DirectionProvider.Type<>(mapCodec, streamCodec));
	}

	private static <P extends DirectionProvider> DirectionProvider.Type<P> registerInternal(String path, MapCodec<P> mapCodec, StreamCodec<RegistryFriendlyByteBuf, P> streamCodec) {
		return register(Kaya.id(path), mapCodec, streamCodec);
	}

}
