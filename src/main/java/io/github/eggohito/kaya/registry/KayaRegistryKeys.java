package io.github.eggohito.kaya.registry;

import io.github.eggohito.kaya.Kaya;
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
import io.github.eggohito.kaya.power.global.GlobalPowerSet;
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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class KayaRegistryKeys {

	public static final ResourceKey<Registry<Power>> POWER = create("power");
	public static final ResourceKey<Registry<GlobalPowerSet>> GLOBAL_POWER_SET = create("global_power_set");

	public static final ResourceKey<Registry<Action>> ACTION = create("action");
	public static final ResourceKey<Registry<Condition>> CONDITION = create("condition");

	public static final ResourceKey<Registry<Power.Type<?>>> POWER_TYPE = create("power_type");
	public static final ResourceKey<Registry<Action.Type<?>>> ACTION_TYPE = create("action_type");
	public static final ResourceKey<Registry<Condition.Type<?>>> CONDITION_TYPE = create("condition_type");

	public static final ResourceKey<Registry<BlockProvider.Type<?>>> BLOCK_PROVIDER_TYPE = create("provider_type/block");
	public static final ResourceKey<Registry<BooleanProvider.Type<?>>> BOOLEAN_PROVIDER_TYPE = create("provider_type/boolean");
	public static final ResourceKey<Registry<BoxProvider.Type<?>>> BOX_PROVIDER_TYPE = create("provider_type/box");
	public static final ResourceKey<Registry<CommandSourceProvider.Type<?>>> COMMAND_SOURCE_PROVIDER_TYPE = create("provider_type/command_source");
	public static final ResourceKey<Registry<DamageSourceProvider.Type<?>>> DAMAGE_SOURCE_PROVIDER_TYPE = create("provider_type/damage_source");
	public static final ResourceKey<Registry<DirectionProvider.Type<?>>> DIRECTION_PROVIDER_TYPE = create("provider_type/direction");
	public static final ResourceKey<Registry<EffectProvider.Type<?>>> EFFECT_PROVIDER_TYPE = create("provider_type/effect");
	public static final ResourceKey<Registry<EntityProvider.Type<?>>> ENTITY_PROVIDER_TYPE = create("provider_type/entity");
	public static final ResourceKey<Registry<FloatProvider.Type<?>>> FLOAT_PROVIDER_TYPE = create("provider_type/float");
	public static final ResourceKey<Registry<IntProvider.Type<?>>> INT_PROVIDER_TYPE = create("provider_type/int");
	public static final ResourceKey<Registry<ItemProvider.Type<?>>> ITEM_PROVIDER_TYPE = create("provider_type/item");
	public static final ResourceKey<Registry<NbtProvider.Type<?>>> NBT_PROVIDER_TYPE = create("provider_type/nbt");
	public static final ResourceKey<Registry<SlotProvider.Type<?>>> SLOT_PROVIDER_TYPE = create("provider_type/slot");
	public static final ResourceKey<Registry<StringProvider.Type<?>>> STRING_PROVIDER_TYPE = create("provider_type/string");
	public static final ResourceKey<Registry<Vec3Provider.Type<?>>> VEC3_PROVIDER_TYPE = create("provider_type/vec3");

	public static final ResourceKey<Registry<Area.Type<?>>> AREA_TYPE = create("area_type");
	public static final ResourceKey<Registry<Color.Type<?>>> COLOR_TYPE = create("color_type");
	public static final ResourceKey<Registry<Comparison.Type<?>>> COMPARISON_TYPE = create("comparison_type");
	public static final ResourceKey<Registry<ContainerMenu.Type<?>>> CONTAINER_MENU_TYPE = create("container_menu_type");
	public static final ResourceKey<Registry<HudElement.Type<?>>> HUD_ELEMENT_TYPE = create("hud_element_type");
	public static final ResourceKey<Registry<Modifier.Type<?>>> MODIFIER_TYPE = create("modifier_type");

	public static final ResourceKey<Registry<ContextParameter.Type<?>>> CONTEXT_PARAMETER_TYPE = create("parameter_type");
	public static final ResourceKey<Registry<ValueProvider.Kind<?>>> VALUE_PROVIDER_KIND = create("provider_kind");

	private static <T> ResourceKey<Registry<T>> create(String path) {
		return ResourceKey.createRegistryKey(Kaya.id(path));
	}

}
