package io.github.eggohito.kaya.mixin.impl.command.storage_holder;

import io.github.eggohito.kaya.duck.internal.ServerCommandStorageHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements ServerCommandStorageHolder {

	@Shadow
	@NotNull
	public abstract MinecraftServer getServer();

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
	public void kaya$sendAll(ServerPlayer player) {
		this.kaya$getStorageHolder().kaya$sendAll(player);
	}

	@Unique
	private ServerCommandStorageHolder kaya$getStorageHolder() {
		return (ServerCommandStorageHolder) this.getServer();
	}

}
