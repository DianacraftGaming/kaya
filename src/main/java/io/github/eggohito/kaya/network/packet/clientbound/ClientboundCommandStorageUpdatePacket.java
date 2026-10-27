package io.github.eggohito.kaya.network.packet.clientbound;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.duck.internal.CommandStorageHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ClientboundCommandStorageUpdatePacket(ResourceLocation id, CompoundTag nbt) implements CustomPacketPayload {

	public static final Type<ClientboundCommandStorageUpdatePacket> TYPE = new Type<>(Kaya.id("clientbound/update_command_storage"));
	public static final StreamCodec<FriendlyByteBuf, ClientboundCommandStorageUpdatePacket> CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, ClientboundCommandStorageUpdatePacket::id,
		ByteBufCodecs.TRUSTED_COMPOUND_TAG, ClientboundCommandStorageUpdatePacket::nbt,
		ClientboundCommandStorageUpdatePacket::new
	);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle(CommandStorageHolder storageHolder) {
		storageHolder.kaya$setStorage(this.id(), this.nbt());
	}

}
