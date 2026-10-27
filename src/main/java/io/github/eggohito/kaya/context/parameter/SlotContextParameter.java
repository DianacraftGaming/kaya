package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SlotAccess;

public record SlotContextParameter(ResourceLocation name) implements ContextParameter<SlotAccess> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.SLOT;
	}

}
