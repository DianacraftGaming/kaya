package io.github.eggohito.kaya.integration;

import io.github.eggohito.kaya.config.KayaCommonConfig;
import io.github.eggohito.kaya.power.custom.ModifyPlayerSpawnPower;
import io.github.eggohito.kaya.power.custom.MultiplePower;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class CommonConfigIntegrations {

	public static void init() {

		KayaCommonConfig.INSTANCE.loadFromFile();

		MultiplePower.Config.INSTANCE.loadFromFile();
		ModifyPlayerSpawnPower.Config.INSTANCE.loadFromFile();

		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {

			KayaCommonConfig.INSTANCE.loadFromFile();

			MultiplePower.Config.INSTANCE.loadFromFile();
			ModifyPlayerSpawnPower.Config.INSTANCE.loadFromFile();

		});

	}

}
