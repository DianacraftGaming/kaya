package io.github.eggohito.kaya.provider.custom.effect;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.WeightedValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaEffectProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

public record WeightedEffectProvider(WeightedList<EffectProvider> entries) implements EffectProvider, WeightedValueProvider<MobEffectInstance, EffectProvider> {

	public static final MapCodec<WeightedEffectProvider> CODEC = WeightedValueProvider.mapCodec(EffectProvider.CODEC, WeightedEffectProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedEffectProvider> STREAM_CODEC = WeightedValueProvider.streamCodec(EffectProvider.STREAM_CODEC, WeightedEffectProvider::new);

	@Override
	public EffectProvider.@NotNull Type<?> getType() {
		return KayaEffectProviderTypes.WEIGHTED;
	}

}
