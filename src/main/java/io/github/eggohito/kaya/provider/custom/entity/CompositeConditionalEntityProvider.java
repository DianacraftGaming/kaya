package io.github.eggohito.kaya.provider.custom.entity;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.provider.CompositeConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaEntityProviderTypes;
import io.github.eggohito.kaya.util.conditional.CompositeConditional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompositeConditionalEntityProvider(List<CompositeConditional.Entry<EntityProvider>> entries, EntityProvider defaultValue) implements EntityProvider, CompositeConditionalValueProvider<Entity, EntityProvider> {

	public static final MapCodec<CompositeConditionalEntityProvider> CODEC = CompositeConditionalValueProvider.mapCodec(EntityProvider.CODEC, CompositeConditionalEntityProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, CompositeConditionalEntityProvider> STREAM_CODEC = CompositeConditionalValueProvider.streamCodec(EntityProvider.STREAM_CODEC, CompositeConditionalEntityProvider::new);

	@Override
	public EntityProvider.@NotNull Type<?> getType() {
		return KayaEntityProviderTypes.COMPOSITE_CONDITIONAL;
	}

}
