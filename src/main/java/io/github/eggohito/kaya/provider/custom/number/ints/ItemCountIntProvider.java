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

public record ItemCountIntProvider(ItemProvider item) implements IntProvider {

	public static final MapCodec<ItemCountIntProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(ItemProvider.CODEC.fieldOf("item").forGetter(ItemCountIntProvider::item))
		.apply(instance, ItemCountIntProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ItemCountIntProvider> STREAM_CODEC = StreamCodec.composite(
		ItemProvider.STREAM_CODEC, ItemCountIntProvider::item,
		ItemCountIntProvider::new
	);

	@Override
	public @NotNull IntProvider.Type<?> getType() {
		return KayaIntProviderTypes.ITEM_COUNT;
	}

	@Override
	public Optional<Integer> getValue(Context context) {
		return item()
			.getValue(context.forChild(".item"))
			.map(ItemStack::getCount);
	}

	@Override
	public void validate(ContextValidator validator) {
		IntProvider.super.validate(validator);
		item().validate(validator.forChild(".item"));
	}

}
