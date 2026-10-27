package io.github.eggohito.kaya.util;

import java.util.function.IntUnaryOperator;

public interface IntBiFunction {

	int apply(int first, int second);

	default IntBiFunction andThen(IntUnaryOperator after) {
		return (first, second) -> after.applyAsInt(this.apply(first, second));
	}

}
