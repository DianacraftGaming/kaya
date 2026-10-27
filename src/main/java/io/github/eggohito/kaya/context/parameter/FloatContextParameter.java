package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;

public record FloatContextParameter(ResourceLocation name) implements ContextParameter<Float> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.FLOAT;
	}

}
