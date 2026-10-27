package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.provider.custom.nbt.NbtProvider;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.provider.custom.number.NbtNumberProvider;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.NumericTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record NbtIntProvider(NbtProvider source, NbtPathArgument.NbtPath path) implements IntProvider, NbtNumberProvider<Integer> {

	public static final MapCodec<NbtIntProvider> CODEC = NbtNumberProvider.mapCodec(NbtIntProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, NbtIntProvider> STREAM_CODEC = NbtNumberProvider.streamCodec(NbtIntProvider::new);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.NBT;
	}

	@Override
	public Optional<Integer> getValue(Context context) {
		return this.convertTag(context, NumericTag::intValue, value -> value);
	}

}
