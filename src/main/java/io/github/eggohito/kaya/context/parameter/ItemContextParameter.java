package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record ItemContextParameter(ResourceLocation name) implements ContextParameter<ItemStack> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.ITEM;
	}

}
