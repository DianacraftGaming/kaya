package io.github.eggohito.kaya.client.hud;

import io.github.eggohito.kaya.client.hud.internal.HudElementHelperImpl;
import io.github.eggohito.kaya.client.hud.renderer.HudElementRenderer;
import io.github.eggohito.kaya.client.hud.source.HudElementSource;
import io.github.eggohito.kaya.hud.element.HudElement;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface HudElementHelper {

	static <E extends HudElement> void registerRenderer(HudElement.Type<E> type, HudElementRenderer<E> renderer) {
		HudElementHelperImpl.registerRenderer(type, renderer);
	}

	static void registerSource(HudElementSource source) {
		HudElementHelperImpl.registerSource(source);
	}

}
