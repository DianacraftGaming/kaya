package io.github.eggohito.kaya.provider.custom.number.floats;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.power.PowerIdentifier;
import io.github.eggohito.kaya.power.custom.FloatResourcePower;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.registry.provider.KayaFloatProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record ResourceFloatProvider(PowerIdentifier power, EntityProvider entity) implements FloatProvider {

	public static final MapCodec<ResourceFloatProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		PowerIdentifier.CODEC.fieldOf("power").forGetter(ResourceFloatProvider::power),
		EntityProvider.CODEC.fieldOf("entity").forGetter(ResourceFloatProvider::entity)
	).apply(instance, ResourceFloatProvider::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ResourceFloatProvider> STREAM_CODEC = StreamCodec.composite(
		PowerIdentifier.STREAM_CODEC, ResourceFloatProvider::power,
		EntityProvider.STREAM_CODEC, ResourceFloatProvider::entity,
		ResourceFloatProvider::new
	);

	@Override
	public @NotNull Type<?> getType() {
		return KayaFloatProviderTypes.RESOURCE;
	}

	@Override
	public Optional<Float> getValue(Context context) {
		return entity().getValue(context.forChild(".entity"))
			.flatMap(Powers::getOptional)
			.flatMap(powers -> powers.getOptionalInstance(power()))
			.filter(FloatResourcePower.Instance.class::isInstance)
			.map(FloatResourcePower.Instance.class::cast)
			.map(FloatResourcePower.Instance::getValue);
	}

	@Override
	public void validate(ContextValidator validator) {
		FloatProvider.super.validate(validator);
		power().validate(validator.forChild(".power"), FloatResourcePower.class, () -> power().asDisplayString() + " is not a resource of a float type!");
		entity().validate(validator.forChild(".entity"));
	}

}
