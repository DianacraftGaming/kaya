package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.comparison.Comparator;
import io.github.eggohito.kaya.comparison.custom.IntComparison;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.context.parameter.ItemContextParameter;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.provider.custom.item.ContextItemProvider;
import io.github.eggohito.kaya.provider.custom.number.ints.ConstantIntProvider;
import io.github.eggohito.kaya.provider.custom.number.ints.ItemCountIntProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;

public record EntityHasItemEquippedCondition(Condition equippedCondition, EquipmentSlotGroup slot, EntityProvider entity) implements Condition {

	public static final ItemContextParameter EQUIPPED_ITEM = new ItemContextParameter(Kaya.id("equipped_item"));
	public static final ContextValidator.Parameters CONDITION_PARAMETER_SET = new ContextValidator.Parameters.Builder().required(EQUIPPED_ITEM).build();

	public static final MapCodec<EntityHasItemEquippedCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Condition.CODEC.optionalFieldOf("equipped_condition", new CompareCondition(new IntComparison(Comparator.GREATER_THAN, new ItemCountIntProvider(new ContextItemProvider(EQUIPPED_ITEM)), new ConstantIntProvider(0)))).forGetter(EntityHasItemEquippedCondition::equippedCondition),
		EquipmentSlotGroup.CODEC.fieldOf("slot").forGetter(EntityHasItemEquippedCondition::slot),
		EntityProvider.CODEC.fieldOf("entity").forGetter(EntityHasItemEquippedCondition::entity)
	).apply(instance, EntityHasItemEquippedCondition::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, EntityHasItemEquippedCondition> STREAM_CODEC = StreamCodec.composite(
		Condition.STREAM_CODEC, EntityHasItemEquippedCondition::equippedCondition,
		EquipmentSlotGroup.STREAM_CODEC, EntityHasItemEquippedCondition::slot,
		EntityProvider.STREAM_CODEC, EntityHasItemEquippedCondition::entity,
		EntityHasItemEquippedCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.ENTITY_HAS_ITEM_EQUIPPED;
	}

	@Override
	public boolean test(Context context) {

		Context entityContext = context.forChild(".entity");
		Entity entity = entity().getValue(entityContext).orElse(null);

		if (entityContext.hasProblems() || !(entity instanceof LivingEntity livingEntity)) {
			return false;
		}

		for (var equipmentSlot : EquipmentSlot.values()) {

			if (!slot().test(equipmentSlot)) {
				continue;
			}

			Context equippedContext = new Context.Builder(context)
				.withRequired(EQUIPPED_ITEM, livingEntity.getItemBySlot(equipmentSlot))
				.build(context.level());

			if (equippedCondition().test(equippedContext.forChild(".equipped_condition"))) {
				return true;
			}

		}

		return false;

	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		equippedCondition().validate(validator.withParams(CONDITION_PARAMETER_SET).forChild(".equipped_condition"));
		entity().validate(validator.forChild(".entity"));
	}

}
