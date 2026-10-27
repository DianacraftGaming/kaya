package io.github.eggohito.kaya.provider.custom.command_source;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaCommandSourceProviderTypes;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalCommandSourceProvider(List<CompositeConditional.Entry<CommandSourceProvider>> entries, CommandSourceProvider defaultValue) implements CommandSourceProvider, CompositeConditionalValueProvider<CommandSourceStack, CommandSourceProvider> {

	public static final MapCodec<CompositeConditionalCommandSourceProvider> CODEC = CompositeConditionalValueProvider.mapCodec(CommandSourceProvider.CODEC, CompositeConditionalCommandSourceProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalCommandSourceProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(CommandSourceProvider.STREAM_CODEC, CompositeConditionalCommandSourceProvider::new);

	@Override
	public CommandSourceProvider.@NotNull Type<?> getType() {
		return KayaCommandSourceProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
