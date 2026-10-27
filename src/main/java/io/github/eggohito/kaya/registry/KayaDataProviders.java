package io.github.eggohito.kaya.registry;

import io.github.eggohito.kaya.command.data.provider.PowerDataProvider;
import io.github.eggohito.kaya.event.DataProviderRegistration;
import net.minecraft.server.commands.data.DataCommands;

import java.util.function.Function;

public final class KayaDataProviders {

    public static final Function<String, DataCommands.DataProvider> POWER_DATA = PowerDataProvider::new;

    public static void registerAll() {
        DataProviderRegistration.EVENT.register(registrant -> registrant.accept(POWER_DATA));
    }

}
