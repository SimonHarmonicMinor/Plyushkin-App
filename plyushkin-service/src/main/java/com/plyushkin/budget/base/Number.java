package com.plyushkin.budget.base;

public interface Number<T extends Number<T>> {
    T increment();
}
