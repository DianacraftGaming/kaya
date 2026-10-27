package io.github.eggohito.kaya.provider.custom.command_source;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.WeightedValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaCommandSourceProviderTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

public record WeightedCommandSourceProvider(WeightedList<CommandSourceProvider> entries) implements CommandSourceProvider, WeightedValueProvider<CommandSourceStack, CommandSourceProvider> {

	public static final MapCodec<WeightedCommandSourceProvider> CODEC = WeightedValueProvider.mapCodec(CommandSourceProvider.CODEC, WeightedCommandSourceProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedCommandSourceProvider> STREAM_CODEC = WeightedValueProvider.streamCodec(CommandSourceProvider.STREAM_CODEC, WeightedCommandSourceProvider::new);

	@Override
	public CommandSourceProvider.@NotNull Type<?> getType() {
		return KayaCommandSourceProviderTypes.WEIGHTED;
	}
}
