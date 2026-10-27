package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.block.BlockProvider;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import io.github.eggohito.kaya.util.CachedBlock;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record EntityHasCorrectToolForBlockCondition(EntityProvider entity, BlockProvider block) implements Condition {

	public static final MapCodec<EntityHasCorrectToolForBlockCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		EntityProvider.CODEC.fieldOf("entity").forGetter(EntityHasCorrectToolForBlockCondition::entity),
		BlockProvider.CODEC.fieldOf("block").forGetter(EntityHasCorrectToolForBlockCondition::block)
	).apply(instance, EntityHasCorrectToolForBlockCondition::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, EntityHasCorrectToolForBlockCondition> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, EntityHasCorrectToolForBlockCondition::entity,
		BlockProvider.STREAM_CODEC, EntityHasCorrectToolForBlockCondition::block,
		EntityHasCorrectToolForBlockCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.ENTITY_HAS_CORRECT_TOOL_FOR_BLOCK;
	}

	@Override
	public boolean test(Context context) {

		Entity entity = entity().getValue(context.forChild(".entity")).orElse(null);
		CachedBlock block = block().getValue(context.forChild(".block")).orElse(null);

		return block != null
			&& entity instanceof Player player
			&& player.hasCorrectToolForDrops(block.state());

	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
		block().validate(validator.forChild(".block"));
	}

}
