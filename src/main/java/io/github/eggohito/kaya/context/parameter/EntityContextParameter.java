package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public record EntityContextParameter(ResourceLocation name) implements ContextParameter<Entity> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.ENTITY;
	}

}
