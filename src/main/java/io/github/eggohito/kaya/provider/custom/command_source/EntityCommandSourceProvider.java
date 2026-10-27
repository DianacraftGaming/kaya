package io.github.eggohito.kaya.provider.custom.command_source;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.config.KayaCommonConfig;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.registry.provider.KayaCommandSourceProviderTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings({"UnstableApiUsage"})
public record EntityCommandSourceProvider(EntityProvider entity) implements CommandSourceProvider {

	public static final MapCodec<EntityCommandSourceProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(EntityProvider.CODEC.fieldOf("entity").forGetter(EntityCommandSourceProvider::entity))
		.apply(instance, EntityCommandSourceProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, EntityCommandSourceProvider> STREAM_CODEC = StreamCodec.composite(
		EntityProvider.STREAM_CODEC, EntityCommandSourceProvider::entity,
		EntityCommandSourceProvider::new
	);

	@Override
	public CommandSourceProvider.@NotNull Type<?> getType() {
		return KayaCommandSourceProviderTypes.ENTITY;
	}

	@Override
	public Optional<CommandSourceStack> getValue(Context context) {
		return entity().getValue(context.forChild(".entity"))
			.flatMap(this::getCommandSource)
			.map(KayaCommonConfig.INSTANCE.command.get()::sanitizeSource);
	}

	@Override
	public void validate(ContextValidator validator) {
		CommandSourceProvider.super.validate(validator);
		entity().validate(validator.forChild(".entity"));
	}

	private Optional<CommandSourceStack> getCommandSource(Entity entity) {

		if (entity instanceof ServerPlayer serverPlayer) {
			return Optional.of(serverPlayer.createCommandSourceStack());
		}

		else if (entity.level() instanceof ServerLevel serverLevel) {
			return Optional.of(entity.createCommandSourceStackForNameResolution(serverLevel));
		}

		else {
			return Optional.empty();
		}

	}

}
