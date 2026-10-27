package io.github.eggohito.kaya.command;

import com.google.gson.JsonElement;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.github.eggohito.kaya.command.argument.ConditionArgument;
import io.github.eggohito.kaya.command.argument.ContextParametersArgument;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.condition.manager.ConditionManager;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextParameterMap;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.registry.KayaRegistries;
import io.github.eggohito.kaya.registry.context.KayaContextParameters;
import io.github.eggohito.kaya.util.JsonTextFormatter;
import io.github.eggohito.kaya.util.MiscUtil;
import io.github.eggohito.kaya.util.Reporter;
import net.minecraft.Util;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class ConditionCommand {

	public static final ContextValidator.Parameters COMMAND_PARAMETERS = new ContextValidator.Parameters.Builder()
		.optional(KayaContextParameters.COMMAND_ENTITY)
		.required(KayaContextParameters.COMMAND_POSITION)
		.build();

	public static void register(CommandBuildContext buildContext, CommandNode<CommandSourceStack> rootNode) {

		var baseNode = literal("condition")
			.requires(source -> source.hasPermission(2))
			.build();

		baseNode.addChild(Dump.node(buildContext));
		baseNode.addChild(Test.node(buildContext));

		rootNode.addChild(baseNode);

	}

	public static final class Dump {

		public static CommandNode<CommandSourceStack> node(CommandBuildContext buildContext) {

			var node = literal("dump")
				.then(argument("condition", ConditionArgument.condition(buildContext))
					.executes(Dump::withDefaultIndent)
					.then(argument("indent", IntegerArgumentType.integer(0))
						.executes(Dump::withSpecificIndent)));

			return node.build();

		}

		public static int withDefaultIndent(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
			return execute(context, 4);
		}

		public static int withSpecificIndent(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
			return execute(context, IntegerArgumentType.getInteger(context, "indent"));
		}

		public static int execute(CommandContext<CommandSourceStack> context, int indent) throws CommandSyntaxException {

			CommandSourceStack source = context.getSource();
			Condition condition = ConditionArgument.getCondition(context, "condition");

			return switch (Condition.CODEC.encodeStart(source.registryAccess().createSerializationContext(JsonOps.INSTANCE), condition)) {
				case DataResult.Success<JsonElement> success -> {

					JsonElement jsonElement = success.value();
					source.sendSuccess(() -> JsonTextFormatter.format(jsonElement, indent), false);

					yield jsonElement.toString().length();

				}
				case DataResult.Error<JsonElement> error ->
					throw MiscUtil.createCommandException(error::message);
			};

		}

	}

	public static final class Test {

		public static CommandNode<CommandSourceStack> node(CommandBuildContext buildContext) {

			var testNode = literal("test")
				.then(argument("condition", ConditionArgument.inlineCondition(buildContext))
					.then(literal("with")
						.then(argument("parameters", ContextParametersArgument.parameters(buildContext))
							.executes(Test::testAsInt))));

			return testNode.build();

		}

		public static int testAsInt(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {

			if (test(commandContext)) {
				commandContext.getSource().sendSuccess(() -> Component.translatable("commands.execute.conditional.pass"), false);
			}

			else {
				throw MiscUtil.createCommandException(Component.translatable("commands.execute.conditional.fail"));
			}

			return 1;

		}

		public static boolean test(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {

			CommandSourceStack source = commandContext.getSource();
			ContextParameterMap parameters = ContextParametersArgument.getParameters(commandContext, "parameters");

			Condition condition = ConditionArgument.getCondition(commandContext, "condition");
			String path = ConditionManager.getInstance().getKeyAsResult(condition).mapOrElse(id -> "{\"" + id + "\"}", error -> "{type: \"" + Util.getRegisteredName(KayaRegistries.CONDITION_TYPE, condition.getType()) + "\"}");

			Reporter reporter = new Reporter(path);
			ContextValidator validator = new ContextValidator(COMMAND_PARAMETERS.merge(parameters.forValidation()), reporter).withResolver(source.registryAccess());

			condition.validate(validator);

			if (reporter.hasProblems()) {
				throw MiscUtil.createCommandException(Component.literal("Found errors while validating the condition\n" + reporter.getReport()));
			}

			Context baseContext = new Context.Builder()
				.withReporter(reporter)
				.withNullable(KayaContextParameters.COMMAND_ENTITY, source.getEntity())
				.withRequired(KayaContextParameters.COMMAND_POSITION, source.getPosition())
				.build(source.getLevel());

			Context context = parameters.forUser(baseContext, ctx -> ctx.forChild(".parameters"));
			boolean result = condition.test(context);

			if (reporter.hasProblems()) {
				throw MiscUtil.createCommandException(Component.literal("Found errors while testing the condition\n" + reporter.getReport()));
			}

			return result;

		}

	}

}
