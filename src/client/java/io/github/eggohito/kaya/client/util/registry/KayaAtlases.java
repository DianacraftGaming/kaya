package io.github.eggohito.kaya.client.util.registry;

import io.github.eggohito.kaya.client.event.TextureAtlasRegistrationEvents;
import io.github.eggohito.kaya.client.util.atlas.AtlasId;
import io.github.eggohito.kaya.hud.element.OverlayHudElement;

public final class KayaAtlases {

	public static final AtlasId OVERLAY = AtlasId.of(OverlayHudElement.ATLAS_SHEET, OverlayHudElement.ATLAS_NAME);

	public static void registerAll() {
		TextureAtlasRegistrationEvents.SIMPLE.register(registrant -> registrant.accept(OVERLAY));
	}

}
