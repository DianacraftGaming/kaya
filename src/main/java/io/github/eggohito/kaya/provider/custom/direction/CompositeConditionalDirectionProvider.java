package io.github.eggohito.kaya.provider.custom.direction;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaDirectionProviderTypes;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalDirectionProvider(List<CompositeConditional.Entry<DirectionProvider>> entries, DirectionProvider defaultValue) implements DirectionProvider, CompositeConditionalValueProvider<Direction, DirectionProvider> {

	public static final MapCodec<CompositeConditionalDirectionProvider> CODEC = CompositeConditionalValueProvider.mapCodec(DirectionProvider.CODEC, CompositeConditionalDirectionProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalDirectionProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(DirectionProvider.STREAM_CODEC, CompositeConditionalDirectionProvider::new);

	@Override
	public DirectionProvider.@NotNull Type<?> getType() {
		return KayaDirectionProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
