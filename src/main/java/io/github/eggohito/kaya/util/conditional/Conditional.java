package io.github.eggohito.kaya.util.conditional;

import io.github.eggohito.kaya.condition.Condition;

public interface Conditional<T> {

	Condition condition();

	T onTrue();

	T onFalse();

}
