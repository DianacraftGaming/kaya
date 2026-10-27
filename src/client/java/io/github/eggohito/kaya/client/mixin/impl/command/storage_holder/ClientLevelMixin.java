package io.github.eggohito.kaya.client.mixin.impl.command.storage_holder;

import io.github.eggohito.kaya.client.duck.internal.ClientCommandStorageHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements ClientCommandStorageHolder {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Override
	public CompoundTag kaya$getStorage(ResourceLocation id) {
		return this.kaya$getStorageHolder().kaya$getStorage(id);
	}

	@Override
	public boolean kaya$contains(ResourceLocation id) {
		return this.kaya$getStorageHolder().kaya$contains(id);
	}

	@Override
	public void kaya$setStorage(ResourceLocation id, CompoundTag nbt) {
		this.kaya$getStorageHolder().kaya$setStorage(id, nbt);
	}

	@Override
	public void kaya$clear() {
		this.kaya$getStorageHolder().kaya$clear();
	}

	@Unique
	private ClientCommandStorageHolder kaya$getStorageHolder() {
		return (ClientCommandStorageHolder) this.minecraft;
	}

}
