package io.github.eggohito.kaya.mixin.impl.power.custom.replace_loot_table;

import com.google.common.base.Predicates;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.duck.internal.KeyableLootTable;
import io.github.eggohito.kaya.duck.internal.ReplacingLootContext;
import io.github.eggohito.kaya.power.custom.ReplaceLootTablePower;
import io.github.eggohito.kaya.power.custom.misc.PrioritizedPower;
import io.github.eggohito.kaya.util.Reporter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.slf4j.event.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixin implements KeyableLootTable {

	@Unique
	private ResourceKey<LootTable> kaya$key;

	@Unique
	private ReloadableServerRegistries.Holder kaya$holder;

	@Override
	public ResourceKey<LootTable> kaya$getKey() {
		return this.kaya$key;
	}

	@Override
	public void kaya$setup(ResourceKey<LootTable> key, ReloadableServerRegistries.Holder holder) {
		this.kaya$key = key;
		this.kaya$holder = holder;
	}

	@Inject(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
	void replaceTable(LootContext lootContext, Consumer<ItemStack> output, CallbackInfo ci) {

		if (!(lootContext instanceof ReplacingLootContext replacingLootContext)) {
			return;
		}

		ContextKeySet keySet = replacingLootContext.kaya$getKeySet();
		ResourceKey<LootTable> key = this.kaya$getKey();

		if (key == null || replacingLootContext.kaya$isReplaced(key)) {
			return;
		}

		Entity thisEntity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
		Entity holder = thisEntity;

		if (keySet == LootContextParamSets.FISHING) {

			if (thisEntity instanceof FishingHook fishingHook) {
				holder = fishingHook.getOwner();
			}

		}

		else if (keySet == LootContextParamSets.ENTITY) {

			if (lootContext.hasParameter(LootContextParams.ATTACKING_ENTITY)) {
				holder = lootContext.getParameter(LootContextParams.ATTACKING_ENTITY);
			}

			else if (lootContext.hasParameter(LootContextParams.DIRECT_ATTACKING_ENTITY)) {
				holder = lootContext.getParameter(LootContextParams.DIRECT_ATTACKING_ENTITY);
			}

		}

		else if (keySet == LootContextParamSets.PIGLIN_BARTER) {

			if (thisEntity instanceof Piglin piglin) {
				holder = piglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).orElse(null);
			}

		}

		PrioritizedPower.InstanceCollection<ReplaceLootTablePower.Instance> instances = new PrioritizedPower.InstanceCollection<>(holder, ReplaceLootTablePower.Instance.class, Predicates.alwaysTrue());
		List<LootTable> replacements = new ObjectArrayList<>();

		for (var instance : instances) {

			Context context = instance.createContext(holder, lootContext);
			Reporter reporter = context.reporter();

			if (instance.isActive(context)) {

				LootTable replacementTable = instance.getReplacement(context.forChild(".replacements"), key)
					.map(this.kaya$holder::getLootTable)
					.orElse(null);

				if (replacementTable != null && replacementTable != LootTable.EMPTY) {
					replacements.add(replacementTable);
				}

			}

			if (reporter.hasProblems()) {
				Kaya.logOnce(Level.WARN, "Found errors while trying to replace loot table \"" + key.location() + "\"\n" + reporter.getReport());
			}

		}

		if (replacements.isEmpty()) {
			return;
		}

		ReplaceLootTablePower.push((LootTable) (Object) this);
		replacements.forEach(ReplaceLootTablePower::push);

		replacingLootContext.kaya$setReplaced(key);
		ReplaceLootTablePower.peek().getRandomItemsRaw(lootContext, output);

		ReplaceLootTablePower.clear();
		ci.cancel();

	}

	@Inject(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootContext;pushVisitedElement(Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;)Z"))
	void popOnEntryPush(LootContext context, Consumer<ItemStack> output, CallbackInfo ci) {
		ReplaceLootTablePower.pop();
	}

	@Inject(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootContext;popVisitedElement(Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;)V"))
	void restoreOnEntryPop(LootContext context, Consumer<ItemStack> output, CallbackInfo ci) {
		ReplaceLootTablePower.restore();
	}

}
