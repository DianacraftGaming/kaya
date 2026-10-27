package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import io.github.eggohito.kaya.util.MiscUtil;
import io.github.eggohito.kaya.util.StreamCodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.WeightedList;

public record WeightedAction(WeightedList<Action> entries) implements Action {

	public static final MapCodec<WeightedAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(WeightedList.nonEmptyCodec(Action.CODEC.fieldOf("action")).fieldOf("entries").forGetter(WeightedAction::entries))
		.apply(instance, WeightedAction::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, WeightedAction> STREAM_CODEC = StreamCodec.composite(
		StreamCodecUtil.weightedList(Action.STREAM_CODEC), WeightedAction::entries,
		WeightedAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.WEIGHTED;
	}

	@Override
	public void execute(Context context) {
		entries().kaya$getRandomAndIndex(context.level().getRandom()).ifPresent(pair -> pair.first().execute(context.forChild(".entries[" + pair.secondInt() + "]")));
	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		MiscUtil.iterateList(entries().unwrap(), (index, weighted) -> weighted.value().validate(validator.forChild(".entries[" + index + "]")));
	}

}
