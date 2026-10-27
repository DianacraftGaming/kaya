package io.github.eggohito.kaya.provider.custom.nbt;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaNbtProviderTypes;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalNbtProvider(List<CompositeConditional.Entry<NbtProvider>> entries, NbtProvider defaultValue) implements NbtProvider, CompositeConditionalValueProvider<Tag, NbtProvider> {

	public static final MapCodec<CompositeConditionalNbtProvider> CODEC = CompositeConditionalValueProvider.mapCodec(NbtProvider.CODEC, CompositeConditionalNbtProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalNbtProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(NbtProvider.STREAM_CODEC, CompositeConditionalNbtProvider::new);

	@Override
	public @NotNull NbtProvider.Type<?> getType() {
		return KayaNbtProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
