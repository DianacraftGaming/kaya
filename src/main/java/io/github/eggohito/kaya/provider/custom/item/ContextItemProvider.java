package io.github.eggohito.kaya.provider.custom.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.ItemContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaItemProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextItemProvider(ItemContextParameter parameter) implements ItemProvider {

	public static final MapCodec<ContextItemProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.ITEM.codec().fieldOf("parameter").forGetter(ContextItemProvider::parameter))
		.apply(instance, ContextItemProvider::new)
	);

	public static final Codec<ContextItemProvider> INLINE_CODEC = KayaContextParameterTypes.ITEM.codec().xmap(
		ContextItemProvider::new,
		ContextItemProvider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextItemProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.ITEM.streamCodec(), ContextItemProvider::parameter,
		ContextItemProvider::new
	);

	@Override
	public ItemProvider.@NotNull Type<?> getType() {
		return KayaItemProviderTypes.CONTEXT;
	}

	@Override
	public Optional<ItemStack> getValue(Context context) {

		if (!context.hasParameter(parameter())) {
			context.reportProblem("Parameter \"" + parameter().name() + "\" is not provided in the context!");
		}

		return context.getOptional(parameter());

	}

	@Override
	public Set<ContextParameter<?>> getRequiredParameters() {
		return Set.of(parameter());
	}

}
