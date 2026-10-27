package io.github.eggohito.kaya.registry;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.event.ComponentContentsRegistration;
import io.github.eggohito.kaya.network.chat.contents.TranslatableContentsWithTextFallback;
import net.minecraft.network.chat.ComponentContents;

public final class KayaComponentContents {

	public static final ComponentContents.Type<TranslatableContentsWithTextFallback> TRANSLATABLE_WITH_TEXT_FALLBACK = registerInternal("translatable_with_text_fallback", TranslatableContentsWithTextFallback.CODEC);

	public static void registerAll() {

	}

	private static <C extends ComponentContents> ComponentContents.Type<C> registerInternal(String path, MapCodec<C> codec) {

		ComponentContents.Type<C> type = new ComponentContents.Type<>(codec, Kaya.id(path).toString());
		ComponentContentsRegistration.EVENT.register(registrant -> registrant.accept(type));

		return type;

	}

}
