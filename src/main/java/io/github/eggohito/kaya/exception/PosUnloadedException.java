package io.github.eggohito.kaya.exception;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PosUnloadedException extends RuntimeException {

	public PosUnloadedException(Level level, BlockPos pos) {
		super("Position " + pos.toShortString() + " is not loaded in dimension \"" + level.dimension().location() + "\"!");
	}

}
