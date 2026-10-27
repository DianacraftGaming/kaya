package io.github.eggohito.kaya.provider.custom.bool;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaBooleanProviderTypes;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalBooleanProvider(List<CompositeConditional.Entry<BooleanProvider>> entries, BooleanProvider defaultValue) implements BooleanProvider, CompositeConditionalValueProvider<Boolean, BooleanProvider> {

	public static final MapCodec<CompositeConditionalBooleanProvider> CODEC = CompositeConditionalValueProvider.mapCodec(BooleanProvider.CODEC, CompositeConditionalBooleanProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalBooleanProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(BooleanProvider.STREAM_CODEC, CompositeConditionalBooleanProvider::new);

	@Override
	public @NotNull BooleanProvider.Type<?> getType() {
		return KayaBooleanProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
