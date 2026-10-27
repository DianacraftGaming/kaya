package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record SetEntityOnFireAction(IntProvider ticks, EntityProvider entity) implements Action {

	public static final MapCodec<SetEntityOnFireAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		IntProvider.CODEC.fieldOf("ticks").forGetter(SetEntityOnFireAction::ticks),
		EntityProvider.CODEC.fieldOf("entity").forGetter(SetEntityOnFireAction::entity)
	).apply(instance, SetEntityOnFireAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SetEntityOnFireAction> STREAM_CODEC = StreamCodec.composite(
		IntProvider.STREAM_CODEC, SetEntityOnFireAction::ticks,
		EntityProvider.STREAM_CODEC, SetEntityOnFireAction::entity,
		SetEntityOnFireAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.SET_ENTITY_ON_FIRE;
	}

	@Override
	public void execute(Context context) {
		entity()
			.getValue(context.forChild(".entity"))
			.ifPresent(entity -> entity.igniteForTicks(ticks().getInt(context.forChild(".ticks"))));
	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		ticks().validate(validator.forChild(".ticks"));
		entity().validate(validator.forChild(".entity"));
	}

}
