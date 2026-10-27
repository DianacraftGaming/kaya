package io.github.eggohito.kaya.hud.element.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.color.Color;
import io.github.eggohito.kaya.hud.Sprite;
import io.github.eggohito.kaya.hud.element.OverlayHudElement;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.registry.KayaHudElementTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record NauseaOverlayHudElement(Sprite sprite, Color color, RenderPhase renderPhase, BooleanProvider shouldRender, BooleanProvider hideWithHud, BooleanProvider visibleInThirdPerson, int order) implements OverlayHudElement {

	public static final MapCodec<NauseaOverlayHudElement> CODEC = OverlayHudElement.createCommonOverlayCodec(NauseaOverlayHudElement::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, NauseaOverlayHudElement> STREAM_CODEC = OverlayHudElement.createCommonOverlayStreamCodec(NauseaOverlayHudElement::new);

	@Override
	public Type<?> getType() {
		return KayaHudElementTypes.NAUSEA_OVERLAY;
	}

}
