package io.github.eggohito.kaya.provider.custom.effect;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.provider.ConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaEffectProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

public record ConditionalEffectProvider(Condition condition, EffectProvider onTrue, EffectProvider onFalse) implements EffectProvider, ConditionalValueProvider<MobEffectInstance, EffectProvider> {

	public static final MapCodec<ConditionalEffectProvider> CODEC = ConditionalValueProvider.mapCodec(EffectProvider.CODEC, ConditionalEffectProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ConditionalEffectProvider> STREAM_CODEC = ConditionalValueProvider.streamCodec(EffectProvider.STREAM_CODEC, ConditionalEffectProvider::new);

	@Override
	public EffectProvider.@NotNull Type<?> getType() {
		return KayaEffectProviderTypes.CONDITIONAL;
	}

}
