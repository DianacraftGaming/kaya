package io.github.eggohito.kaya.power.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.context.visitor.ClearableVisitor;
import io.github.eggohito.kaya.event.PowerModifyEvents;
import io.github.eggohito.kaya.modifier.Modifier;
import io.github.eggohito.kaya.power.Power;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.registry.KayaPowerTypes;
import io.github.eggohito.kaya.util.MiscUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record ModifyAirSpeedPower(Optional<Condition> activeCondition, List<Modifier> modifiers) implements Power {

	public static final ClearableVisitor<Instance> VISITOR = ClearableVisitor.createThreadLocalized();

	public static final MapCodec<ModifyAirSpeedPower> CODEC = RecordCodecBuilder.mapCodec(instance -> Power
		.addActiveConditionField(instance)
		.and(ExtraCodecs.nonEmptyList(Modifier.CODEC.listOf()).fieldOf("modifiers").forGetter(ModifyAirSpeedPower::modifiers))
		.apply(instance, ModifyAirSpeedPower::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ModifyAirSpeedPower> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.optional(Condition.STREAM_CODEC), Power::activeCondition,
		ByteBufCodecs.collection(ObjectArrayList::new, Modifier.STREAM_CODEC), ModifyAirSpeedPower::modifiers,
		ModifyAirSpeedPower::new
	);

	@Override
	public Type<?> getType() {
		return KayaPowerTypes.MODIFY_AIR_SPEED;
	}

	@Override
	public Power.Instance<?> createInstance() {
		return new Instance(this);
	}

	@Override
	public void validate(ContextValidator validator) {
		Power.super.validate(validator);
		MiscUtil.iterateList(modifiers(), (index, modifier) -> modifier.validate(validator.forChild(".modifiers[" + index + "]")));
	}

	public static class Instance extends Power.Instance<ModifyAirSpeedPower> {

		protected Instance(@NotNull ModifyAirSpeedPower power) {
			super(power);
		}

		public List<Modifier> getModifiers() {
			return power.modifiers();
		}

	}

	public static float modify(Entity entity, float airSpeed) {

		List<Modifier.Operation> entries = new ObjectArrayList<>();

		for (var instance : Powers.getInstances(entity, Instance.class)) {

			Context context = instance.createHolderContext(entity);

			try {

				if (VISITOR.push(instance) && instance.isActive(context)) {
					MiscUtil.iterateList(instance.getModifiers(), (index, modifier) -> entries.add(Modifier.operation(modifier, context.forChild(".modifiers[" + index + "]"))));
				}

			}

			finally {
				VISITOR.pop(instance);
			}

		}

		PowerModifyEvents.NUMBER.invoker().beforeModified(KayaPowerTypes.MODIFY_AIR_SPEED, entries, airSpeed);
		return (float) Modifier.applyAll(entries, airSpeed);

	}

}
