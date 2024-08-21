package com.plyushkin.budget.domain;

public interface AbstractNumber<T extends AbstractNumber<T>> {
    T increment();
}
