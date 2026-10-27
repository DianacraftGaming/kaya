package io.github.eggohito.kaya.client.util.registry;

import io.github.eggohito.kaya.Kaya;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

public final class KayaRenderTypes {

	public static final Function<ResourceLocation, RenderType> GUI_NAUSEA_OVERLAY = Util.memoize(id -> RenderType.create(
		Kaya.id("gui_overlay/nausea").toString(),
		1536,
		KayaRenderPipelines.GUI_NAUSEA_OVERLAY,
		RenderType.CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(id, TriState.DEFAULT, false))
			.createCompositeState(false)
	));

	public static final Function<ResourceLocation, RenderType> GUI_TEXTURED_OVERLAY = Util.memoize(id -> RenderType.create(
		Kaya.id("gui_overlay/textured").toString(),
		1536,
		KayaRenderPipelines.GUI_TEXTURED_OVERLAY,
		RenderType.CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(id, TriState.DEFAULT, false))
			.createCompositeState(false)
	));

}
