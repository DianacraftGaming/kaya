package io.github.eggohito.kaya.util.services;

import java.util.ServiceLoader;

public final class KayaServices {

	public static <T> T load(Class<T> serviceClass) {
		return ServiceLoader.load(serviceClass, KayaServices.class.getClassLoader())
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Couldn't load service for " + serviceClass.getName()));
	}

}
