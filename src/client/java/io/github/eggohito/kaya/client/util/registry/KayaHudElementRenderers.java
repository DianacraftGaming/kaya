package io.github.eggohito.kaya.client.util.registry;

import io.github.eggohito.kaya.client.hud.HudElementHelper;
import io.github.eggohito.kaya.client.hud.renderer.custom.NauseaOverlayHudElementRenderer;
import io.github.eggohito.kaya.client.hud.renderer.custom.ResourceBarHudElementRenderer;
import io.github.eggohito.kaya.client.hud.renderer.custom.TextureOverlayHudElementRenderer;
import io.github.eggohito.kaya.registry.KayaHudElementTypes;

public final class KayaHudElementRenderers {

	public static void registerAll() {
		HudElementHelper.registerRenderer(KayaHudElementTypes.NAUSEA_OVERLAY, NauseaOverlayHudElementRenderer.INSTANCE);
		HudElementHelper.registerRenderer(KayaHudElementTypes.RESOURCE_BAR, ResourceBarHudElementRenderer.INSTANCE);
		HudElementHelper.registerRenderer(KayaHudElementTypes.TEXTURE_OVERLAY, TextureOverlayHudElementRenderer.INSTANCE);
	}

}
