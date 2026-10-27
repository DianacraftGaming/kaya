package io.github.eggohito.kaya.action.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.codec.KayaCodecs;
import io.github.eggohito.kaya.codec.KayaStreamCodecs;
import io.github.eggohito.kaya.command.argument.PowerArgument;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.power.entity.MutablePowers;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import io.github.eggohito.kaya.util.ParsedArgument;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Consumer;

public record RemovePowerAction(ParsedArgument<PowerArgument.Result> power, EntityProvider entity) implements Action {

	public static final MapCodec<RemovePowerAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		KayaCodecs.POWER_OR_TAG_ARGUMENT.fieldOf("power").forGetter(RemovePowerAction::power),
		EntityProvider.CODEC.fieldOf("entity").forGetter(RemovePowerAction::entity)
	).apply(instance, RemovePowerAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, RemovePowerAction> STREAM_CODEC = StreamCodec.composite(
		KayaStreamCodecs.POWER_OR_TAG_ARGUMENT, RemovePowerAction::power,
		EntityProvider.STREAM_CODEC, RemovePowerAction::entity,
		RemovePowerAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.REMOVE_POWER;
	}

	@Override
	public void execute(Context context) {
		entity().getValue(context.forChild(".entity"))
			.flatMap(MutablePowers::getOptional)
			.ifPresent(mutable -> this.remove(mutable, context::reportProblem));
	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		power().argument().validate(validator.forChild(".power"));
		entity().validate(validator.forChild(".entity"));
	}

	private void remove(MutablePowers mutable, Consumer<String> errorHandler) {

		try (mutable) {

			for (var holder : power().argument().get()) {

				for (var source : mutable.getSources(holder.id())) {
					mutable.revoke(holder.id(), source);
				}

			}

		}

		catch (CommandSyntaxException e) {
			errorHandler.accept(e.getMessage());
		}

	}

}
