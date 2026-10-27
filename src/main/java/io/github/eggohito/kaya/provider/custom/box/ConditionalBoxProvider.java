package io.github.eggohito.kaya.provider.custom.box;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.provider.ConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaBoxProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public record ConditionalBoxProvider(Condition condition, BoxProvider onTrue, BoxProvider onFalse) implements BoxProvider, ConditionalValueProvider<AABB, BoxProvider> {

	public static final MapCodec<ConditionalBoxProvider> CODEC = ConditionalValueProvider.mapCodec(BoxProvider.CODEC, ConditionalBoxProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ConditionalBoxProvider> STREAM_CODEC = ConditionalValueProvider.streamCodec(BoxProvider.STREAM_CODEC, ConditionalBoxProvider::new);

	@Override
	public @NotNull BoxProvider.Type<?> getType() {
		return KayaBoxProviderTypes.CONDITIONAL;
	}

}
