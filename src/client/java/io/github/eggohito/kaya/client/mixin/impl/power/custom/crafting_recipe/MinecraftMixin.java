package io.github.eggohito.kaya.client.mixin.impl.power.custom.crafting_recipe;

import io.github.eggohito.kaya.duck.internal.PowerRecipeDisplayHolder;
import io.github.eggohito.kaya.power.PowerIdentifier;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements PowerRecipeDisplayHolder {

	@Unique
	private final Int2ObjectOpenHashMap<PowerIdentifier> kaya$powerIdsByIndex = new Int2ObjectOpenHashMap<>();

	@Override
	public Int2ObjectMap<PowerIdentifier> kaya$getPowerIdsByIndex() {
		return new Int2ObjectOpenHashMap<>(kaya$powerIdsByIndex);
	}

	@Override
	public void kaya$setPowerIdsByIndex(Int2ObjectMap<PowerIdentifier> powerIdsByIndex) {

		this.kaya$powerIdsByIndex.clear();

		this.kaya$powerIdsByIndex.putAll(powerIdsByIndex);
		this.kaya$powerIdsByIndex.trim();

	}

}
