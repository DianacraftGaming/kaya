package io.github.eggohito.kaya.mixin.impl.power.custom.replace_loot_table;

import io.github.eggohito.kaya.duck.internal.ContextKeySetHolder;
import io.github.eggohito.kaya.duck.internal.ReplacingLootContext;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

@Mixin(LootContext.class)
public abstract class LootContextMixin implements ReplacingLootContext {

	@Shadow
	@Final
	private LootParams params;

	@Unique
	private final Set<ResourceKey<LootTable>> kaya$replacedTables = new ObjectOpenHashSet<>();

	@Override
	public ContextKeySet kaya$getKeySet() {
		return ((ContextKeySetHolder) this.params).kaya$getKeySet();
	}

	@Override
	public void kaya$setKeySet(ContextKeySet keySet) {

	}

	@Override
	public boolean kaya$isReplaced(ResourceKey<LootTable> key) {
		return kaya$replacedTables.contains(key);
	}

	@Override
	public void kaya$setReplaced(ResourceKey<LootTable> key) {
		this.kaya$replacedTables.add(key);
	}

}
