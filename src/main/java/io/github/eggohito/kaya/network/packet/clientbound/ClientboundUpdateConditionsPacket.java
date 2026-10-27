package io.github.eggohito.kaya.network.packet.clientbound;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.condition.manager.ConditionManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record ClientboundUpdateConditionsPacket(Map<ResourceLocation, Condition> conditions) implements CustomPacketPayload {

	public static final Type<ClientboundUpdateConditionsPacket> TYPE = new Type<>(ConditionManager.ID.withPath(path -> "clientbound/" + path + "/update"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateConditionsPacket> CODEC = StreamCodec.ofMember(ClientboundUpdateConditionsPacket::send, ClientboundUpdateConditionsPacket::receive);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	private static ClientboundUpdateConditionsPacket receive(RegistryFriendlyByteBuf buf) {

		Map<ResourceLocation, Condition> conditions = new Object2ObjectLinkedOpenHashMap<>();
		int count = buf.readInt();

		for (int i = 0; i < count; i++) {

			ResourceLocation id = ResourceLocation.STREAM_CODEC.decode(buf);

			try {
				conditions.put(id, Condition.STREAM_CODEC.decode(buf));
			}

			catch (Exception e) {
				Kaya.LOGGER.error("Couldn't decode condition \"{}\" during the syncing process", id, e);
				throw e;
			}

		}

		return new ClientboundUpdateConditionsPacket(conditions);

	}

	private void send(RegistryFriendlyByteBuf buf) {

		buf.writeInt(conditions().size());

		for (var entry : conditions().entrySet()) {

			ResourceLocation.STREAM_CODEC.encode(buf, entry.getKey());

			try {
				Condition.STREAM_CODEC.encode(buf, entry.getValue());
			}

			catch (Exception e) {
				Kaya.LOGGER.error("Couldn't encode condition \"{}\" during the syncing process", entry.getKey(), e);
				throw e;
			}

		}

	}

}
