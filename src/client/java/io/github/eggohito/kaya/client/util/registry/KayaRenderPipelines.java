package io.github.eggohito.kaya.client.util.registry;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.eggohito.kaya.Kaya;
import net.minecraft.client.renderer.RenderPipelines;

/**
 *	Render pipelines for overlays that has depth test/writing enabled, relevant for rendering overlays below/above the HUD
 */
public final class KayaRenderPipelines {

	public static final RenderPipeline GUI_NAUSEA_OVERLAY = RenderPipelines.register(
		RenderPipeline.builder(RenderPipelines.GUI_TEXTURED_SNIPPET)
			.withLocation(Kaya.id("pipeline/gui_overlay/nausea"))
			.withBlend(BlendFunction.ADDITIVE)
			.build()
	);

	public static final RenderPipeline GUI_TEXTURED_OVERLAY = RenderPipelines.register(
		RenderPipeline.builder(RenderPipelines.GUI_TEXTURED_SNIPPET)
			.withLocation(Kaya.id("pipeline/gui_overlay/textured"))
			.build()
	);

}
