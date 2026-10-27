package io.github.eggohito.kaya.modifier.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.modifier.Modifier;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.KayaModifierTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.stream.DoubleStream;

public record DivideModifier(List<Modifier> modifiers, FloatProvider amount, Phase phase) implements Modifier {

	public static final MapCodec<DivideModifier> CODEC = Modifier.mapCodec(DivideModifier::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, DivideModifier> STREAM_CODEC = Modifier.streamCodec(DivideModifier::new);

	@Override
	public Type<?> getType() {
		return KayaModifierTypes.DIVIDE;
	}

	@Override
	public double apply(DoubleStream amounts, double base, double total) {
		return amounts.reduce(total, (left, right) -> left / right);
	}

}
