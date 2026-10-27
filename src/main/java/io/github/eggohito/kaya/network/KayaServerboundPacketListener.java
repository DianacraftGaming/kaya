package io.github.eggohito.kaya.network;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.network.packet.HandshakePacket;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

import java.util.NoSuchElementException;

public final class KayaServerboundPacketListener {

	public static void init() {

		if (Kaya.performStandaloneHandshake()) {
			ServerConfigurationConnectionEvents.BEFORE_CONFIGURE.addPhaseOrdering(Kaya.HANDSHAKE_PHASE, Event.DEFAULT_PHASE);
			ServerConfigurationConnectionEvents.BEFORE_CONFIGURE.register(Kaya.HANDSHAKE_PHASE, KayaServerboundPacketListener::requestHandshake);
		}

		ServerConfigurationNetworking.registerGlobalReceiver(HandshakePacket.TYPE, KayaServerboundPacketListener::handleHandshakeReply);

	}

	private static void handleHandshakeReply(HandshakePacket payload, ServerConfigurationNetworking.Context context) {

		String modId = payload.modId();
		ModContainer mod;

		try {

			mod = FabricLoader.getInstance()
				.getModContainer(modId)
				.orElseThrow();

			Version serverVersion = mod.getMetadata().getVersion();
			Version clientVersion = Version.parse(payload.modVersion());

			if (serverVersion.equals(clientVersion)) {
				context.networkHandler().completeTask(HandshakeTask.TYPE);
			}

			else {
				context.networkHandler().disconnect(Component.literal("This server is running a different version of '" + modId + "' (" + serverVersion + "), which is incompatible with the one you have installed! (" + clientVersion + ")"));
			}

		}

		catch (VersionParsingException e) {
			context.networkHandler().disconnect(Component.literal("The version of '" + modId + "' you have installed is invalid!"));
		}

		catch (NoSuchElementException ignored) {
			context.networkHandler().disconnect(payload.createMissingModComponent());
		}

	}

	private static void requestHandshake(ServerConfigurationPacketListenerImpl handler, MinecraftServer server) {

		HandshakeTask task = new HandshakeTask(Kaya.MOD_NAMESPACE, Kaya.getVersion().getFriendlyString());

		if (ServerConfigurationNetworking.canSend(handler, HandshakePacket.TYPE)) {
			handler.addTask(task);
		}

		else {
			handler.disconnect(task.createMissingModComponent());
		}

	}

}
