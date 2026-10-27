package io.github.eggohito.kaya.hud.element.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.color.Color;
import io.github.eggohito.kaya.hud.Sprite;
import io.github.eggohito.kaya.hud.element.OverlayHudElement;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.registry.KayaHudElementTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record TextureOverlayHudElement(Sprite sprite, Color color, RenderPhase renderPhase, BooleanProvider shouldRender, BooleanProvider hideWithHud, BooleanProvider visibleInThirdPerson, int order) implements OverlayHudElement {

	public static final MapCodec<TextureOverlayHudElement> CODEC = OverlayHudElement.createCommonOverlayCodec(TextureOverlayHudElement::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, TextureOverlayHudElement> STREAM_CODEC = OverlayHudElement.createCommonOverlayStreamCodec(TextureOverlayHudElement::new);

	@Override
	public Type<?> getType() {
		return KayaHudElementTypes.TEXTURE_OVERLAY;
	}

}
