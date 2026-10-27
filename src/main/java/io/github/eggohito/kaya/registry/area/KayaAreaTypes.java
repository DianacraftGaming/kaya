package io.github.eggohito.kaya.registry.area;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.area.Area;
import io.github.eggohito.kaya.area.custom.BoxArea;
import io.github.eggohito.kaya.area.custom.RadiusArea;
import io.github.eggohito.kaya.registry.KayaRegistries;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class KayaAreaTypes {

	public static final Area.Type<RadiusArea> RADIUS = registerInternal("radius", RadiusArea.CODEC, RadiusArea.STREAM_CODEC);
	public static final Area.Type<BoxArea> BOX = registerInternal("box", BoxArea.CODEC, BoxArea.STREAM_CODEC);

	public static void registerAll() {

	}

	public static <A extends Area> Area.Type<A> register(ResourceLocation id, MapCodec<A> mapCodec, StreamCodec<RegistryFriendlyByteBuf, A> streamCodec) {
		return Registry.register(KayaRegistries.AREA_TYPE, id, new Area.Type<>(mapCodec, streamCodec));
	}

	private static <A extends Area> Area.Type<A> registerInternal(String path, MapCodec<A> mapCodec, StreamCodec<RegistryFriendlyByteBuf, A> streamCodec) {
		return register(Kaya.id(path), mapCodec, streamCodec);
	}

}
