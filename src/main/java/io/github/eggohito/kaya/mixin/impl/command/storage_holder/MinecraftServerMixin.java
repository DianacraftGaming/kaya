package io.github.eggohito.kaya.mixin.impl.command.storage_holder;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.eggohito.kaya.duck.internal.ServerAccess;
import io.github.eggohito.kaya.duck.internal.ServerCommandStorageHolder;
import io.github.eggohito.kaya.mixin.access.CommandStorageAccessor;
import io.github.eggohito.kaya.network.packet.clientbound.ClientboundCommandStorageUpdatePacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.CommandStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements ServerCommandStorageHolder {

	@Shadow
	public abstract CommandStorage getCommandStorage();

	@ModifyExpressionValue(method = "createLevels", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/storage/DimensionDataStorage;)Lnet/minecraft/world/level/storage/CommandStorage;"))
	private CommandStorage cacheServerToCommandStorage(CommandStorage original) {
		((ServerAccess) original).kaya$setServer((MinecraftServer) (Object) this);
		return original;
	}

	@Override
	public CompoundTag kaya$getStorage(ResourceLocation id) {
		return this.getCommandStorage().get(id);
	}

	@Override
	public boolean kaya$contains(ResourceLocation id) {
		CommandStorage.Container container = ((CommandStorageAccessor) this.getCommandStorage()).callGetContainer(id.getNamespace());
		return container != null
			&& ((CommandStorageAccessor.ContainerAccessor) container).getData().get(id.getPath()) != null;
	}

	@Override
	public void kaya$setStorage(ResourceLocation id, CompoundTag nbt) {
		this.getCommandStorage().set(id, nbt);
	}

	@Override
	public void kaya$sendAll(ServerPlayer player) {

		Map<String, CommandStorage.Container> storages = ((CommandStorageAccessor) this.getCommandStorage()).getNamespaces();

		for (var storage : storages.entrySet()) {

			String namespace = storage.getKey();
			CommandStorage.Container persistentState = storage.getValue();

			Map<String, CompoundTag> data = ((CommandStorageAccessor.ContainerAccessor) persistentState).getData();

			for (var entry : data.entrySet()) {

				String path = entry.getKey();
				CompoundTag nbt = entry.getValue();

				ServerPlayNetworking.send(player, new ClientboundCommandStorageUpdatePacket(ResourceLocation.fromNamespaceAndPath(namespace, path), nbt));

			}

		}

	}

}
