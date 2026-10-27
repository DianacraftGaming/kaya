package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public record BoxContextParameter(ResourceLocation name) implements ContextParameter<AABB> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.BOX;
	}

}
