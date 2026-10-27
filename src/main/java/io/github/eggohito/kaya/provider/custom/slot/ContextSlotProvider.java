package io.github.eggohito.kaya.provider.custom.slot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.context.parameter.SlotContextParameter;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.registry.provider.KayaSlotProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.SlotAccess;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public record ContextSlotProvider(SlotContextParameter parameter) implements SlotProvider {

	public static final MapCodec<ContextSlotProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaContextParameterTypes.SLOT.codec().fieldOf("parameter").forGetter(ContextSlotProvider::parameter))
		.apply(instance, ContextSlotProvider::new)
	);

	public static final Codec<ContextSlotProvider> INLINE_CODEC = KayaContextParameterTypes.SLOT.codec().xmap(
		ContextSlotProvider::new,
		ContextSlotProvider::parameter
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ContextSlotProvider> STREAM_CODEC = StreamCodec.composite(
		KayaContextParameterTypes.SLOT.streamCodec(), ContextSlotProvider::parameter,
		ContextSlotProvider::new
	);

	@Override
	public SlotProvider.@NotNull Type<?> getType() {
		return KayaSlotProviderTypes.CONTEXT;
	}

	@Override
	public Optional<SlotAccess> getValue(Context context) {
		return context.getOptional(parameter());
	}

	@Override
	public Set<ContextParameter<?>> getRequiredParameters() {
		return Set.of(parameter());
	}

}
