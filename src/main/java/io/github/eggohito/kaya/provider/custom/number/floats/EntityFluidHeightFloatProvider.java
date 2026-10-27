package io.github.eggohito.kaya.provider.custom.number.floats;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.provider.KayaFloatProviderTypes;
import io.github.eggohito.kaya.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record EntityFluidHeightFloatProvider(TagKey<Fluid> fluidTag, EntityProvider entity) implements FloatProvider {

	public static final MapCodec<EntityFluidHeightFloatProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		TagKey.hashedCodec(Registries.FLUID).fieldOf("fluid_tag").forGetter(EntityFluidHeightFloatProvider::fluidTag),
		EntityProvider.CODEC.fieldOf("entity").forGetter(EntityFluidHeightFloatProvider::entity)
	).apply(instance, EntityFluidHeightFloatProvider::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, EntityFluidHeightFloatProvider> STREAM_CODEC = StreamCodec.composite(
		TagKey.streamCodec(Registries.FLUID), EntityFluidHeightFloatProvider::fluidTag,
		EntityProvider.STREAM_CODEC, EntityFluidHeightFloatProvider::entity,
		EntityFluidHeightFloatProvider::new
	);

	@Override
	public @NotNull FloatProvider.Type<?> getType() {
		return KayaFloatProviderTypes.ENTITY_FLUID_HEIGHT;
	}

	@Override
	public Optional<Float> getValue(Context context) {
		return entity()
			.getValue(context.forChild(".entity"))
			.map(entity -> (float) entity.getFluidHeight(fluidTag()));
	}

	@Override
	public void validate(ContextValidator validator) {
		FloatProvider.super.validate(validator);
		RegistryUtil.validateTag(validator.forChild(".fluid_tag"), fluidTag());
		entity().validate(validator.forChild(".entity"));
	}

}
