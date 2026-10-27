package io.github.eggohito.kaya.mixin.impl.power.misc.crafting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.eggohito.kaya.duck.internal.PowerCrafting;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CraftingContainer.class)
public interface CraftingContainerMixin extends PowerCrafting {

	@ModifyReturnValue(method = "asPositionedCraftInput", at = @At("RETURN"))
	private CraftingInput.Positioned passCacheToPositioned(CraftingInput.Positioned original) {

		if (original.input() instanceof PowerCrafting powerCrafting) {
			powerCrafting.kaya$setModifyingInstances(this.kaya$getModifyingInstances());
			powerCrafting.kaya$setInventory(this.kaya$getInventory());
			powerCrafting.kaya$setEntity(this.kaya$getEntity());
		}

		return original;

	}

}
