package io.github.eggohito.kaya.network;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.network.packet.HandshakePacket;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ConfigurationTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public record HandshakeTask(String modId, String modVersion) implements ConfigurationTask {

	public static final Type TYPE = new Type(Kaya.id("handshake").toString());

	@Override
	public @NotNull Type type() {
		return TYPE;
	}

	@Override
	public void start(Consumer<Packet<?>> task) {
		task.accept(ServerConfigurationNetworking.createS2CPacket(new HandshakePacket(this.modId(), this.modVersion())));
	}

	public Component createMissingModComponent() {
		return createMissingModComponent(this.modId(), this.modVersion());
	}

	public static Component createMissingModComponent(String modId, String modVersion) {
		return Component.literal("This server requires you to install '" + modId + "' (" + modVersion + ") to play!");
	}

}
