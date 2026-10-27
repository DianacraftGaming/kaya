package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.context.parameter.BlockContextParameter;
import io.github.eggohito.kaya.exception.PosOutOfBoundsException;
import io.github.eggohito.kaya.exception.PosUnloadedException;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import io.github.eggohito.kaya.util.CachedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public record IsEntitySteppingOnBlockCondition(Condition steppedOnCondition, EntityProvider entity) implements Condition {

	public static final BlockContextParameter STEPPED_ON_BLOCK = new BlockContextParameter(Kaya.id("stepped_on_block"));
	public static final ContextValidator.Parameters CONDITION_PARAMETER_SET = new ContextValidator.Parameters.Builder().required(STEPPED_ON_BLOCK).build();

	public static final MapCodec<IsEntitySteppingOnBlockCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Condition.CODEC.optionalFieldOf("stepped_on_condition", new ConstantCondition(true)).forGetter(IsEntitySteppingOnBlockCondition::steppedOnCondition),
		EntityProvider.CODEC.fieldOf("entity").forGetter(IsEntitySteppingOnBlockCondition::entity)
	).apply(instance, IsEntitySteppingOnBlockCondition::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, IsEntitySteppingOnBlockCondition> STREAM_CODEC = StreamCodec.composite(
		Condition.STREAM_CODEC, IsEntitySteppingOnBlockCondition::steppedOnCondition,
		EntityProvider.STREAM_CODEC, IsEntitySteppingOnBlockCondition::entity,
		IsEntitySteppingOnBlockCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_ENTITY_STEPPING_ON_BLOCK;
	}

	@Override
	public boolean test(Context context) {

		Level level = context.level();
		Entity entity = entity().getValue(context.forChild(".entity")).orElse(null);

		try {

			if (!context.visitor().push(this)) {
				return false;
			}

			else if (entity == null || !entity.onGround()) {
				return false;
			}

			else {

				try {

					BlockPos steppingPos = entity.getOnPos();
					Context steppedOnContext = new Context.Builder(context)
						.withRequired(STEPPED_ON_BLOCK, CachedBlock.fromLoadedPos(level, steppingPos))
						.build(level);

					return steppedOnCondition().test(steppedOnContext.forChild(".stepped_on_condition"));

				}

				catch (PosUnloadedException | PosOutOfBoundsException ignored) {
					return false;
				}

			}

		}

		finally {
			context.visitor().pop(this);
		}

	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		steppedOnCondition().validate(validator.withParams(CONDITION_PARAMETER_SET).forChild(".stepped_on_condition"));
		entity().validate(validator.forChild(".entity"));
	}

}
