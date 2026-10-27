package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;

public record DamageSourceContextParameter(ResourceLocation name) implements ContextParameter<DamageSource> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.DAMAGE_SOURCE;
	}

}
