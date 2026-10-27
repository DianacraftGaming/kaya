package io.github.eggohito.kaya.client.mixin.impl.power.custom.modify_elytra_render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.eggohito.kaya.power.custom.ModifyElytraRenderPower;
import io.github.eggohito.kaya.power.entity.Powers;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin {

	@ModifyExpressionValue(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;showCape:Z", opcode = Opcodes.GETFIELD))
	boolean hideWhenCustomWingsRendered(boolean original, @Local(argsOnly = true) PlayerRenderState renderState) {
		return original
			&& !Powers.hasInstances(renderState.kaya$getEntity(), ModifyElytraRenderPower.Instance.class, instance -> instance.isActive(renderState.kaya$getEntity()));
	}

}
