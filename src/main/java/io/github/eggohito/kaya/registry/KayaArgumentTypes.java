package io.github.eggohito.kaya.registry;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.command.argument.ActionArgument;
import io.github.eggohito.kaya.command.argument.ConditionArgument;
import io.github.eggohito.kaya.command.argument.ContextParametersArgument;
import io.github.eggohito.kaya.command.argument.PowerArgument;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

public class KayaArgumentTypes {

	public static void registerAll() {

		ArgumentTypeRegistry.registerArgumentType(Kaya.id("power"), PowerArgument.class, PowerArgument.Info.INSTANCE);

		ArgumentTypeRegistry.registerArgumentType(Kaya.id("action"), ActionArgument.class, ActionArgument.Info.INSTANCE);
		ArgumentTypeRegistry.registerArgumentType(Kaya.id("condition"), ConditionArgument.class, ConditionArgument.Info.INSTANCE);
		ArgumentTypeRegistry.registerArgumentType(Kaya.id("context_parameters"), ContextParametersArgument.class, SingletonArgumentInfo.contextAware(ContextParametersArgument::new));

	}

}
