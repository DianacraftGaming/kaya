package io.github.eggohito.kaya.action.custom;

import com.mojang.serialization.MapCodec;
import io.github.eggohito.kaya.action.Action;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.registry.KayaActionTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public enum NothingAction implements Action {

	INSTANCE;

	public static final MapCodec<NothingAction> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, NothingAction> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public Type<?> getType() {
		return KayaActionTypes.NOTHING;
	}

	@Override
	public void execute(Context context) {

	}

	@Override
	public void validate(ContextValidator validator) {

	}

}
