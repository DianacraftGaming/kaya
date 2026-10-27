package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;

public record StringContextParameter(ResourceLocation name) implements ContextParameter<String> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.STRING;
	}

}
