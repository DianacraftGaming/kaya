package io.github.eggohito.kaya.provider.custom.item;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.provider.ConditionalValueProvider;
import io.github.eggohito.kaya.registry.provider.KayaItemProviderTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ConditionalItemProvider(Condition condition, ItemProvider onTrue, ItemProvider onFalse) implements ItemProvider, ConditionalValueProvider<ItemStack, ItemProvider> {

	public static final MapCodec<ConditionalItemProvider> CODEC = ConditionalValueProvider.mapCodec(ItemProvider.CODEC, ConditionalItemProvider::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, ConditionalItemProvider> STREAM_CODEC = ConditionalValueProvider.streamCodec(ItemProvider.STREAM_CODEC, ConditionalItemProvider::new);

	@Override
	public ItemProvider.@NotNull Type<?> getType() {
		return KayaItemProviderTypes.CONDITIONAL;
	}

}
