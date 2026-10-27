package io.github.eggohito.kaya.mixin.impl.misc.better_codec_errors;

import com.mojang.serialization.DataResult;
import io.github.eggohito.kaya.util.MiscUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DataResult.class)
public interface DataResultMixin {

	/**
	 * @author	eggohito
	 * @reason	To append the error messages in a readable way, although it may take up more space vertically.
	 */
	@Overwrite(remap = false)
	static String appendMessages(String first, String second) {
		return MiscUtil.mergeErrors(first, second);
	}

}
