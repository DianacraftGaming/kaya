package io.github.eggohito.kaya.registry;

import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.area.Area;
import io.github.eggohito.kaya.color.Color;
import io.github.eggohito.kaya.comparison.Comparison;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.container_menu.ContainerMenu;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.hud.element.HudElement;
import io.github.eggohito.kaya.modifier.Modifier;
import io.github.eggohito.kaya.power.Power;
import io.github.eggohito.kaya.provider.ValueProvider;
import io.github.eggohito.kaya.provider.custom.block.BlockProvider;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.provider.custom.box.BoxProvider;
import io.github.eggohito.kaya.provider.custom.command_source.CommandSourceProvider;
import io.github.eggohito.kaya.provider.custom.damage_source.DamageSourceProvider;
import io.github.eggohito.kaya.provider.custom.direction.DirectionProvider;
import io.github.eggohito.kaya.provider.custom.effect.EffectProvider;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.provider.custom.item.ItemProvider;
import io.github.eggohito.kaya.provider.custom.nbt.NbtProvider;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.provider.custom.slot.SlotProvider;
import io.github.eggohito.kaya.provider.custom.string.StringProvider;
import io.github.eggohito.kaya.provider.custom.vec3.Vec3Provider;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class KayaRegistries {

	public static final Registry<Power.Type<?>> POWER_TYPE = create(KayaRegistryKeys.POWER_TYPE);
	public static final Registry<Action.Type<?>> ACTION_TYPE = create(KayaRegistryKeys.ACTION_TYPE);
	public static final Registry<Condition.Type<?>> CONDITION_TYPE = create(KayaRegistryKeys.CONDITION_TYPE);

	public static final Registry<BlockProvider.Type<?>> BLOCK_PROVIDER_TYPE = create(KayaRegistryKeys.BLOCK_PROVIDER_TYPE);
	public static final Registry<BooleanProvider.Type<?>> BOOLEAN_PROVIDER_TYPE = create(KayaRegistryKeys.BOOLEAN_PROVIDER_TYPE);
	public static final Registry<BoxProvider.Type<?>> BOX_PROVIDER_TYPE = create(KayaRegistryKeys.BOX_PROVIDER_TYPE);
	public static final Registry<CommandSourceProvider.Type<?>> COMMAND_SOURCE_PROVIDER_TYPE = create(KayaRegistryKeys.COMMAND_SOURCE_PROVIDER_TYPE);
	public static final Registry<DamageSourceProvider.Type<?>> DAMAGE_SOURCE_PROVIDER_TYPE = create(KayaRegistryKeys.DAMAGE_SOURCE_PROVIDER_TYPE);
	public static final Registry<DirectionProvider.Type<?>> DIRECTION_PROVIDER_TYPE = create(KayaRegistryKeys.DIRECTION_PROVIDER_TYPE);
	public static final Registry<EffectProvider.Type<?>> EFFECT_PROVIDER_TYPE = create(KayaRegistryKeys.EFFECT_PROVIDER_TYPE);
	public static final Registry<EntityProvider.Type<?>> ENTITY_PROVIDER_TYPE = create(KayaRegistryKeys.ENTITY_PROVIDER_TYPE);
	public static final Registry<FloatProvider.Type<?>> FLOAT_PROVIDER_TYPE = create(KayaRegistryKeys.FLOAT_PROVIDER_TYPE);
	public static final Registry<IntProvider.Type<?>> INT_PROVIDER_TYPE = create(KayaRegistryKeys.INT_PROVIDER_TYPE);
	public static final Registry<ItemProvider.Type<?>> ITEM_PROVIDER_TYPE = create(KayaRegistryKeys.ITEM_PROVIDER_TYPE);
	public static final Registry<NbtProvider.Type<?>> NBT_PROVIDER_TYPE = create(KayaRegistryKeys.NBT_PROVIDER_TYPE);
	public static final Registry<SlotProvider.Type<?>> SLOT_PROVIDER_TYPE = create(KayaRegistryKeys.SLOT_PROVIDER_TYPE);
	public static final Registry<StringProvider.Type<?>> STRING_PROVIDER_TYPE = create(KayaRegistryKeys.STRING_PROVIDER_TYPE);
	public static final Registry<Vec3Provider.Type<?>> VEC3_PROVIDER_TYPE = create(KayaRegistryKeys.VEC3_PROVIDER_TYPE);

	public static final Registry<Area.Type<?>> AREA_TYPE = create(KayaRegistryKeys.AREA_TYPE);
	public static final Registry<Color.Type<?>> COLOR_TYPE = create(KayaRegistryKeys.COLOR_TYPE);
	public static final Registry<Comparison.Type<?>> COMPARISON_TYPE = create(KayaRegistryKeys.COMPARISON_TYPE);
	public static final Registry<ContainerMenu.Type<?>> CONTAINER_MENU_TYPE = create(KayaRegistryKeys.CONTAINER_MENU_TYPE);
	public static final Registry<HudElement.Type<?>> HUD_ELEMENT_TYPE = create(KayaRegistryKeys.HUD_ELEMENT_TYPE);
	public static final Registry<Modifier.Type<?>> MODIFIER_TYPE = create(KayaRegistryKeys.MODIFIER_TYPE);

	public static final Registry<ContextParameter.Type<?>> CONTEXT_PARAMETER_TYPE = create(KayaRegistryKeys.CONTEXT_PARAMETER_TYPE);
	public static final Registry<ValueProvider.Kind<?>> VALUE_PROVIDER_KIND = create(KayaRegistryKeys.VALUE_PROVIDER_KIND);

	private static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
		return FabricRegistryBuilder.createSimple(key)
			.attribute(RegistryAttribute.SYNCED)
			.buildAndRegister();
	}

}
