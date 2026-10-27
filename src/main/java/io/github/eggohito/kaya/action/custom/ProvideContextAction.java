package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextParameterMap;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record ProvideContextAction(Action action, ContextParameterMap parameters) implements Action {

	public static final MapCodec<ProvideContextAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Action.CODEC.fieldOf("action").forGetter(ProvideContextAction::action),
		ContextParameterMap.CODEC.fieldOf("parameters").forGetter(ProvideContextAction::parameters)
	).apply(instance, ProvideContextAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ProvideContextAction> STREAM_CODEC = StreamCodec.composite(
		Action.STREAM_CODEC, ProvideContextAction::action,
		ContextParameterMap.STREAM_CODEC, ProvideContextAction::parameters,
		ProvideContextAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.PROVIDE_CONTEXT;
	}

	@Override
	public void execute(Context context) {
		action().execute(parameters()
			.forUser(context, ctx -> ctx.forChild(".parameters"))
			.forChild(".action")
		);
	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		action().validate(validator.withParams(parameters().forValidation()).forChild(".action"));
		parameters().validate(validator.forChild(".parameters"));
	}

}
