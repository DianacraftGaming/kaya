package io.github.eggohito.kaya.power.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.power.Power;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.registry.KayaPowerTypes;
import io.github.eggohito.kaya.registry.context.KayaContextParameters;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record ModifyEffectImmunityPower(Optional<Condition> activeCondition) implements Power {

	public static final MapCodec<ModifyEffectImmunityPower> CODEC = RecordCodecBuilder.mapCodec(instance -> Power
		.addActiveConditionField(instance)
		.apply(instance, ModifyEffectImmunityPower::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ModifyEffectImmunityPower> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.optional(Condition.STREAM_CODEC), Power::activeCondition,
		ModifyEffectImmunityPower::new
	);

	@Override
	public Type<?> getType() {
		return KayaPowerTypes.MODIFY_EFFECT_IMMUNITY;
	}

	@Override
	public Power.Instance<?> createInstance() {
		return new Instance(this);
	}

	public static class Instance extends Power.Instance<ModifyEffectImmunityPower> {

		protected Instance(@NotNull ModifyEffectImmunityPower power) {
			super(power);
		}

		public Context createContext(Entity holder, MobEffectInstance effectInstance, @Nullable Entity source) {
			return this.createHolderContextBuilder(holder)
				.withNullable(KayaContextParameters.ACTOR_ENTITY, source)
				.withRequired(KayaContextParameters.TARGET_ENTITY, holder)
				.withRequired(KayaContextParameters.APPLIED_EFFECT, effectInstance)
				.build(holder.level());
		}

	}

	public static boolean modify(@NotNull Entity holder, MobEffectInstance effectInstance, @Nullable Entity source) {

		for (var instance : Powers.getInstances(holder, Instance.class)) {

			Context context = instance.createContext(holder, effectInstance, source);

			if (instance.isActive(context)) {
				return true;
			}

		}

		return false;

	}

}
