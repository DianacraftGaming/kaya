package io.github.eggohito.kaya.provider.custom.vec3;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.codec.KayaCodecs;
import io.github.eggohito.kaya.codec.KayaStreamCodecs;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.provider.KayaVec3ProviderTypes;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record EntityPositionVec3Provider(EntityProvider entity, EntityAnchorArgument.Anchor anchor) implements Vec3Provider {

	public static final MapCodec<EntityPositionVec3Provider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		EntityProvider.CODEC.fieldOf("entity").forGetter(EntityPositionVec3Provider::entity),
		KayaCodecs.ENTITY_ANCHOR.optionalFieldOf("anchor", EntityAnchorArgument.Anchor.FEET).forGetter(EntityPositionVec3Provider::anchor)
	).apply(instance, EntityPositionVec3Provider::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, EntityPositionVec3Provider> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, EntityPositionVec3Provider::entity,
		KayaStreamCodecs.ENTITY_ANCHOR, EntityPositionVec3Provider::anchor,
		EntityPositionVec3Provider::new
	);

	@Override
	public @NotNull Vec3Provider.Type<?> getType() {
		return KayaVec3ProviderTypes.ENTITY_POSITION;
	}

	@Override
	public Optional<Vec3> getValue(Context context) {
		return entity()
			.getValue(context.forChild(".entity"))
			.map(anchor()::apply);
	}

	@Override
	public void validate(ContextValidator validator) {
		Vec3Provider.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
	}

}
