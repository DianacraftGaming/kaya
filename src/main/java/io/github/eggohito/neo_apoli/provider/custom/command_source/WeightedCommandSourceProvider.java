package io.github.eggohito.neo_apoli.provider.custom.command_source;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.neo_apoli.provider.WeightedValueProvider;
import io.github.eggohito.neo_apoli.registry.provider.NeoApoliCommandSourceProviderTypes;
import io.github.eggohito.neo_apoli.util.MapCodecUtil;
import io.github.eggohito.neo_apoli.util.StreamCodecUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.NotNull;

public record WeightedCommandSourceProvider(WeightedList<CommandSourceProvider> entries) implements CommandSourceProvider, WeightedValueProvider<CommandSourceStack, CommandSourceProvider> {

	public static final MapCodec<WeightedCommandSourceProvider> CODEC = MapCodecUtil.lazy(WeightedCommandSourceProvider.class.getSimpleName(), () -> WeightedValueProvider.mapCodec(CommandSourceProvider.CODEC, WeightedCommandSourceProvider::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedCommandSourceProvider> STREAM_CODEC = StreamCodecUtil.lazy(WeightedCommandSourceProvider.class.getSimpleName(), () -> WeightedValueProvider.streamCodec(CommandSourceProvider.STREAM_CODEC, WeightedCommandSourceProvider::new));

	@Override
	public CommandSourceProvider.@NotNull Type<?> getType() {
		return NeoApoliCommandSourceProviderTypes.WEIGHTED;
	}
}
