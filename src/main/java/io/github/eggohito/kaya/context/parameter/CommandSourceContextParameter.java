package io.github.eggohito.kaya.context.parameter;

import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public record CommandSourceContextParameter(ResourceLocation name) implements ContextParameter<CommandSourceStack> {

	@Override
	public Type<?> getType() {
		return KayaContextParameterTypes.COMMAND_SOURCE;
	}

}
