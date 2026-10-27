package io.github.eggohito.kaya.registry.provider;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.provider.ValueProvider;
import io.github.eggohito.kaya.registry.KayaRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class KayaValueProviderTypes {

	public static void registerAll() {
		KayaBlockProviderTypes.registerAll();
		KayaBooleanProviderTypes.registerAll();
		KayaBoxProviderTypes.registerAll();
		KayaCommandSourceProviderTypes.registerAll();
		KayaDamageSourceProviderTypes.registerAll();
		KayaDirectionProviderTypes.registerAll();
		KayaEffectProviderTypes.registerAll();
		KayaEntityProviderTypes.registerAll();
		KayaFloatProviderTypes.registerAll();
		KayaIntProviderTypes.registerAll();
		KayaItemProviderTypes.registerAll();
		KayaNbtProviderTypes.registerAll();
		KayaSlotProviderTypes.registerAll();
		KayaStringProviderTypes.registerAll();
		KayaVec3ProviderTypes.registerAll();
	}

	public static <P extends ValueProvider<?>> ValueProvider.Kind<P> register(ResourceLocation id, ValueProvider.Kind<P> kind) {
		return Registry.register(KayaRegistries.VALUE_PROVIDER_KIND, id, kind);
	}

	private static <P extends ValueProvider<?>> ValueProvider.Kind<P> registerInternal(String path, ValueProvider.Kind<P> kind) {
		return register(Kaya.id(path), kind);
	}

}
