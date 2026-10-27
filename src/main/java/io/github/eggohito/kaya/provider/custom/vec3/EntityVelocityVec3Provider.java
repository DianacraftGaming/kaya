package io.github.eggohito.kaya.provider.custom.vec3;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.duck.MovingEntity;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.provider.KayaVec3ProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record EntityVelocityVec3Provider(EntityProvider entity) implements Vec3Provider {

    public static final MapCodec<EntityVelocityVec3Provider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
        .group(EntityProvider.CODEC.fieldOf("entity").forGetter(EntityVelocityVec3Provider::entity))
        .apply(instance, EntityVelocityVec3Provider::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, EntityVelocityVec3Provider> STREAM_CODEC = StreamCodec.composite(
        EntityProvider.STREAM_CODEC, EntityVelocityVec3Provider::entity,
        EntityVelocityVec3Provider::new
    );

    @Override
    public @NotNull Vec3Provider.Type<?> getType() {
        return KayaVec3ProviderTypes.ENTITY_VELOCITY;
    }

    @Override
    public Optional<Vec3> getValue(Context context) {

        Context entityContext = context.forChild(".entity");
        Optional<Vec3> velocity = entity().getValue(entityContext).map(MovingEntity::kaya$getVelocity);

        if (velocity.isEmpty()) {
            entityContext.reportProblem("Entity doesn't exist!");
        }

        return velocity;

    }

    @Override
    public void validate(ContextValidator validator) {
        Vec3Provider.super.validate(validator);
        entity().validate(validator.forChild(".entity"));
    }

}
