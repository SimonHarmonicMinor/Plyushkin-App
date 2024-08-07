package com.plyushkin.budget.base;

public interface AbstractNumber<T extends AbstractNumber<T>> {
    T increment();
}
