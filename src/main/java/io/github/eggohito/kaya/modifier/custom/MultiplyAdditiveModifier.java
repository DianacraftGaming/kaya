package io.github.eggohito.kaya.modifier.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.modifier.Modifier;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.KayaModifierTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.stream.DoubleStream;

public record MultiplyAdditiveModifier(List<Modifier> modifiers, FloatProvider amount, Phase phase) implements Modifier {

	public static final MapCodec<MultiplyAdditiveModifier> CODEC = Modifier.mapCodec(MultiplyAdditiveModifier::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, MultiplyAdditiveModifier> STREAM_CODEC = Modifier.streamCodec(MultiplyAdditiveModifier::new);

	@Override
	public Type<?> getType() {
		return KayaModifierTypes.MULTIPLY_ADDITIVE;
	}

	@Override
	public double apply(DoubleStream amounts, double base, double total) {
		return total + (base * amounts.reduce(0.0, Double::sum));
	}

}
