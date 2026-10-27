package io.github.eggohito.kaya.provider.custom.number.ints;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.item.ItemProvider;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.registry.provider.KayaIntProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record ItemCountMaxIntProvider(ItemProvider item) implements IntProvider {

	public static final MapCodec<ItemCountMaxIntProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(ItemProvider.CODEC.fieldOf("item").forGetter(ItemCountMaxIntProvider::item))
		.apply(instance, ItemCountMaxIntProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ItemCountMaxIntProvider> STREAM_CODEC = StreamCodec.composite(
		ItemProvider.STREAM_CODEC, ItemCountMaxIntProvider::item,
		ItemCountMaxIntProvider::new
	);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.ITEM_COUNT_MAX;
	}

	@Override
	public Optional<Integer> getValue(Context context) {
		return item()
			.getValue(context.forChild(".item"))
			.map(ItemStack::getMaxStackSize);
	}

	@Override
	public void validate(ContextValidator validator) {
		IntProvider.super.validate(validator);
		item().validate(validator.forChild(".item"));
	}

}
