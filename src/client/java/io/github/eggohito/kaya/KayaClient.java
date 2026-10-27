package io.github.eggohito.kaya;

import io.github.eggohito.kaya.client.duck.internal.ClientCommandStorageHolder;
import io.github.eggohito.kaya.client.integration.ClientConfigIntegrations;
import io.github.eggohito.kaya.client.integration.PowerClientIntegrations;
import io.github.eggohito.kaya.client.util.registry.KayaAtlases;
import io.github.eggohito.kaya.client.util.registry.KayaHudElementRenderers;
import io.github.eggohito.kaya.duck.internal.PowerRecipeDisplayHolder;
import io.github.eggohito.kaya.network.KayaClientboundPacketListener;
import io.github.eggohito.kaya.network.packet.clientbound.ClientboundClearCachedLogsPacket;
import io.github.eggohito.kaya.registry.KayaColorResolvers;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorResolverRegistry;

public final class KayaClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		KayaClientboundPacketListener.init();

		PowerClientIntegrations.registerAll();
		ClientConfigIntegrations.init();

		KayaAtlases.registerAll();
		KayaColorResolvers.registerAll(ColorResolverRegistry::register);

		KayaHudElementRenderers.registerAll();

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			((ClientCommandStorageHolder) client).kaya$clear();
			((PowerRecipeDisplayHolder) client).kaya$setPowerIdsByIndex(new Int2ObjectOpenHashMap<>());
		});

		ClientPlayNetworking.registerGlobalReceiver(ClientboundClearCachedLogsPacket.TYPE, (payload, context) -> clearCachedLogs());
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> clearCachedLogs());

	}

	private static void clearCachedLogs() {
		Kaya.CACHED_LOGS.clear();
	}

}
