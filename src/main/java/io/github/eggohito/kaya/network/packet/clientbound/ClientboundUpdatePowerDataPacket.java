package io.github.eggohito.kaya.network.packet.clientbound;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.attachment.entity.PowersAttachment;
import io.github.eggohito.kaya.power.PowerIdentifier;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.power.manager.PowerManager;
import io.github.eggohito.kaya.registry.attachment.KayaEntityAttachments;
import io.github.eggohito.kaya.util.MiscUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.function.Consumers;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public record ClientboundUpdatePowerDataPacket(int entityId, Map<PowerIdentifier, Tag> powersAndData) implements CustomPacketPayload {

	private static final StreamCodec<RegistryFriendlyByteBuf, Map<PowerIdentifier, Tag>> POWERS_AND_DATA_CODEC = ByteBufCodecs.map(Object2ObjectOpenHashMap::new, PowerIdentifier.STREAM_CODEC, ByteBufCodecs.TRUSTED_TAG);

	public static final Type<ClientboundUpdatePowerDataPacket> TYPE = new Type<>(Kaya.id("clientbound/update_power_data"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdatePowerDataPacket> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, ClientboundUpdatePowerDataPacket::entityId, POWERS_AND_DATA_CODEC, ClientboundUpdatePowerDataPacket::powersAndData, ClientboundUpdatePowerDataPacket::new);

	public ClientboundUpdatePowerDataPacket(int entityId, PowerIdentifier id, Tag data) {
		this(entityId, Util.make(new Object2ObjectOpenHashMap<>(), map -> map.put(id, data)));
	}

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	@SuppressWarnings("UnstableApiUsage")
	public void handle(Level level) {

		Entity holder = level.getEntity(this.entityId());
		PowersAttachment attachment = holder != null
			? holder.getAttached(KayaEntityAttachments.POWERS)
			: null;

		if (holder == null) {
			Kaya.LOGGER.warn("Couldn't sync data of the following powers to non-existent entity: [{}]", powersAndData().keySet().stream().map(PowerIdentifier::toString).collect(Collectors.joining(", ")));
		}

		else if (attachment != null && attachment != PowersAttachment.EMPTY) {

			Powers powers = Powers.getOrCreate(holder);
			RegistryOps<Tag> ops = level.registryAccess().createSerializationContext(NbtOps.INSTANCE);

			for (var entry : powersAndData().entrySet()) {

				PowerIdentifier id = entry.getKey();
				Tag data = entry.getValue();

				if (!PowerManager.getInstance().contains(id)) {
					Kaya.LOGGER.warn("Couldn't sync data of unregistered {}!", id.asDisplayString(false));
				}

				else if (!powers.hasInstance(id)) {
					Kaya.LOGGER.warn("Couldn't sync data of {} to entity {} as it wasn't granted!", id.asDisplayString(false), holder.getName().getString());
				}

				else {
					MiscUtil.handleResult(
						powers.getInstance(id).decodeData(ops, data),
						Consumers.nop(),
						warning -> Kaya.LOGGER.warn("Found warnings while decoding data of instance for {} on entity {}: {}", id.asDisplayString(false), holder.getName().getString(), warning),
						error -> Kaya.LOGGER.error("Couldn't decode and receive data of instance for {} on entity {}: {}", id.asDisplayString(false), holder.getName().getString(), error)
					);
				}

			}

		}

	}

}
