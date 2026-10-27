package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record LoopAction(Action beforeAction, Action afterAction, IntProvider iterations, Action action) implements Action {

	public static final MapCodec<LoopAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Action.CODEC.optionalFieldOf("before_action", NothingAction.INSTANCE).forGetter(LoopAction::beforeAction),
		Action.CODEC.optionalFieldOf("after_action", NothingAction.INSTANCE).forGetter(LoopAction::afterAction),
		IntProvider.CODEC.fieldOf("iterations").forGetter(LoopAction::iterations),
		Action.CODEC.fieldOf("action").forGetter(LoopAction::action)
	).apply(instance, LoopAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, LoopAction> STREAM_CODEC = StreamCodec.composite(
		Action.STREAM_CODEC, LoopAction::beforeAction,
		Action.STREAM_CODEC, LoopAction::afterAction,
		IntProvider.STREAM_CODEC, LoopAction::iterations,
		Action.STREAM_CODEC, LoopAction::action,
		LoopAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.LOOP;
	}

	@Override
	public void execute(Context context) {

		beforeAction().execute(context.forChild(".before_action"));
		int iterations = iterations().getInt(context.forChild(".iterations"));

		for (int i = 0; i < iterations; i++) {
			action().execute(context.forChild(".action"));
		}

		afterAction().execute(context.forChild(".after_action"));

	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		beforeAction().validate(validator.forChild(".before_action"));
		afterAction().validate(validator.forChild(".after_action"));
		iterations().validate(validator.forChild(".iterations"));
		action().validate(validator.forChild(".action"));
	}

}
