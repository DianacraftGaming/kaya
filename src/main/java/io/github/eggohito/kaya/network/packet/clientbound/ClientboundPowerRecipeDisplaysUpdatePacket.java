package io.github.eggohito.kaya.network.packet.clientbound;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.duck.internal.PowerRecipeDisplayHolder;
import io.github.eggohito.kaya.power.PowerIdentifier;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ClientboundPowerRecipeDisplaysUpdatePacket(Int2ObjectMap<PowerIdentifier> displays) implements CustomPacketPayload {

	private static final StreamCodec<RegistryFriendlyByteBuf, Int2ObjectMap<PowerIdentifier>> RECIPE_CODEC = ByteBufCodecs.map(Int2ObjectOpenHashMap::new, ByteBufCodecs.INT, PowerIdentifier.STREAM_CODEC);

	public static final Type<ClientboundPowerRecipeDisplaysUpdatePacket> TYPE = new Type<>(Kaya.id("clientbound/update_power_recipe_displays"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundPowerRecipeDisplaysUpdatePacket> CODEC = RECIPE_CODEC.map(ClientboundPowerRecipeDisplaysUpdatePacket::new, ClientboundPowerRecipeDisplaysUpdatePacket::displays);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle(PowerRecipeDisplayHolder holder) {
		holder.kaya$setPowerIdsByIndex(this.displays());
	}

}
