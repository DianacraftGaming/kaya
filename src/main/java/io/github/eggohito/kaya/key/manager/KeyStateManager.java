package io.github.eggohito.kaya.key.manager;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.key.KeyState;
import io.github.eggohito.kaya.util.manager.KayaManagers;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface KeyStateManager {

	ResourceLocation ID = Kaya.id("manager/key_state");

	Supplier<KeyStateManager> DEFERRED_INSTANCE = KayaManagers.deferredPhysicalSideSpecific(KeyStateManager.class, ServerKeyStateManager::new);

	Optional<KeyState> getCurrentState(UUID uuid, String key);

	Optional<KeyState> getPreviousState(UUID uuid, String key);

	@ApiStatus.Internal
	void init();

	static KeyStateManager getInstance() {
		return DEFERRED_INSTANCE.get();
	}

}
