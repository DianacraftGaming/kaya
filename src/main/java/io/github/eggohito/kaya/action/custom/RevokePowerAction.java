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
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public record RevokePowerAction(ParsedArgument<PowerArgument.Result> power, ResourceLocation source, EntityProvider entity) implements Action {

	public static final MapCodec<RevokePowerAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		KayaCodecs.POWER_OR_TAG_ARGUMENT.fieldOf("power").forGetter(RevokePowerAction::power),
		ResourceLocation.CODEC.fieldOf("source").forGetter(RevokePowerAction::source),
		EntityProvider.CODEC.fieldOf("entity").forGetter(RevokePowerAction::entity)
	).apply(instance, RevokePowerAction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, RevokePowerAction> STREAM_CODEC = StreamCodec.composite(
		KayaStreamCodecs.POWER_OR_TAG_ARGUMENT, RevokePowerAction::power,
		ResourceLocation.STREAM_CODEC, RevokePowerAction::source,
		EntityProvider.STREAM_CODEC, RevokePowerAction::entity,
		RevokePowerAction::new
	);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.REVOKE_POWER;
	}

	@Override
	public void execute(Context context) {
		entity().getValue(context.forChild(".entity"))
			.flatMap(MutablePowers::getOptional)
			.ifPresent(mutable -> this.revoke(mutable, context::reportProblem));
	}

	@Override
	public void validate(ContextValidator validator) {
		Action.super.validate(validator);
		power().argument().validate(validator.forChild(".power"));
		entity().validate(validator.forChild(".entity"));
	}

	private void revoke(MutablePowers mutable, Consumer<String> errorHandler) {

		try (mutable) {

			for (var holder : power.argument().get()) {
				mutable.revoke(holder.id(), source());
			}

		}

		catch (CommandSyntaxException e) {
			errorHandler.accept(e.getMessage());
		}

	}

}
