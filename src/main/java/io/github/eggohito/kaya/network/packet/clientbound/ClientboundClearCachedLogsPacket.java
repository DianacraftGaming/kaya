package io.github.eggohito.kaya.network.packet.clientbound;

import io.github.eggohito.kaya.Kaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public enum ClientboundClearCachedLogsPacket implements CustomPacketPayload {

	INSTANCE;

	public static final Type<ClientboundClearCachedLogsPacket> TYPE = new Type<>(Kaya.id("clientbound/clear_logs"));
	public static final StreamCodec<ByteBuf, ClientboundClearCachedLogsPacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

}
