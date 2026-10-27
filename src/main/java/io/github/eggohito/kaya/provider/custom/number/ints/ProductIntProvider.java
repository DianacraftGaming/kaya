package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.provider.custom.number.MultiIntProvider;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record ProductIntProvider(List<IntProvider> values) implements MultiIntProvider {

	public static final MapCodec<ProductIntProvider> CODEC = MultiIntProvider.mapCodec(ProductIntProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ProductIntProvider> STREAM_CODEC = MultiIntProvider.streamCodec(ProductIntProvider::new);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.PRODUCT;
	}

	@Override
	public Optional<Integer> getValue(Context context) {
		return this.iterateAndProcess(context, (first, second) -> first * second);
	}

}
