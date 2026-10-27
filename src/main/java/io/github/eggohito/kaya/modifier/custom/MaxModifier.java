package io.github.eggohito.kaya.modifier.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.modifier.Modifier;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.KayaModifierTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.stream.DoubleStream;

public record MaxModifier(List<Modifier> modifiers, FloatProvider amount, Phase phase) implements Modifier {

	public static final MapCodec<MaxModifier> CODEC = Modifier.mapCodec(MaxModifier::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, MaxModifier> STREAM_CODEC = Modifier.streamCodec(MaxModifier::new);

	@Override
	public Type<?> getType() {
		return KayaModifierTypes.MAX;
	}

	@Override
	public double apply(DoubleStream amounts, double base, double total) {
		return amounts.reduce(total, Math::max);
	}

}
