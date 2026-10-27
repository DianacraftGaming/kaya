package io.github.eggohito.kaya.provider.custom.nbt;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.provider.ConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaNbtProviderTypes;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record ConditionalNbtProvider(Condition condition, NbtProvider onTrue, NbtProvider onFalse) implements NbtProvider, ConditionalValueProvider<Tag, NbtProvider> {

	public static final MapCodec<ConditionalNbtProvider> CODEC = ConditionalValueProvider.mapCodec(NbtProvider.CODEC, ConditionalNbtProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ConditionalNbtProvider> STREAM_CODEC = ConditionalValueProvider.streamCodec(NbtProvider.STREAM_CODEC, ConditionalNbtProvider::new);

	@Override
	public @NotNull NbtProvider.Type<?> getType() {
		return KayaNbtProviderTypes.CONDITIONAL;
	}

}
