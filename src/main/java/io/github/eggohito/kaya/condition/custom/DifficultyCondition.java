package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.codec.KayaCodecs;
import io.github.eggohito.kaya.codec.KayaStreamCodecs;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Difficulty;

public record DifficultyCondition(Difficulty difficulty) implements Condition {

	public static final MapCodec<DifficultyCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(KayaCodecs.DIFFICULTY.fieldOf("difficulty").forGetter(DifficultyCondition::difficulty))
		.apply(instance, DifficultyCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, DifficultyCondition> STREAM_CODEC = StreamCodec.composite(
		KayaStreamCodecs.DIFFICULTY, DifficultyCondition::difficulty,
		DifficultyCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.DIFFICULTY;
	}

	@Override
	public boolean test(Context context) {
		return context.level().getDifficulty() == difficulty();
	}

}
