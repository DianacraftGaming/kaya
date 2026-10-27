package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

public record EffectContextParameter(ResourceLocation name) implements ContextParameter<MobEffectInstance> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.EFFECT;
	}

}
