package io.github.eggohito.kaya.color.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.color.Color;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.provider.custom.vec3.Vec3Provider;
import io.github.eggohito.kaya.registry.KayaColorResolvers;
import io.github.eggohito.kaya.registry.KayaColorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.ColorResolver;

public record BiomeFoliageColor(Vec3Provider position, FloatProvider alpha, BooleanProvider dry) implements Color {

	public static final MapCodec<BiomeFoliageColor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Vec3Provider.CODEC.fieldOf("position").forGetter(BiomeFoliageColor::position),
		FloatProvider.clamped(0.0F, 1.0F).fieldOf("alpha").forGetter(BiomeFoliageColor::alpha),
		BooleanProvider.CODEC.fieldOf("dry").forGetter(BiomeFoliageColor::dry)
	).apply(instance, BiomeFoliageColor::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, BiomeFoliageColor> STREAM_CODEC = StreamCodec.composite(
		Vec3Provider.STREAM_CODEC, BiomeFoliageColor::position,
		FloatProvider.STREAM_CODEC, BiomeFoliageColor::alpha,
		BooleanProvider.STREAM_CODEC, BiomeFoliageColor::dry,
		BiomeFoliageColor::new
	);

	@Override
	public Type<?> getType() {
		return KayaColorTypes.BIOME_FOLIAGE;
	}

	@Override
	public int intValue(Context context) {

		BlockPos position = position().getValue(context.forChild(".position"))
			.map(BlockPos::containing)
			.orElse(null);

		if (position == null) {
			return 0;
		}

		float alphaFloat = alpha().getValue(context.forChild(".alpha")).orElse(1.0F);
		boolean dry = dry().getBoolean(context.forChild(".dry"));

		ColorResolver colorResolver = dry
			? KayaColorResolvers.BIOME_DRY_FOLIAGE_COLOR
			: KayaColorResolvers.BIOME_FOLIAGE_COLOR;

		return ARGB.color(ARGB.as8BitChannel(alphaFloat), context.level().getBlockTint(position, colorResolver));

	}

	@Override
	public void validate(ContextValidator validator) {
		Color.super.validate(validator);
		position().validate(validator.forChild(".position"));
		alpha().validate(validator.forChild(".alpha"));
		dry().validate(validator.forChild(".dry"));
	}

}
