package io.github.eggohito.kaya.mixin.impl.power.custom.replace_loot_table;

import io.github.eggohito.kaya.duck.internal.ContextKeySetHolder;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(LootParams.class)
public abstract class LootParamsMixin implements ContextKeySetHolder {

	@Unique
	private ContextKeySet kaya$keySet;

	@Override
	public ContextKeySet kaya$getKeySet() {
		return Objects.requireNonNull(this.kaya$keySet, "Loot params not initialized properly!");
	}

	@Override
	public void kaya$setKeySet(ContextKeySet keySet) {
		this.kaya$keySet = keySet;
	}

}
