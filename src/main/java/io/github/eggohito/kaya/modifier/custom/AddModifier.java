package io.github.eggohito.kaya.modifier.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.modifier.Modifier;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.KayaModifierTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.stream.DoubleStream;

public record AddModifier(List<Modifier> modifiers, FloatProvider amount, Phase phase) implements Modifier {

	public static final MapCodec<AddModifier> CODEC = Modifier.mapCodec(AddModifier::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, AddModifier> STREAM_CODEC = Modifier.streamCodec(AddModifier::new);

	@Override
	public Type<?> getType() {
		return KayaModifierTypes.ADD;
	}

	@Override
	public double apply(DoubleStream amounts, double base, double total) {
		return amounts.reduce(total, Double::sum);
	}

}
