package io.github.eggohito.kaya.provider.custom.string;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaStringProviderTypes;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalStringProvider(List<CompositeConditional.Entry<StringProvider>> entries, StringProvider defaultValue) implements StringProvider, CompositeConditionalValueProvider<String, StringProvider> {

	public static final MapCodec<CompositeConditionalStringProvider> CODEC = CompositeConditionalValueProvider.mapCodec(StringProvider.CODEC, CompositeConditionalStringProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalStringProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(StringProvider.STREAM_CODEC, CompositeConditionalStringProvider::new);

	@Override
	public @NotNull StringProvider.Type<?> getType() {
		return KayaStringProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
