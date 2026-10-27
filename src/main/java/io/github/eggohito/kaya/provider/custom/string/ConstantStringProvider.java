package io.github.eggohito.kaya.provider.custom.string;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.registry.provider.KayaStringProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record ConstantStringProvider(String value) implements StringProvider {

	public static final MapCodec<ConstantStringProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.STRING.fieldOf("value").forGetter(ConstantStringProvider::value)
	).apply(instance, ConstantStringProvider::new));

	public static final Codec<ConstantStringProvider> INLINE_CODEC = Codec.STRING.xmap(
		ConstantStringProvider::new,
		ConstantStringProvider::value
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ConstantStringProvider> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.STRING_UTF8, ConstantStringProvider::value,
		ConstantStringProvider::new
	);

	@Override
	public @NotNull StringProvider.Type<?> getType() {
		return KayaStringProviderTypes.CONSTANT;
	}

	@Override
	public Optional<String> getValue(Context context) {
		return Optional.of(value());
	}

}
