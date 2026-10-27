package io.github.eggohito.kaya.provider.custom.damage_source;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.provider.ConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaDamageSourceProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.NotNull;

public record ConditionalDamageSourceProvider(Condition condition, DamageSourceProvider onTrue, DamageSourceProvider onFalse) implements DamageSourceProvider, ConditionalValueProvider<DamageSource, DamageSourceProvider> {

	public static final MapCodec<ConditionalDamageSourceProvider> CODEC = ConditionalValueProvider.mapCodec(DamageSourceProvider.CODEC, ConditionalDamageSourceProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ConditionalDamageSourceProvider> STREAM_CODEC = ConditionalValueProvider.streamCodec(DamageSourceProvider.STREAM_CODEC, ConditionalDamageSourceProvider::new);

	@Override
	public DamageSourceProvider.@NotNull Type<?> getType() {
		return KayaDamageSourceProviderTypes.CONDITIONAL;
	}

}
