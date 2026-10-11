package io.github.eggohito.neo_apoli.provider.custom.bool;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.neo_apoli.codec.NeoApoliStreamCodecs;
import io.github.eggohito.neo_apoli.context.Context;
import io.github.eggohito.neo_apoli.context.ContextValidator;
import io.github.eggohito.neo_apoli.provider.custom.nbt.NbtProvider;
import io.github.eggohito.neo_apoli.registry.provider.NeoApoliBooleanProviderTypes;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record NbtBooleanProvider(NbtProvider source, NbtPathArgument.NbtPath path) implements BooleanProvider {

	public static final MapCodec<NbtBooleanProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		NbtProvider.CODEC.fieldOf("source").forGetter(NbtBooleanProvider::source),
		NbtPathArgument.NbtPath.CODEC.fieldOf("path").forGetter(NbtBooleanProvider::path)
	).apply(instance, NbtBooleanProvider::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, NbtBooleanProvider> STREAM_CODEC = StreamCodec.composite(
		NbtProvider.STREAM_CODEC, NbtBooleanProvider::source,
		NeoApoliStreamCodecs.NBT_PATH, NbtBooleanProvider::path,
		NbtBooleanProvider::new
	);

	@Override
	public @NotNull BooleanProvider.Type<?> getType() {
		return NeoApoliBooleanProviderTypes.NBT;
	}

	@Override
	public Optional<Boolean> getValue(Context context) {

		Tag source = source()
			.getValue(context.forChild(".source"))
			.orElse(null);

		if (source == null) {
			return Optional.empty();
		}

		try {

			List<Tag> tags = path().get(source);

			if (tags.size() == 1) {
				return tags.getFirst().asBoolean();
			}

		}

		catch (CommandSyntaxException e) {
			context.reportProblem("Error trying to get a boolean value in NBT path \"" + this.path() + "\" from NBT \"" + this.source + "\": " + e.getMessage());
		}

		return Optional.empty();

	}

	@Override
	public void validate(ContextValidator validator) {
		BooleanProvider.super.validate(validator);
		source().validate(validator.forChild(".source"));
	}

}
