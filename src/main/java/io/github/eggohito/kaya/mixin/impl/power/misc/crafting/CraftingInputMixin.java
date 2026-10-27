package io.github.eggohito.kaya.mixin.impl.power.misc.crafting;

import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.duck.internal.PowerCrafting;
import io.github.eggohito.kaya.power.Power;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.crafting.CraftingInput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
import java.util.Objects;

@Mixin(CraftingInput.class)
public abstract class CraftingInputMixin implements PowerCrafting {

	@Unique
	private final ThreadLocal<Map<? extends Power.Instance<?>, Context>> kaya$modifyingInstances = new ThreadLocal<>();

	@Unique
	private final ThreadLocal<TransientCraftingContainer> kaya$inventory = new ThreadLocal<>();

	@Unique
	private final ThreadLocal<Entity> kaya$entity = new ThreadLocal<>();

	@Override
	public Map<? extends Power.Instance<?>, Context> kaya$getModifyingInstances() {
		return Objects.requireNonNullElseGet(kaya$modifyingInstances.get(), Map::of);
	}

	@Override
	public void kaya$setModifyingInstances(Map<? extends Power.Instance<?>, Context> modifyingInstances) {

		if (modifyingInstances == null || modifyingInstances.isEmpty()) {
			this.kaya$modifyingInstances.remove();
		}

		else {
			this.kaya$modifyingInstances.set(modifyingInstances);
		}

	}

	@Override
	public TransientCraftingContainer kaya$getInventory() {
		return this.kaya$inventory.get();
	}

	@Override
	public void kaya$setInventory(TransientCraftingContainer inventory) {

		if (inventory == null) {
			this.kaya$inventory.remove();
		}

		else {
			this.kaya$inventory.set(inventory);
		}

	}

	@Override
	public @Nullable Entity kaya$getEntity() {
		return this.kaya$entity.get();
	}

	@Override
	public void kaya$setEntity(@Nullable Entity entity) {

		if (entity == null) {
			this.kaya$entity.remove();
		}

		else {
			this.kaya$entity.set(entity);
		}

	}

}
