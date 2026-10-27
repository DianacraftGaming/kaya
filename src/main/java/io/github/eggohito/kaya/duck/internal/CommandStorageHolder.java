package io.github.eggohito.kaya.duck.internal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public interface CommandStorageHolder {

	CompoundTag kaya$getStorage(ResourceLocation id);

	boolean kaya$contains(ResourceLocation id);

	void kaya$setStorage(ResourceLocation id, CompoundTag nbt);

}
