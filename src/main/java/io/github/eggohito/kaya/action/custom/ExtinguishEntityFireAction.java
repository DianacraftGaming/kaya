package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;

public record ExtinguishEntityFireAction(EntityProvider entity) implements Action {

	public static final MapCodec<ExtinguishEntityFireAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(ExtinguishEntityFireAction::entity))
		.apply(instance, ExtinguishEntityFireAction::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ExtinguishEntityFireAction> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, ExtinguishEntityFireAction::entity,
		ExtinguishEntityFireAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.EXTINGUISH_ENTITY_FIRE;
	}

	@Override
	public void execute(Context context) {
		entity().getValue(context.forChild(".entity")).ifPresent(Entity::extinguishFire);
	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
	}

}
