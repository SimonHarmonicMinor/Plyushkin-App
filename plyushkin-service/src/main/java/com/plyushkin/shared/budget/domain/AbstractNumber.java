package com.plyushkin.shared.budget.domain;

public interface AbstractNumber<T extends AbstractNumber<T>> {
    T increment();
}
