package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record Vec3ContextParameter(ResourceLocation name) implements ContextParameter<Vec3> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.VEC3;
	}

}
