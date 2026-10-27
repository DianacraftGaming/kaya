package io.github.eggohito.kaya.client.mixin.impl.command.storage_holder;

import io.github.eggohito.kaya.client.duck.internal.ClientCommandStorageHolder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements ClientCommandStorageHolder {

	@Unique
	private final Object2ObjectOpenHashMap<ResourceLocation, CompoundTag> kaya$commandStorageCache = new Object2ObjectOpenHashMap<>();

	@Override
	public CompoundTag kaya$getStorage(ResourceLocation id) {
		return kaya$commandStorageCache.getOrDefault(id, new CompoundTag());
	}

	@Override
	public boolean kaya$contains(ResourceLocation id) {
		return kaya$commandStorageCache.containsKey(id);
	}

	@Override
	public void kaya$setStorage(ResourceLocation id, CompoundTag nbt) {

		if (nbt.isEmpty()) {
			kaya$commandStorageCache.remove(id);
		}

		else {
			kaya$commandStorageCache.put(id, nbt);
		}

	}

	@Override
	public void kaya$clear() {
		this.kaya$commandStorageCache.clear();
		this.kaya$commandStorageCache.trim();
	}

}
