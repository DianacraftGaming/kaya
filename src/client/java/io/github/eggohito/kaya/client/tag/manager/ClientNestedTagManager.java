package io.github.eggohito.kaya.client.tag.manager;

import io.github.eggohito.kaya.network.packet.clientbound.ClientboundUpdateNestedTagPacket;
import io.github.eggohito.kaya.tag.NestedTag;
import io.github.eggohito.kaya.tag.manager.ServerNestedTagManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ClientNestedTagManager extends ServerNestedTagManager {

	@Override
	public void init() {

		super.init();

		ClientPlayNetworking.registerGlobalReceiver(ClientboundUpdateNestedTagPacket.TYPE, (payload, context) -> this.receive(payload));
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> this.clear());

	}

	private <T> void receive(ClientboundUpdateNestedTagPacket<T> payload) {
		NestedTag<T> nestedTag = payload.nestedTag();
		this.registry.put(nestedTag.registryKey(), nestedTag);
	}

	private void clear() {
		this.registry.clear();
	}

}
