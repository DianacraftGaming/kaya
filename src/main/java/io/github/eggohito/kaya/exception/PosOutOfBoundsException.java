package io.github.eggohito.kaya.exception;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PosOutOfBoundsException extends RuntimeException {

	public PosOutOfBoundsException(Level level, BlockPos pos) {
		super("Position " + pos.toShortString() + " is out of bounds of dimension \"" + level.dimension().location() + "\"!");
	}

}
