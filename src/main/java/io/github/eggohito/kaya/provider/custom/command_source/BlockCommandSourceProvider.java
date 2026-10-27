package io.github.eggohito.kaya.provider.custom.command_source;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.config.KayaCommonConfig;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.block.BlockProvider;
import io.github.eggohito.kaya.registry.provider.KayaCommandSourceProviderTypes;
import io.github.eggohito.kaya.util.CachedBlock;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public record BlockCommandSourceProvider(BlockProvider block) implements CommandSourceProvider {

	public static final MapCodec<BlockCommandSourceProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(BlockProvider.CODEC.fieldOf("block").forGetter(BlockCommandSourceProvider::block))
		.apply(instance, BlockCommandSourceProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, BlockCommandSourceProvider> STREAM_CODEC = StreamCodec.composite(
		BlockProvider.STREAM_CODEC, BlockCommandSourceProvider::block,
		BlockCommandSourceProvider::new
	);

	@Override
	public CommandSourceProvider.@NotNull Type<?> getType() {
		return KayaCommandSourceProviderTypes.BLOCK;
	}

	@Override
	public Optional<CommandSourceStack> getValue(Context context) {

		if (context.level() instanceof ServerLevel serverLevel) {
			return block().getValue(context.forChild(".block"))
				.map(block -> this.getCommandSource(serverLevel, block))
				.map(KayaCommonConfig.INSTANCE.command.get()::sanitizeSource);
		}

		else {
			return Optional.empty();
		}

	}

	@Override
	public void validate(ContextValidator validator) {
		CommandSourceProvider.super.validate(validator);
		block().validate(validator.forChild(".block"));
	}

	private CommandSourceStack getCommandSource(ServerLevel level, CachedBlock block) {
		Component blockName = Component.translatable(block.state().getBlock().getDescriptionId());
		return new CommandSourceStack(
			CommandSource.NULL,
			block.pos().getCenter(),
			Vec2.ZERO,
			level,
			0,
			blockName.getString(),
			blockName,
			level.getServer(),
			null
		);
	}

}
