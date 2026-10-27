package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.codec.KayaStreamCodecs;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.SlotRange;
import net.minecraft.world.inventory.SlotRanges;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record SlotIdIntProvider(SlotRange slot) implements IntProvider {

	public static final MapCodec<SlotIdIntProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(SlotRanges.CODEC.fieldOf("slot").forGetter(SlotIdIntProvider::slot))
		.apply(instance, SlotIdIntProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, SlotIdIntProvider> STREAM_CODEC = StreamCodec.composite(
		KayaStreamCodecs.SLOT_RANGE, SlotIdIntProvider::slot,
		SlotIdIntProvider::new
	);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.SLOT_ID;
	}

	@Override
	public Optional<Integer> getValue(Context context) {
		return Optional.of(slot().slots().getFirst());
	}

	@Override
	public void validate(ContextValidator validator) {

		IntProvider.super.validate(validator);

		if (slot().size() > 1) {
			validator.forChild(".slot").reportProblem("Slot with multiple IDs is not allowed!");
		}

	}

}
