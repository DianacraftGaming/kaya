package io.github.eggohito.kaya.provider.custom.nbt;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.duck.internal.CommandStorageHolder;
import io.github.eggohito.kaya.registry.provider.KayaNbtProviderTypes;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record StorageNbtProvider(ResourceLocation id) implements NbtProvider {

	public static final MapCodec<StorageNbtProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(ResourceLocation.CODEC.fieldOf("id").forGetter(StorageNbtProvider::id))
		.apply(instance, StorageNbtProvider::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, StorageNbtProvider> STREAM_CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, StorageNbtProvider::id,
		StorageNbtProvider::new
	);

	@Override
	public @NotNull NbtProvider.Type<?> getType() {
		return KayaNbtProviderTypes.STORAGE;
	}

	@Override
	public Optional<Tag> getValue(Context context) {
		CommandStorageHolder holder = (CommandStorageHolder) context.level();
		return holder.kaya$contains(this.id())
			? Optional.of(holder.kaya$getStorage(this.id()))
			: Optional.empty();
	}

}
