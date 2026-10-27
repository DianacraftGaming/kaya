package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public record DirectionContextParameter(ResourceLocation name) implements ContextParameter<Direction> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.DIRECTION;
	}

}
