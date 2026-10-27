package io.github.eggohito.kaya.mixin.impl.power.custom.modify_damage_taken;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import io.github.eggohito.kaya.power.custom.ModifyDamageTakenPower;
import io.github.eggohito.kaya.power.entity.Powers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class, priority = 998)
public abstract class PlayerMixin extends LivingEntity {

	PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;removeEntitiesOnShoulder()V"))
	void accountForModifyingPowers(ServerLevel level, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir, @Share(value = "hasDamageModifiers", namespace = "kaya:damage_modifying") LocalBooleanRef hasDamageModifiersRef) {
		hasDamageModifiersRef.set(hasDamageModifiersRef.get() || Powers.hasInstances(this, ModifyDamageTakenPower.Instance.class));
	}

}
