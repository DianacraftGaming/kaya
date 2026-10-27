package io.github.eggohito.kaya.client.mixin.impl.power.custom.modify_model_color_self;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.eggohito.kaya.duck.internal.EntityCache;
import io.github.eggohito.kaya.power.custom.ModifyModelColorSelfPower;
import io.github.eggohito.kaya.power.entity.Powers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.lang.ref.WeakReference;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin implements EntityCache {

	@Unique
	protected WeakReference<Entity> kaya$entity = null;

	@Nullable
	@Override
	public Entity kaya$getEntity() {

		if (kaya$entity != null) {
			return kaya$entity.get();
		}

		else {
			return null;
		}

	}

	@Override
	public void kaya$setEntity(@Nullable Entity entity) {
		this.kaya$entity = entity == null ? null : new WeakReference<>(entity);
	}

	@WrapOperation(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
	void modifyArmModelColor(ModelPart armPart, PoseStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original) {

		Entity entity = this.kaya$getEntity();

		if (entity != null && Powers.hasInstances(entity, ModifyModelColorSelfPower.Instance.class)) {
			armPart.render(matrices, vertices, light, overlay, ModifyModelColorSelfPower.modify(null, entity, -1));
		}

		else {
			original.call(armPart, matrices, vertices, light, overlay);
		}

	}

}
