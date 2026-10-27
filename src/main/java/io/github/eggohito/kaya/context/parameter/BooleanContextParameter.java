package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;

public record BooleanContextParameter(ResourceLocation name) implements ContextParameter<Boolean> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.BOOLEAN;
	}

}
