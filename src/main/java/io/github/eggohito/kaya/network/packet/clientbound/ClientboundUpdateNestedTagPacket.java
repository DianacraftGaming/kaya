package io.github.eggohito.kaya.network.packet.clientbound;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.tag.NestedTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ClientboundUpdateNestedTagPacket<T>(NestedTag<T> nestedTag) implements CustomPacketPayload {

	public static final Type<ClientboundUpdateNestedTagPacket<?>> TYPE = new Type<>(Kaya.id("clientbound/nested_tag/update"));
	public static final StreamCodec<FriendlyByteBuf, ClientboundUpdateNestedTagPacket<?>> CODEC = NestedTag.STREAM_CODEC.map(ClientboundUpdateNestedTagPacket::new, ClientboundUpdateNestedTagPacket::nestedTag);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

}
