package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import io.github.eggohito.kaya.util.CachedBlock;
import net.minecraft.resources.ResourceLocation;

public record BlockContextParameter(ResourceLocation name) implements ContextParameter<CachedBlock> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.BLOCK;
	}

}
